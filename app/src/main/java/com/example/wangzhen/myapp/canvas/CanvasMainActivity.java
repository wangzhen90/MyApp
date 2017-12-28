package com.example.wangzhen.myapp.canvas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wangzhen.myapp.R;

public class CanvasMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_main);
    }

    public void canvas(View view) {

        Intent intent = new Intent(this,CanvasActivity.class);
        startActivity(intent);



    }
}
