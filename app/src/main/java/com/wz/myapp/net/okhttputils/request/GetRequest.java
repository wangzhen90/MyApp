package com.wz.myapp.net.okhttputils.request;

import android.net.Uri;

import com.wz.myapp.net.okhttputils.ApiClient;
import com.wz.myapp.net.okhttputils.callback.BaseCallback;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by dell on 2016/6/24.
 */
public class GetRequest extends BaseRequest {

  public GetRequest(String url, Object tag, Map<String, String> headers, Map<String, String> params,
      String cacheKey, boolean isAddGloableHeaders, boolean isAddGloablParams,int cacheType) {
    super(url, tag, headers, params, cacheKey, isAddGloableHeaders, isAddGloablParams,cacheType);
  }

  @Override RequestBody buildRequestBody() {

    return null;
  }

  @Override Request buildRequest(RequestBody requestBody) {

    Map<String, String> gloablParams = ApiClient.getInstance().getGloabalParams();
    if (isAddGloabalParams) {
      url = appendParams(url, gloablParams);
    }

    if (params != null) {
      url = appendParams(url, params);
    }
    builder.url(url);
    return builder.build();
  }


}
