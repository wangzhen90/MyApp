package com.wz.myapp.net.okhttputils.request;

import java.util.Map;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2016/7/3.
 */
public class PostRequest extends BaseRequest {

  private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");

  private String content;
  private MediaType mediaType;

  public PostRequest(String url, Object tag, Map<String, String> headers,
      Map<String, String> params, String cacheKey, boolean isAddGloabalHeaders,
      boolean isAddGloabalParams, String content, MediaType mediaType,int cacheType) {
    super(url, tag, headers, params, cacheKey, isAddGloabalHeaders, isAddGloabalParams,cacheType);
    this.content = content;
    this.mediaType = mediaType;

    if (this.content == null) {
      throw new IllegalArgumentException("the content can not be null !");
    }
    if (this.mediaType == null) {
      this.mediaType = MEDIA_TYPE_PLAIN;
    }
  }

  @Override RequestBody buildRequestBody() {

    return RequestBody.create(mediaType, content);
  }

  @Override Request buildRequest(RequestBody requestBody) {
    return builder.post(buildRequestBody()).build();
  }
}
