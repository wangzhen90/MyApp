package com.wz.myapp.net.okhttputils.builder;

import com.wz.myapp.net.okhttputils.request.PostFileRequest;
import com.wz.myapp.net.okhttputils.request.RequestCall;
import java.io.File;


/**
 * Created by Administrator on 2016/7/5.
 */
public class PostFileBuilder extends BaseBuilder<PostFileBuilder> {

  private File file;

  @Override public RequestCall build() {

    return new PostFileRequest(url, tag, headers, params, cacheKey, isAddGloabalHeaders,
        isAddGloabalParams, cacheType, file).build();
  }

  public PostFileBuilder file(File file) {
    this.file = file;
    return this;
  }
}
