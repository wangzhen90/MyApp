package com.wz.myapp.net.okhttputils.request;

import com.wz.myapp.net.okhttputils.ApiClient;
import com.wz.myapp.net.okhttputils.builder.PostFormBuilder;
import com.wz.myapp.net.okhttputils.callback.BaseCallback;
import com.wz.myapp.net.okhttputils.helper.ProgressListener;
import com.wz.myapp.net.okhttputils.httpbody.ProgressRequestBody;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by dell on 2016/7/5.
 */

public class PostFormRequest extends BaseRequest {
    private List<PostFormBuilder.FileInput> files;

    public PostFormRequest(String url, Object tag, Map<String, String> headers, Map<String, String> params, String cacheKey, boolean isAddGloabalHeaders, boolean isAddGloabalParams, int cacheType, List<PostFormBuilder.FileInput> files) {
        super(url, tag, headers, params, cacheKey, isAddGloabalHeaders, isAddGloabalParams, cacheType);
        this.files = files;
    }

    @Override
    RequestBody buildRequestBody() {
        if (files == null || files.isEmpty()) {
            FormBody.Builder builder = new FormBody.Builder();
            addParams(builder);
            return builder.build();
        } else {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            addParams(builder);

            for (int i = 0; i < files.size(); i++) {
                PostFormBuilder.FileInput fileInput = files.get(i);
                RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileInput.filename)), fileInput.file);
                builder.addFormDataPart(fileInput.key, fileInput.filename, fileBody);
            }
            return builder.build();
        }
    }

    @Override
    RequestBody detectRequestBody(RequestBody requestBody, final BaseCallback callback) {
        if (files == null || files.isEmpty() || callback == null) return requestBody;
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(requestBody, new ProgressListener() {
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

    @Override
    Request buildRequest(RequestBody requestBody) {

        if (isAddGloabalParams) {
            Map<String, String> gloablParams = ApiClient.getInstance().getGloabalParams();
            url = appendParams(url, gloablParams);
            builder.url(url);
        }

        return builder.post(requestBody).build();
    }

    private void addParams(MultipartBody.Builder builder) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, params.get(key)));
            }
        }
    }

    private void addParams(FormBody.Builder builder) {
        if (params != null) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


}
