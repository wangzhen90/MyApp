package com.wz.myapp.net.okhttputils.callback;

import com.google.gson.Gson;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by dell on 2016/6/27.
 */
public abstract class JsonCallBack<T extends Object> extends BaseCallback<T> {

    private Gson gson = new Gson();
    private Class<T> clazz;

    public JsonCallBack(Class<T> clazz) {
        this.clazz = clazz;
    }


    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {

        ResponseBody body = response.body();
        T t = null;
        if (body != null) {
            t = gson.fromJson(body.toString(), clazz);
        }
        return t;
    }
}
