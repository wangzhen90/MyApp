package com.wz.myapp.net.okhttputils.builder;

import com.wz.myapp.net.okhttputils.request.RequestCall;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2016/7/3.
 */
public class PostStringBuilder extends BaseBuilder<PostStringBuilder> {

  protected String content;
  protected MediaType mediaType;


  public PostStringBuilder content(String content)
  {
    this.content = content;
    return this;
  }

  public PostStringBuilder mediaType(MediaType mediaType)
  {
    this.mediaType = mediaType;
    return this;
  }

  @Override public RequestCall build() {
    return null;
  }
}
