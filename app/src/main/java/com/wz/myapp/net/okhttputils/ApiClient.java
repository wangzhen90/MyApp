package com.wz.myapp.net.okhttputils;

import com.wz.myapp.net.okhttputils.builder.GetBuilder;
import com.wz.myapp.net.okhttputils.callback.BaseCallback;
import com.wz.myapp.net.okhttputils.request.GetRequest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dell on 2016/5/30.
 */
public class ApiClient {

    private ApiClient INSTANCE;
    private OkHttpClient mOkHttpClient;
    public static final int CONNECT_TIMEOUT = 10;
    public static final int WRITE_TIMEOUT = 10;
    public static final int READ_TIMEOUT = 30;

    private ApiClient() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    public ApiClient getInstance() {

        if (INSTANCE == null) {
            synchronized ((ApiClient.class)) {
                if (INSTANCE == null) {
                    INSTANCE = new ApiClient();
                }
            }
        }
        return INSTANCE;
    }

    public GetBuilder get() {
        return new GetBuilder();
    }

    public void excute(Request request,BaseCallback callback){

//        mOkHttpClient.newCall(request).enqueue(callback);


    }

    public void enqueue(Request request, final BaseCallback callback){


        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(callback != null){
                    try {
                        callback.parseNetworkResponse(response);

                        callback.onResponse(call,);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });


    }




}
