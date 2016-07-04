package com.wz.myapp.net.okhttputils.builder;

import com.wz.myapp.net.okhttputils.helper.HasParamsable;
import com.wz.myapp.net.okhttputils.request.GetRequest;
import com.wz.myapp.net.okhttputils.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dell on 2016/6/24.
 */
public class GetBuilder extends BaseBuilder<GetBuilder> implements HasParamsable {

    @Override
    public RequestCall build() {

        return new GetRequest(url, tag, headers, params, cacheKey, isAddGloabalHeaders, isAddGloabalParams,cacheType).build();
    }

    @Override
    public GetBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public GetBuilder addParam(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }
}
