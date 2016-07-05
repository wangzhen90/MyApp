package com.wz.myapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.wz.myapp.net.okhttputils.ApiClient;
import com.wz.myapp.net.okhttputils.ApiConfig;
import com.wz.myapp.net.okhttputils.cache.ACache;
import com.wz.myapp.net.okhttputils.callback.BaseCallback;
import com.wz.myapp.net.okhttputils.callback.FileCallback;
import com.wz.myapp.net.okhttputils.callback.JsonCallBack;
import com.wz.myapp.net.okhttputils.helper.ResFileHelper;
import com.wz.myapp.net.okhttputils.helper.TypeAdapters;
import com.wz.myapp.net.okhttputils.response.ProgressResponseBody;
import com.wz.myapp.net.okhttputils.testmodle.CacheResult;
import com.wz.myapp.net.okhttputils.testmodle.JsonBacklogs;

import com.wz.myapp.net.okhttputils.testmodle.JsonBacklogsNew;
import com.wz.myapp.net.okhttputils.testmodle.JsonBacklogsNew2;
import com.wz.myapp.net.okhttputils.testmodle.JsonBase;
import com.wz.myapp.net.okhttputils.testmodle.JsonBaseNew;
import java.io.File;
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

  @Override protected void onCreate(Bundle savedInstanceState) {
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
      @Override public void run() {
        try {
          Request request = new Request.Builder().url("https://github.com/hongyangAndroid")
              //                            .header("User-Agent", "OkHttp Headers.java")
              //                            .addHeader("Accept", "application/json; q=0.5")
              //                            .addHeader("Accept", "application/vnd.github.v3+json")

              .build();

          Response response = client.newCall(request).execute();

          if (!response.isSuccessful()) throw new IOException("Unexpected code" + response);

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

      @Override public void run() {
        try {
          Request request = new Request.Builder().url("https://github.com/hongyangAndroid").build();

          Response response = client.newCall(request).execute();
          if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

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
    Request request = new Request.Builder().url(
        "https://crm-dev6.xiaoshouyi.com/mobile/count/undo-counts.action?source=1&os=22&model=motorola+victara&cache_key=78734&appType=0&_vs=4.1.4")
        .header("Cookie",
            "x-ienterprise-passport=\"rFFKs4EFJ+tmDt/B6PIWE+jFP2SrL4Lw5VO7Sfa9BPA=\";userId=\"78734\"")
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

    ApiClient.getInstance()
        .get()
        .url(ApiConfig.HOSTS + "/count/undo-counts.action")
        .build()
        .enqueue(new JsonCallBack<JsonBacklogs>() {
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

  public void testApiGetFile() {

    //        ApiClient.getInstance().get()

  }

  void testPostString() {

    final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    final OkHttpClient client = new OkHttpClient();

    new Thread(new Runnable() {
      @Override public void run() {
        try {
          String postBody = "" + "Releases\n" + "--------\n" + "\n" + " * _1.0_ May 6, 2013\n"
              + " * _1.1_ June 15, 2013\n" + " * _1.2_ August 11, 2013\n";

          Request request = new Request.Builder().url("https://api.github.com/markdown/raw")
              .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
              .build();

          Response response = client.newCall(request).execute();
          if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
          System.out.println(response.body().string());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  void testGetFile() {
    final ProgressResponseBody.ProgressListener progressListener =
        new ProgressResponseBody.ProgressListener() {
          @Override public void update(long bytesRead, long contentLength, boolean done) {
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

    OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
            .build();
      }
    }).build();
    Request request = new Request.Builder().url(
        "https://codeload.github.com/jeasonlzy0216/OkHttpUtils/zip/master").build();

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
        "title: \"����_������ stand A chance ��999999.0��\",\n" +
        "userName: \"wz\",\n" +
        "startDate: 1465786476660,\n" +
        "endDate: \"\",\n" +
        "belongId: 64,\n" +
        "type: \"approval\",\n" +
        "count: 23\n" +
        "},\n" +
        "{\n" +
        "title: \"�ձ� ��2016-06-06��\",\n" +
        "userName: \"��ԲԲ\",\n" +
        "startDate: 1465201904750,\n" +
        "endDate: \"\",\n" +
        "belongId: 101222,\n" +
        "type: \"workreport\",\n" +
        "count: 2\n" +
        "},\n" +
        "{\n" +
        "title: \"����\",\n" +
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

    Gson gson;
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(long.class, TypeAdapters.LONG);
    gsonBuilder.registerTypeAdapter(double.class, TypeAdapters.DOUBLE);
    gsonBuilder.registerTypeAdapter(int.class, TypeAdapters.INTEGER);
    gsonBuilder.registerTypeAdapter(float.class, TypeAdapters.FLOAT);
    gson = gsonBuilder.create();
    //JsonBacklogs jsonBase = gson.fromJson(jsonStr, JsonBacklogs.class);
    //Log.e("test_gson", "jsonBase:" + jsonBase.body.unDealLists.size());
    JsonBaseNew<JsonBacklogsNew> jsonBase = gson.fromJson(jsonStr, JsonBaseNew.class);
    Log.e("test_gson", "jsonBase:" + jsonBase.body.getClass());

    //直接在JsonBase中写 T body；解析的时候会自动被解析成LinkedHsahMap

    try {
      JsonBacklogsNew backlogs = gson.fromJson(gson.toJson(jsonBase.body), JsonBacklogsNew.class);
      Log.e("test_gson", "backlogs:" + backlogs.unDealLists.size());
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      JsonBacklogsNew2 backlogs = gson.fromJson(gson.toJson(jsonBase.body), JsonBacklogsNew2.class);
      Log.e("test_gson", "backlogs:" + backlogs.unDealLists.size());
    } catch (Exception e) {
      e.printStackTrace();
    }

    ACache.get().put("test_json",jsonStr);
      Log.e("Acache","respons:"+ACache.get().getAsObject("test_json"));

  }

  public void test_api_get(View view) {
    testApiGet();
  }

  public void test_api_get_file(View view) {
    ApiClient.getInstance().get().url(
        "https://xsybucket.s3.cn-north-1.amazonaws.com.cn/101/2016/06/29/e46ee726-2eb3-4028-9a2e-b15582db1d18.pdf")
        //                .url("https://xsybucket.s3.cn-north-1.amazonaws.com.cn/101/2016/07/01/d01b63b7-9844-4ea6-a0cb-8e1ab0626a1d.txt")
        //                .url("https://xsybucket.s3.cn-north-1.amazonaws.com.cn/101/2016/07/01/0fb89dea-0c82-49a2-8b76-35eebea6278d.jpg")
        .addGlobalHeaders(true)
        .addGlobalParams(false)
        .build()
        .enqueue(
                new FileCallback(Environment.getExternalStorageDirectory() + "/1myapp", "test1.pdf") {
                    @Override
                    public void onResponse(Call call, File response) {

                    }

                    @Override
                    public void onFailure(Call call, Exception error) {

                    }

                    @Override
                    public void onProgress(long bytesRead, long contentLength, boolean done) {
                        Log.e("getFile", "progress:" + bytesRead / contentLength + ",hasRead:" + bytesRead
                                + ",contentLength:" + contentLength + ",done:" + done);
                    }

                    @Override
                    public void onAfter() {
                        openFile(OKHttpActivity.this,
                                Environment.getExternalStorageDirectory() + "/1myapp" + "test1.pdf");
                    }
                });
  }

  public static void openFile(Context context, String path) {
    Intent intent = ResFileHelper.openFile(path);
    try {
      context.startActivity(intent);
    } catch (ActivityNotFoundException exception) {
      intent = ResFileHelper.getAllIntent(path);
      context.startActivity(intent);
    }
  }

  public void test_get_cache(View view) {
    ApiClient.getInstance()
        .get()
        .url("http://api.stay4it.com/test/jdsjlzx.php")
        .cacheKey("http://api.stay4it.com/test/jdsjlzx.php")
        .addGlobalHeaders(false)
        .addGlobalHeaders(false)
//        .cacheType(ApiClient.CACHE_TYPE_CACHE)
        .build()
        .enqueue(new JsonCallBack<CacheResult>() {
          @Override public void onResponse(Call call, CacheResult response) {
            Log.e("test_get_cache",
                "CacheResult:" + response.resut.data.list.size() + ",cache:" + ((CacheResult)ACache.get().getAsObject("http://api.stay4it.com/test/jdsjlzx.php")).resut.data.list.get(0).desc);
          }

          @Override public void onFailure(Call call, Exception error) {
            Log.e("test_get_cache", "failed,error:" + error.getMessage());
          }
        });
  }

    public void test_post_form(View view){
        ApiClient.getInstance().postForm()
                .url("https://crm.xiaoshouyi.com/mobile/global/login.action")
                .addGlobalHeaders(false)
                .addParams("password","111111")
                .addParams("passport","liuzq@9.cn")
                .build()
                .enqueue(new JsonCallBack() {
                    @Override
                    public void onResponse(Call call, Object response) {

                    }

                    @Override
                    public void onFailure(Call call, Exception error) {

                    }
                });


    }

}
