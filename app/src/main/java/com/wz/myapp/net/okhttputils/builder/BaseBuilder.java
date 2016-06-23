package com.wz.myapp.net.okhttputils.builder;

import com.wz.myapp.net.okhttputils.request.BaseRequest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dell on 2016/6/16.
 */
public abstract class BaseBuilder<T extends BaseBuilder,V extends BaseRequest> {

    String url;
    Object tag;
    Map<String, String> headers;
    Map<String, String> params;
    String cacheKey;

    public T cacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
        return (T) this;
    }

    public T url(String url) {
        this.url = url;
        return (T) this;
    }

    public T tag(Object tag) {
        this.tag = tag;
        return (T) this;
    }

    public T addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return (T) this;
    }

    public T headers(Map<String, String> headers) {
        this.headers = headers;
        return (T) this;
    }

    public abstract V build();
}
