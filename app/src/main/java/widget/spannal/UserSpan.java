package widget.spannal;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dell on 2015/12/25.
 */
public class UserSpan extends ClickableSpan {
    Context mContext;
    TextView textView;
    private boolean mIsPressed;

    private int mPressedBackgroundColor = Color.GRAY;

    private int mNormalTextColor;

    private int mPressedTextColor;


    public UserSpan(String url,Context context,TextView textView) {
      super();

        mContext = context;
        this.textView = textView;


    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.BLUE);
        ds.setUnderlineText(false);
        ds.bgColor = mIsPressed ? mPressedBackgroundColor : Color.TRANSPARENT;

        Log.i("test","updateDrawState");
    }

    @Override
    public void onClick(View widget) {
//        textView.setHighlightColor(Color.GRAY);
        Toast.makeText(mContext,"跳转到主页面",Toast.LENGTH_SHORT).show();
//        widget.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                textView.setHighlightColor(Color.TRANSPARENT);
//            }
//        }, 500);


    }

    public void setPressed(boolean isSelected) {
        mIsPressed = isSelected;
    }
}
