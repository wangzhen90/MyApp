package com.wz.myapp.net.okhttputils.request;

import com.wz.myapp.net.okhttputils.ApiClient;
import com.wz.myapp.net.okhttputils.ApiConfig;
import com.wz.myapp.net.okhttputils.callback.BaseCallback;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by dell on 2016/6/30.
 */
public class RequestCall {
  private BaseRequest okHttpRequest;
  private Request request;
  private int cacheType = 0;

  public Call getCall() {
    return call;
  }

  public Request getRequest() {
    return request;
  }

  private Call call;
  private long readTimeOut;
  private long writeTimeOut;
  private long connTimeOut;
  private ArrayList<Interceptor> interceptors;

  private OkHttpClient clone;

  public RequestCall readTimeOut(long readTimeOut) {
    this.readTimeOut = readTimeOut;
    return this;
  }

  public RequestCall writeTimeOut(long writeTimeOut) {
    this.writeTimeOut = writeTimeOut;
    return this;
  }

  public RequestCall connTimeOut(long connTimeOut) {
    this.connTimeOut = connTimeOut;
    return this;
  }

  public RequestCall(BaseRequest request) {
    this.okHttpRequest = request;
  }

  private Call buildCall(BaseCallback callback) {

    request = generateRequest(callback);
    clone = getClient();
    call = clone.newCall(request);
    return call;
  }

  private Request generateRequest(BaseCallback callback) {

    return okHttpRequest.generateRequest(callback);
  }

  public void enqueue(BaseCallback callback) {
    buildCall(callback);
    ApiClient.getInstance().enqueue(this, callback);
  }

  public OkHttpClient getClient() {
    if (readTimeOut > 0 || writeTimeOut > 0 || connTimeOut > 0 || (interceptors != null
        && !interceptors.isEmpty())) {
      readTimeOut = readTimeOut > 0 ? readTimeOut : ApiConfig.READ_TIMEOUT;
      writeTimeOut = writeTimeOut > 0 ? writeTimeOut : ApiConfig.WRITE_TIMEOUT;
      connTimeOut = connTimeOut > 0 ? connTimeOut : ApiConfig.CONNECT_TIMEOUT;

      OkHttpClient.Builder clientBuilder = ApiClient.getInstance()
          .getOkHttpClient()
          .newBuilder()
          .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
          .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
          .connectTimeout(connTimeOut, TimeUnit.MILLISECONDS);

      if (!interceptors.isEmpty()) {
        for (Interceptor interceptor : interceptors) {
          clientBuilder.addInterceptor(interceptor);
        }
      }
      clone = clientBuilder.build();
    } else {
      clone = ApiClient.getInstance().getOkHttpClient();
    }
    return clone;
  }

  private void addIntercepter(Interceptor interceptor) {
    if (interceptors == null) {
      interceptors = new ArrayList<>();
    }
    interceptors.add(interceptor);
  }

  public String getCacheTag() {
    return okHttpRequest.cacheKey;
  }

  public int getCacheType() {
    return okHttpRequest.cacheType;
  }

  public void setCacheType(int cacheType) {
    okHttpRequest.cacheType = cacheType;
  }
}
