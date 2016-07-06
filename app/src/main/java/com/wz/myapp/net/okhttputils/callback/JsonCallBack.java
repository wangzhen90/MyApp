package com.wz.myapp.net.okhttputils.callback;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wz.myapp.AppApplication;
import com.wz.myapp.net.okhttputils.helper.TypeAdapters;
import com.wz.myapp.net.okhttputils.testmodle.JsonBase;
import android.util.Log;
import android.widget.Toast;

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
//            String str = ""+new String(body.string().getBytes(),"utf-8") ;
            t = gson.fromJson(body.string(), getType());//not toString()
//            t = (T)gson.fromJson(body.string(), test.class);//not toString()
//            t = (T) gson.fromJson(body.charStream(), JsonBase.class);
        }
        return t;
    }

    class test{
        Integer scode;
    }
}
