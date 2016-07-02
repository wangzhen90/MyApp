package com.wz.myapp.net.okhttputils.testmodle;

import java.util.ArrayList;

/**
 * Created by dell on 2016/6/28.
 */
public class JsonBacklogs extends JsonBase {

    public ObjectWrapper body;

    public class ObjectWrapper {
        public ArrayList<JsonBacklog> unDealLists = new ArrayList<>();
    }
}
