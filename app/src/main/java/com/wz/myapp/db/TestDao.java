package com.wz.myapp.db;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dell on 2016/7/7.
 */
public class TestDao {

    public static void insert(String name) {

        TestDbhelper helper = TestDbhelper.getInstance();
        SQLiteDatabase database = helper.getWritableDatabase();
        try {
            String sql = "insert into " + TestDbhelper.TABLE_NAME + "( 'name' ) values ('"+name+"')";
            database.execSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.close();
        }


    }


}
