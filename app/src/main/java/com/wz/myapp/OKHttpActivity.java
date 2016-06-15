package com.wz.myapp;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.util.Log;


public class OKHttpActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    static final String TAG = "okTest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
//        testHeader();
//        testGetSync();
        testGetAsync();

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

                    Log.e("okTest",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

    void testGetAsync(){
        Request request = new Request.Builder()
                .url("https://github.com/hongyangAndroid")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                System.out.println(response.body().string());
            }
        });



    }


}
