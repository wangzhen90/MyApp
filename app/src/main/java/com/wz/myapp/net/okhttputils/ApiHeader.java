package com.wz.myapp.net.okhttputils;

import android.net.Uri;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dell on 2016/6/29.
 * 添加公共的header和params
 */
public class ApiHeader implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        String url = request.url().toString();

        Uri.Builder builder = Uri.parse(url).buildUpon();

        builder.appendQueryParameter("source",1+"");
        builder.appendQueryParameter("os",22+"");
        builder.appendQueryParameter("model","motorola+victara");
        builder.appendQueryParameter("cache_key",78734+"");
        builder.appendQueryParameter("appType","0");
        builder.appendQueryParameter("_vs","4.1.4");
        url = builder.build().toString();

        request = request.newBuilder()
                .addHeader("Cookie", "x-ienterprise-passport=\"rFFKs4EFJ+tmDt/B6PIWE+jFP2SrL4Lw5VO7Sfa9BPA=\";userId=\"78734\"")
                .addHeader("charset", "UTF-8")
                .addHeader("Accept-Encoding", "gzip")
                .url(url)
                .build();
        return chain.proceed(request);
    }
}
