package com.wz.myapp.net.okhttputils.helper;

import com.wz.myapp.net.okhttputils.builder.BaseBuilder;

import java.util.Map;

/**
 * Created by dell on 2016/6/29.
 */
public interface FormParamsable {
    BaseBuilder formParams(Map<String, String> params);

    BaseBuilder addFormParam(String key, String val);
}
