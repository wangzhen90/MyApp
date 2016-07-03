package com.wz.myapp.net.okhttputils;

/**
 * Created by dell on 2016/6/28.
 */
public class ApiConfig {
    public static final int CONNECT_TIMEOUT = 10;
    public static final int WRITE_TIMEOUT = 10;
    public static final int READ_TIMEOUT = 30;



    //public static final String HOSTS = "https://crm-dev6.xiaoshouyi.com/mobile";
    public static final String HOSTS = "https://crm.xiaoshouyi.com/mobile";
    public static final String DOWNLOAD_HOSTS = "https://xsybucket.s3.cn-north-1.amazonaws.com.cn";
    public static  boolean isShowResponse;
    public static  boolean debug;

    public static void setDebug(boolean debug) {
        ApiConfig.debug = debug;
    }

}
