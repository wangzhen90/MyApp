package widget.deckview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by dell on 2016/2/15.
 */
public class DeckChildView<T>extends FrameLayout{
    public DeckChildView(Context context) {
        super(context);
    }

    public DeckChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DeckChildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
