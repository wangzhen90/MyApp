package com.wz.myapp.net.okhttputils.callback;

import com.wz.myapp.net.okhttputils.ApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by dell on 2016/6/29.
 */
public abstract class FileCallback extends BaseCallback<File> {

    private String destFileDir;
    private String destFileName;

    public FileCallback(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }


    @Override
    public File parseNetworkResponse(Response response) throws Exception {
        return saveFile(response);
    }

    public File saveFile(Response response) throws IOException {
        InputStream is;
        byte[] buffer = new byte[1024];
        int len;
        FileOutputStream os = null;

        try {
            is = response.body().byteStream();
            final long totalLength = response.body().contentLength();
            long downLength = 0;
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdir();
            }

            File file = new File(dir, destFileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            os = new FileOutputStream(file);
            //读写长度要注意
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                downLength += len;
                os.write(buffer, 0, len);
                final long finalDownLength = downLength;
                ApiClient.getInstance().runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        onProgress(finalDownLength, totalLength, finalDownLength == totalLength);
                    }
                });
            }
            os.flush();
            return file;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (os != null) os.close();
            response.close();
        }
    }

}
