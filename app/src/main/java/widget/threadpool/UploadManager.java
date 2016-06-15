package widget.threadpool;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

import com.wz.myapp.AppApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by dell on 2016/3/7.
 */
public class UploadManager {

    public static int DEFALT_THREAD_NUM = 3;
    //    private ThreadPoolExecutor executor;
    ScheduledExecutorService mExcutor;
    SharedPreferences sp;

    private Object mLock = new Object();
    /**
     * 缓存上传任务
     */
    private List<UploadTask> mCacheTasks = new ArrayList<UploadTask>();

    private static UploadManager INSTANCE;

    public static UploadManager getInstance() {

        if (INSTANCE == null) {
            synchronized (UploadManager.class) {
                if (INSTANCE == null)
                    INSTANCE = new UploadManager();
            }
        }

        return INSTANCE;
    }

    private UploadManager() {

        init();
    }


    public void init() {

//        BlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>();
//        executor = new ThreadPoolExecutor(DEFALT_THREAD_NUM, DEFALT_THREAD_NUM, 30, TimeUnit.MILLISECONDS, taskQueue);
        mExcutor = Executors.newScheduledThreadPool(DEFALT_THREAD_NUM);


    }

    public void upload(UploadTask task) {

        if (isTaskExists(task)) return;
        addTask(task);
        mExcutor.execute(task);
//        mExcutor.submit(task);

    }

    /**
     * 失败之后延时重试
     *
     * @param task
     * @param delay 单位ms
     */
    public void uploadSchedule(UploadTask task, long delay) {

        mExcutor.schedule(task, delay, TimeUnit.MILLISECONDS);
    }


    public boolean isTaskExists(UploadTask task) {
        if (mCacheTasks.contains(task)) return true;
        return false;
    }

    public void addTask(UploadTask task) {

        synchronized (mLock) {
            sp = AppApplication.getInstance().getSharedPreferences(task.saveName, Context.MODE_PRIVATE);
            if (sp.contains(task.stamp)) return;
            sp.edit().putString(task.stamp, (String) task.mTask).commit();
            mCacheTasks.add(task);
        }
    }


    public void removeTask(UploadTask task) {

        synchronized (mLock) {
            mCacheTasks.remove(task);
            sp = AppApplication.getInstance().getSharedPreferences(task.saveName, Context.MODE_PRIVATE);
            sp.edit().remove(task.stamp).commit();
        }

    }


    public void uploadAllComments(String spName) {
        sp = AppApplication.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE);

        HashMap<String, String> map = (HashMap<String, String>) sp.getAll();

        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            final String value = entry.getValue();

            UploadTask<String> uploadTask = new UploadTask<String>(value, key, spName) {
                @Override
                void uploadApi(UploadTask<String> task) {
                    SystemClock.sleep(2000);
                    Log.e("task test", task.mTask);
//                    if (value.contains("11")) {
//                        task.onFaild();
//                    } else {
                        task.onSuccess();
//                    }
                }
            };

            upload(uploadTask);

        }

    }

    public void uploadAllPraise(String spName) {


    }

}
