package widget.fabricview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.wz.myapp.R;

/**
 * Created by dell on 2016/1/7.
 */
public class FabRicActivity extends Activity {
    FabricView fabricView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fabric);

        fabricView = (FabricView) findViewById(R.id.faricView);

        fabricView.setInteractionMode(FabricView.SELECT_MODE);

    }

    public void draw_mode(View v) {
        fabricView.setInteractionMode(FabricView.DRAW_MODE);
    }

    public void select_mode(View v) {
        fabricView.setInteractionMode(FabricView.SELECT_MODE);
    }

    public void rotate_mode(View v) {
        fabricView.setInteractionMode(FabricView.ROTATE_MODE);
    }
}
