package com.wz.myapp.net.okhttputils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.wz.myapp.net.okhttputils.builder.GetBuilder;
import com.wz.myapp.net.okhttputils.callback.BaseCallback;
import com.wz.myapp.net.okhttputils.intercepter.LoggerInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dell on 2016/5/30.
 */
public class ApiClient {

    private static ApiClient INSTANCE;
    private OkHttpClient mOkHttpClient;

    private final Handler mUIHandler;
    private boolean isShowResponse = true;
    private boolean isShowRequest;

    private ApiClient() {
        mUIHandler = new Handler(Looper.getMainLooper());
        mOkHttpClient = new OkHttpClient.Builder().connectTimeout(ApiConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(ApiConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(ApiConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new LoggerInterceptor(null,isShowResponse))
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

    public GetBuilder get() {
        return new GetBuilder();
    }

    public void excute(Request request, BaseCallback callback) {

        //TODO

    }

    public void enqueue(Request request, BaseCallback callback) {

        if (callback == null) {
            callback = BaseCallback.DEFALT_CALLBACK;
        }
        final BaseCallback finalCallback = callback;
        finalCallback.onBefore();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallback(call, e, finalCallback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Request had cancel!"), finalCallback);
                        return;
                    }
                    Object obj = finalCallback.parseNetworkResponse(response);
                    Log.e("ApiClient","parseNetworkResponse:"+obj.getClass() + ","+obj);
                    senSuccResultCallback(call, obj, finalCallback);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback);
                    e.printStackTrace();
                } finally {
                    if (response != null) {
                        response.close();
                    }
                }
            }
        });
    }

    private void sendFailResultCallback(final Call call, final Exception e,
                                        final BaseCallback callback) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onFailure(call, e);
                    callback.onAfter();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void senSuccResultCallback(final Call call, final Object response,
                                       final BaseCallback callback) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onResponse(call, response);
                    callback.onAfter();
                } catch (Exception e) {
                    e.printStackTrace();
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

}
