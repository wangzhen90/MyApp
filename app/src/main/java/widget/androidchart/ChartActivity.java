package widget.androidchart;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wz.myapp.R;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {
    LineView lineView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        ArrayList<String> strList = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            strList.add("str" + i);
        }

        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            ArrayList<Integer> data = new ArrayList<>();

            data.add( i);
            data.add(i+1);
            data.add(5);
            data.add(10);
            data.add(3);


            dataLists.add(data);

        }


        lineView = (LineView)findViewById(R.id.line_view);
        lineView.setDrawDotLine(false); //optional
        lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY); //optional
        lineView.setBottomTextList(strList);
        lineView.setDataList(dataLists);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
                for(int i = 0; i < 2; i++){
                    ArrayList<Integer> data = new ArrayList<>();

                    data.add(3);
                    data.add(3);
                    data.add(7);
                    data.add(1);
                    data.add(5);


                    dataLists.add(data);

                }
                lineView.setDataList(dataLists);
            }
        },5000);

    }



}
