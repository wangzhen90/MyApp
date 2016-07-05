package com.wz.myapp.net.okhttputils.request;

import com.wz.myapp.net.okhttputils.ApiClient;
import com.wz.myapp.net.okhttputils.callback.BaseCallback;
import com.wz.myapp.net.okhttputils.helper.ProgressListener;
import com.wz.myapp.net.okhttputils.httpbody.ProgressRequestBody;
import java.io.File;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016/7/5.
 */
public class PostFileRequest extends BaseRequest{
  private static MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");
  private File file;
  public PostFileRequest(String url, Object tag, Map<String, String> headers,
      Map<String, String> params, String cacheKey, boolean isAddGloabalHeaders,
      boolean isAddGloabalParams, int cacheType,File file) {
    super(url, tag, headers, params, cacheKey, isAddGloabalHeaders, isAddGloabalParams, cacheType);
    this.file = file;
  }

  @Override RequestBody buildRequestBody() {
    return RequestBody.create(MEDIA_TYPE_STREAM,file);
  }

  @Override Request buildRequest(RequestBody requestBody) {
    return builder.post(buildRequestBody()).build();
  }

  @Override
  RequestBody detectRequestBody(RequestBody requestBody, final BaseCallback callback) {
    if (file == null || callback == null) return requestBody;
    ProgressRequestBody
        progressRequestBody = new ProgressRequestBody(requestBody, new ProgressListener() {
      @Override
      public void update(final long bytesRead, final long contentLength, final boolean done) {
        ApiClient.getInstance().runOnUI(new Runnable() {
          @Override
          public void run() {
            callback.onProgress(bytesRead, contentLength, done);
          }
        });
      }
    });
    return progressRequestBody;
  }
}
