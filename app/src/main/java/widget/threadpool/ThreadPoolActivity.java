package widget.threadpool;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.wz.myapp.R;

public class ThreadPoolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
        String saveName = "test_task_1";


        UploadManager.getInstance().uploadAllComments(saveName);

//        for (int i = 0; i < 12; i++) {
//            final int index = i;
//            UploadTask<String> task = new UploadTask<String>("任务：" + i, System.currentTimeMillis() + "", saveName) {
//                @Override
//                void uploadApi(UploadTask<String> task) {
//                    SystemClock.sleep(2000);
//                    if (index == 3 || index == 5 || index == 20 || index == 11) {
//                        task.onFaild();
//                    } else {
//                        task.onSuccess();
//                    }
//                    Log.e("task test", task.mTask);
//
//
//                }
//            };
//            UploadManager.getInstance().upload(task);
//        }


    }

}
