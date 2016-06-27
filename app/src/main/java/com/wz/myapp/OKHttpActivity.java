package com.wz.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wz.myapp.net.okhttputils.response.ProgressResponseBody;
import com.wz.myapp.net.okhttputils.callback.JsonCallBack;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OKHttpActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    static final String TAG = "okTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
//        testHeader();
//        testGetSync();
//        testGetAsync();
//        testPostString();
        testGetFile();
    }

    void testHeader() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("https://github.com/hongyangAndroid")
//                            .header("User-Agent", "OkHttp Headers.java")
//                            .addHeader("Accept", "application/json; q=0.5")
//                            .addHeader("Accept", "application/vnd.github.v3+json")

                            .build();

                    Response response = client.newCall(request).execute();

                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code" + response);

                    Log.e("testHeader", "Server: " + response.header("Server"));
                    Log.e("testHeader", "Date: " + response.header("Date"));
                    Log.e("testHeader", "Set-Cookie: " + response.headers("Set-Cookie"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();


    }


    void testGetSync() {

        new Thread(new Runnable() {
            private final OkHttpClient client = new OkHttpClient();

            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("https://github.com/hongyangAndroid")
                            .build();

                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        Log.e("okTest", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    Log.e("okTest", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    void testGetAsync() {
        Request request = new Request.Builder()
                .url("https://github.com/hongyangAndroid")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                System.out.println(response.body().string());
            }
        });


    }


    void testPostString() {

        final MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("text/x-markdown; charset=utf-8");
        final OkHttpClient client = new OkHttpClient();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String postBody = ""
                            + "Releases\n"
                            + "--------\n"
                            + "\n"
                            + " * _1.0_ May 6, 2013\n"
                            + " * _1.1_ June 15, 2013\n"
                            + " * _1.2_ August 11, 2013\n";

                    Request request = new Request.Builder()
                            .url("https://api.github.com/markdown/raw")
                            .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                            .build();

                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    void testGetFile(){
        final ProgressResponseBody.ProgressListener progressListener = new ProgressResponseBody.ProgressListener() {
            @Override public void update(long bytesRead, long contentLength, boolean done) {
                System.out.println(bytesRead);
                System.out.println(contentLength);
                System.out.println(done);
                System.out.format("%d%% done\n", (100 * bytesRead) / contentLength);
                Log.e("okTest", "bytesRead:"+bytesRead);
                Log.e("okTest", "contentLength:"+contentLength);
                Log.e("okTest", "done:"+done);
                Log.e("okTest", "progress:"+(100 * bytesRead) / contentLength);
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(new Interceptor() {
              @Override public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
              }
            })
            .build();
        Request request = new Request.Builder()
            .url("https://codeload.github.com/jeasonlzy0216/OkHttpUtils/zip/master")
            .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                Log.e("okTest", e.getMessage());
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                Log.e("okTest", response.body().string());
            }
        });
    }


}
