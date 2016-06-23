package com.wz.myapp.net.okhttputils.request;

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

    BaseRequest(String url, Object tag, Map<String, String> headers, Map<String, String> params, String cacheKey) {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        this.cacheKey = cacheKey;

        if (url == null) {
            throw new IllegalArgumentException("url can not be null!");
        }

        initBuilder();
    }

    private void initBuilder() {
        builder.url(url);
        appendHeaders();
    }

    void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }


    abstract RequestBody buildRequestBody();

    /**
     * Different methods to create different request,example:get,post.
     *
     * @return
     */
    abstract Request buildRequest(RequestBody requestBody);

    public Request generateRequest() {
        return buildRequest(buildRequestBody());
    }

}
