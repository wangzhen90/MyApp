package com.wz.myapp.net.okhttputils.builder;

import com.wz.myapp.net.okhttputils.request.RequestCall;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2016/7/3.
 */
public class PostBuilder extends PostStringBuilder {

  public PostBuilder() {
    mediaType = MediaType.parse("application/json; charset=utf-8");
  }

  @Override public RequestCall build() {
    return null;
  }
}
