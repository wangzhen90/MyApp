package com.wz.myapp.net.okhttputils.testmodle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/4.
 */
public class CacheResult implements Serializable {

  public String state;
  public Result resut;

  public class Result implements Serializable {
   public DataWrap data;
  }

  public class DataWrap implements Serializable {
    public String total;
    public ArrayList<Data> list;

  }


  public class Data implements Serializable {
    public String desc;
    public String icon;
    public String name;
    public String size;
    public String url;
  }
}
