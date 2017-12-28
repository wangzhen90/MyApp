package com.example.wangzhen.myapp.matrix;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.wangzhen.myapp.R;

public class MatrixApiDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_api_demo);

        initPolyView();

    }


    void initPolyView(){
        final PolyView polyView = (PolyView) findViewById(R.id.polyView);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()){
                    case R.id.point0: polyView.setTestPoint(0); break;
                    case R.id.point1: polyView.setTestPoint(1); break;
                    case R.id.point2: polyView.setTestPoint(2); break;
                    case R.id.point3: polyView.setTestPoint(3); break;
                    case R.id.point4: polyView.setTestPoint(4); break;
                }

            }
        });
    }
}
