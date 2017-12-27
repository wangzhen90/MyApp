package com.example.wangzhen.myapp.matrix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wangzhen.myapp.R;

public class MatrixActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);



    }

    public void pictureZoom(View view) {

        Intent intent = new Intent(this,PictureZoomActivity.class);
        startActivity(intent);


    }
}
