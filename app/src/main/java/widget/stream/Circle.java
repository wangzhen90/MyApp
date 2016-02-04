package widget.stream;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Region;
import android.util.Log;
import android.view.View;

/**
 * Created by dell on 2016/1/7.
 */
public class Circle implements StreamDrawable {
    public Paint paint;
    public Canvas canvas;
    public int x;
    public int y;
    public int rad;

    public int lineLength;

    public int targetX;
    public int targetY;
    public int targetRad;
    public int velocity;

    public Circle pre;
    public Circle next;

    public boolean drawLine;

    public Region region = new Region();

    public Circle(Paint paint, int x, int y, int rad, int velocity) {
        this.paint = paint;
        this.x = x;
        this.y = y;
        this.rad = rad;
        this.velocity = velocity;
        region.set(x - rad, y - rad, x + rad, y + rad);
    }

    public void setVeloctiy(int velocity) {
        this.velocity = velocity;

    }

    public void update() {

        rad = updateSelf(rad, targetRad, this.velocity);

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


    @Override
    public Paint getPaint() {
        return paint;
    }

    @Override
    public void setPaint(Paint p) {
        this.paint = p;
    }

    @Override
    public void draw(Canvas canvas, boolean animator) {
        this.canvas = canvas;
        if (animator) {
            canvas.drawCircle(x, y, rad, getPaint());
        } else {
            canvas.drawCircle(x, y, rad, getPaint());
        }
        Log.e("drawLine:","drawLine:"+drawLine);
        if (drawLine) {
            drawLine(canvas, animator);
        }

    }

    public void drawLine(Canvas canvas, boolean animator) {

        Line line = new Line(x + rad, y, x + rad + lineLength, y, 10);

        getPaint().setStrokeWidth(10);
        canvas.drawLine(line.startX, line.startY, line.endX, line.endY, getPaint());

    }

    public void onClick(Step step) {

        if (step != null) {
            step.drawable = new Circle(getPaint(), (int) (this.x + lineLength + rad * 2.0f), rad * 2, rad, 5);
            step.drawable.pre = this;
            this.next = step.drawable;
        }


    }


}
