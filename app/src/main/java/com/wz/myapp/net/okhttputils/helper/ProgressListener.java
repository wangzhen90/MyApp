package com.wz.myapp.net.okhttputils.helper;

public interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
  }