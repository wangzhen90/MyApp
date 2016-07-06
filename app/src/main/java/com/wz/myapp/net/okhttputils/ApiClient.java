package com.wz.myapp.net.okhttputils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.wz.myapp.AppApplication;
import com.wz.myapp.net.okhttputils.builder.GetBuilder;
import com.wz.myapp.net.okhttputils.builder.PostBuilder;
import com.wz.myapp.net.okhttputils.builder.PostFormBuilder;
import com.wz.myapp.net.okhttputils.builder.PostStringBuilder;
import com.wz.myapp.net.okhttputils.cache.ACache;
import com.wz.myapp.net.okhttputils.callback.BaseCallback;
import com.wz.myapp.net.okhttputils.https.HttpsUtils;
import com.wz.myapp.net.okhttputils.intercepter.LoggerInterceptor;
import com.wz.myapp.net.okhttputils.request.RequestCall;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by dell on 2016/5/30.
 */
public class ApiClient {

    private static ApiClient INSTANCE;

    private OkHttpClient mOkHttpClient;

    private final Handler mUIHandler;
    private boolean isShowResponse = true;
    private boolean isShowRequest;
    private Map<String, String> gloableHeaders;
    private Map<String, String> gloableParams;

    /**
     * 从网络获�?
     */
    public static final int CACHE_TYPE_NET = 0;
    /**
     * 获取缓存
     */
    public static final int CACHE_TYPE_CACHE = 1;
    /**
     * 网络获取失败，获取缓�?
     */
    public static final int CACHE_TYPE_NET_FAILED_CACHE = 2;
    /**
     * 先获取缓存在获取网络
     */
    public static final int CACHE_TYPE_CACHE_AND_NET = 3;

    private ApiClient() {
        mUIHandler = new Handler(Looper.getMainLooper());
        mOkHttpClient =
                new OkHttpClient.Builder().connectTimeout(ApiConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(ApiConfig.WRITE_TIMEOUT, TimeUnit.SECONDS).readTimeout(
                        ApiConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                        //                .addInterceptor(new ApiHeader())
                        .addInterceptor(new LoggerInterceptor(null, isShowResponse))
                        .addNetworkInterceptor(new StethoInterceptor())
                        .sslSocketFactory(HttpsUtils.getSslSocketFactory(null,null,null))
                        .build();
    }

    public static ApiClient getInstance() {

        if (INSTANCE == null) {
            synchronized ((ApiClient.class)) {
                if (INSTANCE == null) {
                    INSTANCE = new ApiClient();
                }
            }
        }
        return INSTANCE;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public GetBuilder get() {
        return new GetBuilder();
    }

    public PostBuilder post() {
        return new PostBuilder();
    }

    public PostFormBuilder postForm() {
        return new PostFormBuilder();
    }

    public PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public void excute(Request request, BaseCallback callback) {

        //TODO

    }

    public void enqueue(RequestCall requestCall, BaseCallback callback) {
        if (callback == null) {
            callback = BaseCallback.DEFALT_CALLBACK;
        }
        final BaseCallback finalCallback = callback;
        request(requestCall, finalCallback);
    }

    private void requestNet(final RequestCall requestCall, final BaseCallback finalCallback) {
        Call call = requestCall.getCall();
        if (call == null) {
            return;
        }
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallback(call, e, finalCallback, requestCall);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Request had cancel!"), finalCallback,
                                requestCall);
                        return;
                    }

                    Object obj = finalCallback.parseNetworkResponse(response);
                    //TODO make a thread pool?  add a params to make async or sync?
                    if (obj != null && obj instanceof Serializable) {
                        saveCache((Serializable) obj, requestCall.getCacheTag());
//                        ACache.get().put(requestCall.getCacheTag(),response.body().bytes());
                    }

                    senSuccResultCallback(call, obj, finalCallback, requestCall);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, requestCall);
                    e.printStackTrace();
                } finally {
                    if (response != null) {
                        response.close();
                    }
                }
            }
        });
    }

    private void requestCache(final RequestCall requestCall, final BaseCallback finalCallback) {
        finalCallback.onBefore();
        Object cache = getCache(requestCall.getCacheTag());
        if (cache == null) {
            sendFailResultCallback(null, new IllegalStateException("get Cache failed!"), finalCallback,
                    requestCall);
        } else {
            senSuccResultCallback(null, cache, finalCallback, requestCall);
        }
    }

    private void request(RequestCall requestCall, final BaseCallback finalCallback) {
        finalCallback.onBefore();
        if (requestCall.getCacheTag() == null) {
            requestCall.setCacheType(CACHE_TYPE_NET);
            requestNet(requestCall, finalCallback);
            return;
        }

        switch (requestCall.getCacheType()) {

            case CACHE_TYPE_NET:
            case CACHE_TYPE_NET_FAILED_CACHE:
                requestNet(requestCall, finalCallback);
                break;

            case CACHE_TYPE_CACHE:
                requestCache(requestCall, finalCallback);
                break;
            case CACHE_TYPE_CACHE_AND_NET:
                requestCache(requestCall, finalCallback);
                break;
        }
    }

    private void sendFailResultCallback(final Call call, final Exception e,
                                        final BaseCallback callback, final RequestCall requestCall) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {

                switch (requestCall.getCacheType()) {

                    case CACHE_TYPE_NET_FAILED_CACHE:
                        requestCall.setCacheType(CACHE_TYPE_CACHE);
                        requestCache(requestCall, callback);

                        break;

                    default:
                        try {
                            callback.onFailure(call, e);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        } finally {
                            try {
                                callback.onAfter();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                        break;
                }
            }
        });
    }

    private void senSuccResultCallback(final Call call, final Object response,
                                       final BaseCallback callback, final RequestCall requestCall) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onResponse(call, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        callback.onAfter();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                switch (requestCall.getCacheType()) {
                    case CACHE_TYPE_CACHE_AND_NET:
                        requestCall.setCacheType(CACHE_TYPE_NET);
                        requestNet(requestCall, callback);
                        break;
                }
            }
        });
    }

    public void cancel(Object tag) {

        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public void runOnUI(Runnable runnable) {
        mUIHandler.post(runnable);
    }

    public ApiClient addGloableHeaders(Map<String, String> headers) {
        gloableHeaders = headers;
        return this;
    }

    public Map<String, String> getGloabalHeaders() {
        return gloableHeaders;
    }

    public ApiClient addGloableParams(Map<String, String> params) {
        gloableParams = params;
        return this;
    }

    public Map<String, String> getGloabalParams() {
        return gloableParams;
    }

    private void saveCache(String responsString, String cacheTag) {
        try {
            if (responsString != null) {
                ACache.get().put(cacheTag, responsString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveCache(Serializable cache, String cacheTag) {
        try {
            if (cache != null) {
                ACache.get().put(cacheTag, cache);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getCache(String cacheTag) {

        return ACache.get().getAsObject(cacheTag);
    }
}
