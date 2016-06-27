package com.wz.myapp.net.okhttputils;

import android.os.Handler;
import android.os.Looper;
import com.wz.myapp.net.okhttputils.builder.GetBuilder;
import com.wz.myapp.net.okhttputils.callback.BaseCallback;
import com.wz.myapp.net.okhttputils.request.GetRequest;

import java.io.IOException;
import java.util.Objects;
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

  private ApiClient INSTANCE;
  private OkHttpClient mOkHttpClient;
  public static final int CONNECT_TIMEOUT = 10;
  public static final int WRITE_TIMEOUT = 10;
  public static final int READ_TIMEOUT = 30;
  private final Handler mUIHandler;

  private ApiClient() {
    mUIHandler = new Handler(Looper.getMainLooper());
    mOkHttpClient = new OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .build();
  }

  public ApiClient getInstance() {

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

    //        mOkHttpClient.newCall(request).enqueue(callback);

  }

  public void enqueue(Request request, BaseCallback callback) {

    if (callback == null) {
      callback = BaseCallback.DEFALT_CALLBACK;
    }
    final BaseCallback finalCallback = callback;
    finalCallback.onBefore();
    mOkHttpClient.newCall(request).enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {
        sendFailResultCallback(call, e, finalCallback);
      }

      @Override public void onResponse(Call call, Response response) throws IOException {

        try {
          if (call.isCanceled()) {
            sendFailResultCallback(call, new IOException("Request had cancel!"), finalCallback);
            return;
          }
          Object obj = finalCallback.parseNetworkResponse(response);
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
      @Override public void run() {
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
      @Override public void run() {
        try {
          callback.onResponse(call, response);
          callback.onAfter();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
}
