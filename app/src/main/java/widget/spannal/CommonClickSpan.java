package widget.spannal;

import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by dell on 2016/3/17.
 */
public class CommonClickSpan extends ClickableSpan {

     boolean mIsPressed;
    public void setPressed(boolean isSelected) {
        mIsPressed = isSelected;
    }
    @Override
    public void onClick(View widget) {

    }
}
