package com.wz.myapp.net.okhttputils.builder;

import com.wz.myapp.net.okhttputils.request.GetRequest;

/**
 * Created by dell on 2016/6/24.
 */
public class GetBuilder extends BaseBuilder<GetBuilder,GetRequest>{

    @Override
    public GetRequest build() {

        return new GetRequest(url,tag,headers,params,cacheKey);
    }
}
