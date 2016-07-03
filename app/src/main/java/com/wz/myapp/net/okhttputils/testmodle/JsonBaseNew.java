package com.wz.myapp.net.okhttputils.testmodle;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/7/3.
 */
public class JsonBaseNew<T> {
  public String scode;
  public Header head;
  public JsonObject body;
  public Type getType(){
    Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    if(type instanceof Class){
      return type;
    }else{
      return new TypeToken<T>(){}.getType();
    }
  }

}
