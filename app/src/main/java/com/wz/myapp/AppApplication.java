package com.wz.myapp;

import android.app.Application;

/**
 * Created by dell on 2016/1/4.
 */
public class AppApplication extends Application {

   static AppApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;


    }

    public static Application getInstance(){
        return instance;
    }

}
