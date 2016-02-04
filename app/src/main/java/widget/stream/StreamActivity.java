package widget.stream;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.wz.myapp.R;

import java.util.ArrayList;

import widget.utils.DensityUtil;

public class StreamActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    ArrayList<Step> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);


        dataList = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            Step step = new Step("S" + i, null, null);
            dataList.add(step);


        }

        frameLayout = (FrameLayout) findViewById(R.id.stream_container);
        StreamView streamView = new StreamView(this, dataList);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(1000, DensityUtil.dp2px(this,500));
        streamView.setLayoutParams(layoutParams);
        frameLayout.addView(streamView);


    }

}
