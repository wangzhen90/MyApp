package widget.deckview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by dell on 2016/2/4.
 */
public class DeckView<T> extends FrameLayout {
    public DeckView(Context context) {
        this(context,null);
    }

    public DeckView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DeckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
   
    }



}
