package widget.threadpool;

import android.util.Log;

/**
 * Created by dell on 2016/3/7.
 */
public abstract class UploadTask<T> implements Runnable {
    public T mTask;
    public String saveName;
    public String stamp;
    public int maxUploadCount = 3;

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long delay = 3000;

    public UploadTask(T task, String stamp, String saveName) {
        mTask = task;
        this.stamp = stamp;
        this.saveName = saveName;
    }

    @Override
    public void run() {

        uploadApi(this);
    }

    abstract void uploadApi(UploadTask<T> task);


    public void onSuccess() {
        UploadManager.getInstance().removeTask(this);
    }

    public void onFaild() {
        if (maxUploadCount > 0) {
            maxUploadCount--;
            UploadManager.getInstance().uploadSchedule(this, delay);
            Log.e("uplaod test", (String) mTask + "上传第" + maxUploadCount + "次");
        } else {

            Log.e("uplaod test", (String) mTask + "上传三次都失败了");
        }
    }
}
