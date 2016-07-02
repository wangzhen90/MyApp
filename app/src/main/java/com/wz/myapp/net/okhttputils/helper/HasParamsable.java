package com.wz.myapp.net.okhttputils.helper;

import com.wz.myapp.net.okhttputils.builder.BaseBuilder;

import java.util.Map;

/**
 * Created by zhy on 16/3/1.
 */
public interface HasParamsable
{
    BaseBuilder params(Map<String, String> params);
    BaseBuilder addParam(String key, String val);
}
