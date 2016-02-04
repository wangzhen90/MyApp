package widget.stream;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by dell on 2016/1/7.
 */
public interface StreamDrawable {
    Paint getPaint();
    void setPaint(Paint p);
    void draw(Canvas canvas,boolean animator);
}
