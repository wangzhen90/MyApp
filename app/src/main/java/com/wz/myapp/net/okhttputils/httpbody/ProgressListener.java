package com.wz.myapp.net.okhttputils.httpbody;

interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
  }