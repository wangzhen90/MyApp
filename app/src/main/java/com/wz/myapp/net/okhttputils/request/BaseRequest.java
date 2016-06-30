package com.wz.myapp.net.okhttputils.request;

import com.wz.myapp.net.okhttputils.ApiClient;
import com.wz.myapp.net.okhttputils.callback.BaseCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by dell on 2016/6/16.
 */
public abstract class BaseRequest {

    String url;
    Object tag;
    Map<String, String> headers;
    Map<String, String> params;
    String cacheKey;

    Request.Builder builder = new Request.Builder();
    boolean isAddGloabalHeaders = true;
    boolean isAddGloabalParams = true;

    public BaseRequest(String url, Object tag, Map<String, String> headers, Map<String, String> params, String cacheKey, boolean isAddGloabalHeaders, boolean isAddGloabalParams) {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        this.cacheKey = cacheKey;
        this.isAddGloabalHeaders = isAddGloabalHeaders;
        this.isAddGloabalParams = isAddGloabalParams;

        if (url == null) {
            throw new IllegalArgumentException("url can not be null!");
        }

        initBuilder();
    }

    private void initBuilder() {
        builder.url(url);

        if (isAddGloabalHeaders) {
            appendHeaders(ApiClient.getInstance().getGloabalHeaders());
        } else {
            removeHeaders(ApiClient.getInstance().getGloabalHeaders());
        }
        appendHeaders(headers);
    }

    void appendHeaders(Map<String, String> headers) {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    void removeHeaders(Map<String, String> headers) {
        if (headers == null || headers.isEmpty()) return;
        for (String key : headers.keySet()) {
            builder.removeHeader(key);
        }
    }


    abstract RequestBody buildRequestBody();

    /**
     * Different methods to create different request,example:get,post.
     *
     * @return
     */
    abstract Request buildRequest(RequestBody requestBody);

    RequestBody detectRequestBody(RequestBody requestBody, final BaseCallback callback) {
        return requestBody;
    }


    public Request generateRequest(BaseCallback callback) {
        return buildRequest(detectRequestBody(buildRequestBody(), callback));
    }

    public RequestCall build() {
        return new RequestCall(this);
    }


}
