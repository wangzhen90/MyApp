package widget.stream;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by dell on 2016/1/7.
 */
public class Line implements StreamDrawable {


    public int startX;
    public int startY;
    public int endX;
    public int endY;
    public int velocity;

    public Line(int startX, int startY, int endX, int endY, int velocity) {
        this.startX = startX;
        this.velocity = velocity;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public void setVeloctiy(int velocity) {
        this.velocity = velocity;
    }

    @Override
    public Paint getPaint() {
        return null;
    }

    @Override
    public void setPaint(Paint p) {

    }

    @Override
    public void draw(Canvas canvas, boolean animator) {

    }

    public void update() {
        endX = updateSelf(startX, endX, velocity);
        endY = updateSelf(startY, endY, velocity);

    }

    private int updateSelf(int origin, int target, int velocity) {
        if (origin < target) {
            origin += velocity;
        } else if (origin > target) {
            origin -= velocity;
        }
        if (Math.abs(target - origin) < velocity) {
            origin = target;
        }
        return origin;
    }


}
