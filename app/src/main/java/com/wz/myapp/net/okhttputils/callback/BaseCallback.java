package com.wz.myapp.net.okhttputils.callback;

import java.io.IOException;

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

    public abstract void onResponse(Call call, Response response);

    public abstract void onError(Call call, IOException error);

    public abstract T parseNetworkResponse(Response response) throws Exception;

}
