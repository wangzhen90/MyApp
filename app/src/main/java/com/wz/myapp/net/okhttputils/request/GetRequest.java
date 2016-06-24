package com.wz.myapp.net.okhttputils.request;

import android.net.Uri;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by dell on 2016/6/24.
 */
public class GetRequest extends BaseRequest {

    public GetRequest(String url, Object tag, Map<String, String> headers, Map<String, String> params, String cacheKey) {
        super(url, tag, headers, params, cacheKey);
    }

    @Override
    RequestBody buildRequestBody() {

        return null;
    }

    @Override
    Request buildRequest(RequestBody requestBody) {
        if(params != null){
            appendParams(url,params);
        }
        return builder.build();
    }

    protected String appendParams(String url, Map<String, String> params)
    {
        if (url == null || params == null || params.isEmpty())
        {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }
}
