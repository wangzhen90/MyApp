package com.wz.myapp.net.okhttputils.callback;

import android.content.res.TypedArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.wz.myapp.net.okhttputils.helper.TypeAdapters;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by dell on 2016/6/27.
 */
public abstract class JsonCallBack<T extends Object> extends BaseCallback<T> {

//    private Gson gson = new Gson();


    private Gson gson;

    public JsonCallBack() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(long.class,TypeAdapters.LONG);
        gsonBuilder.registerTypeAdapter(double.class,TypeAdapters.DOUBLE);
        gsonBuilder.registerTypeAdapter(int.class,TypeAdapters.INTEGER);
        gsonBuilder.registerTypeAdapter(float.class,TypeAdapters.FLOAT);
        gson = gsonBuilder.create();
    }


    @Override
    public T parseNetworkResponse(Response response) throws Exception {

        ResponseBody body = response.body();
        T t = null;
        if (body != null) {
            t = gson.fromJson(body.string(), getType());//²»ÒªÐ´³ÉtoString()
//            t = gson.fromJson(body.charStream(), clazz);
        }
        return t;
    }
}
