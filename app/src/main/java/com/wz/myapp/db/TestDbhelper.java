package com.wz.myapp.db;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wz.myapp.AppApplication;

/**
 * Created by dell on 2016/7/7.
 */
public class TestDbhelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "upload_data";
    public static final int version = 1;
    private static TestDbhelper INSTANCE = null;
    public static final String TABLE_NAME = "table_test";

    public static TestDbhelper getInstance(){
        if(INSTANCE == null){
            synchronized (TestDbhelper.class){
                if(INSTANCE == null){
                    INSTANCE = new TestDbhelper();
                }
            }
        }

        return INSTANCE;
    }
    private TestDbhelper() {
        super(AppApplication.getInstance(), DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table table_test (" +
                "name text " +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
