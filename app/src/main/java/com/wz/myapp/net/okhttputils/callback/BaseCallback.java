package com.wz.myapp.net.okhttputils.callback;

import java.io.IOException;

import java.util.Objects;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dell on 2016/6/23.
 */
public abstract class BaseCallback<T extends Object> {

    public void onBefore() {

    }

    public void onAfter() {

    }

    public void onProgress(long bytesRead, long contentLength, boolean done) {

    }

    public abstract void onResponse(Call call, T response);

    public abstract void onFailure(Call call, Exception error);

    public abstract T  parseNetworkResponse(Response response) throws Exception;


    public  static BaseCallback DEFALT_CALLBACK = new BaseCallback() {
        @Override public void onResponse(Call call, Object response) {

        }

        @Override public void onFailure(Call call, Exception error) {

        }

        @Override public Object parseNetworkResponse(Response response) throws Exception {
            return null;
        }
    };
}
