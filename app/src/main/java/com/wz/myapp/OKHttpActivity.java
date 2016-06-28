package com.wz.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wz.myapp.net.okhttputils.ApiClient;
import com.wz.myapp.net.okhttputils.callback.JsonCallBack;
import com.wz.myapp.net.okhttputils.helper.TypeAdapters;
import com.wz.myapp.net.okhttputils.response.ProgressResponseBody;
import com.wz.myapp.net.okhttputils.testmodle.JsonBacklogs;

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
//        testGetFile();
//        testApiGet();
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
                .url("https://crm-dev6.xiaoshouyi.com/mobile/count/undo-counts.action?source=1&os=22&model=motorola+victara&cache_key=78734&appType=0&_vs=4.1.4")
                .header("Cookie", "x-ienterprise-passport=\"rFFKs4EFJ+tmDt/B6PIWE+jFP2SrL4Lw5VO7Sfa9BPA=\";userId=\"78734\"")
                .header("charset", "UTF-8")
                .header("Accept-Encoding", "gzip")
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

    public void testApiGet() {

        ApiClient.getInstance().get()
                .addHeader("Cookie", "x-ienterprise-passport=\"rFFKs4EFJ+tmDt/B6PIWE+jFP2SrL4Lw5VO7Sfa9BPA=\";userId=\"78734\"")
                .addHeader("charset", "UTF-8")
                .addHeader("Accept-Encoding", "gzip")
                .url("/count/undo-counts.action?source=1&os=22&model=motorola+victara&cache_key=78734&appType=0&_vs=4.1.4")
                .build().enqueue(new JsonCallBack<JsonBacklogs>() {
            @Override
            public void onResponse(Call call, JsonBacklogs response) {
                Log.e("ApiClient", "onResponse,body response:" + response.body.unDealLists.size());
            }

            @Override
            public void onFailure(Call call, Exception error) {
                Log.e("ApiClient", "onFailure");
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


    void testGetFile() {
        final ProgressResponseBody.ProgressListener progressListener = new ProgressResponseBody.ProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                System.out.println(bytesRead);
                System.out.println(contentLength);
                System.out.println(done);
                System.out.format("%d%% done\n", (100 * bytesRead) / contentLength);
                Log.e("okTest", "bytesRead:" + bytesRead);
                Log.e("okTest", "contentLength:" + contentLength);
                Log.e("okTest", "done:" + done);
                Log.e("okTest", "progress:" + (100 * bytesRead) / contentLength);
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
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
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("okTest", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("okTest", response.body().string());
            }
        });
    }

    public void test_gson(View view) {

        String jsonStr = "{\n" +
                "scode: \"0\",\n" +
                "head: {\n" +
                "uid: 78734,\n" +
                "tid: 40541,\n" +
                "isex: false\n" +
                "},\n" +
                "body: {\n" +
                "unDealLists: [\n" +
                "{\n" +
                "title: \"审批_报销单 stand A chance 【999999.0】\",\n" +
                "userName: \"wz\",\n" +
                "startDate: 1465786476660,\n" +
                "endDate: \"\",\n" +
                "belongId: 64,\n" +
                "type: \"approval\",\n" +
                "count: 23\n" +
                "},\n" +
                "{\n" +
                "title: \"日报 【2016-06-06】\",\n" +
                "userName: \"于圆圆\",\n" +
                "startDate: 1465201904750,\n" +
                "endDate: \"\",\n" +
                "belongId: 101222,\n" +
                "type: \"workreport\",\n" +
                "count: 2\n" +
                "},\n" +
                "{\n" +
                "title: \"各地\",\n" +
                "userName: \"wz\",\n" +
                "startDate: 1462957200000,\n" +
                "endDate: \"\",\n" +
                "belongId: \"\",\n" +
                "type: \"task\",\n" +
                "count: 21\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "}";

        Gson gson ;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(long.class, TypeAdapters.LONG);
        gsonBuilder.registerTypeAdapter(double.class,TypeAdapters.DOUBLE);
        gsonBuilder.registerTypeAdapter(int.class,TypeAdapters.INTEGER);
        gsonBuilder.registerTypeAdapter(float.class,TypeAdapters.FLOAT);
        gson = gsonBuilder.create();
        JsonBacklogs jsonBase = gson.fromJson(jsonStr, JsonBacklogs.class);
        Log.e("test_gson", "jsonBase:" + jsonBase.body.unDealLists.size());


    }

    public void test_api_get(View view) {
        testApiGet();
    }


}
