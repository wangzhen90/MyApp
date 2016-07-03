package com.wz.myapp;

import android.app.Application;

import com.wz.myapp.net.okhttputils.ApiClient;

import java.util.HashMap;

/**
 * Created by dell on 2016/1/4.
 */
public class AppApplication extends Application {

    static AppApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        HashMap<String, String> headers = new HashMap<>();
        //headers.put("Cookie", "x-ienterprise-passport=\"rFFKs4EFJ+tmDt/B6PIWE+jFP2SrL4Lw5VO7Sfa9BPA=\";userId=\"78734\"");
        headers.put("Cookie", "x-ienterprise-passport=\"TmnV7MjKiQjFqN5oPVJoBN1tOoLd2/YX\";userId=\"101\"");
        headers.put("charset", "UTF-8");
        headers.put("Accept-Encoding", "gzip");

        HashMap<String, String> params = new HashMap<>();

        params.put("source", 1 + "");
        params.put("os", 22 + "");
        params.put("model", "motorola+victara");
        params.put("cache_key", 78734 + "");
        params.put("appType", "0");
        params.put("_vs", "4.1.4");

        ApiClient.getInstance()
                .addGloableHeaders(headers)
                .addGloableParams(params);

    }

    public static Application getInstance() {
        return instance;
    }

}
