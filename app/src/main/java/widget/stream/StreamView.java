package widget.stream;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import widget.utils.DensityUtil;

/**
 * Created by dell on 2016/1/7.
 */
public class StreamView extends View {
    Paint mCirclePaint;
    Paint mLinePaint;
    TextPaint mTextPaint;

    float horizonLineLenth;
    int verticalLineLength;
    float circleRad;
    float marginLeft;

    ArrayList<Step> dataList;


    public StreamView(Context context) {
        super(context);
        init();
    }

    public StreamView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StreamView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public StreamView(Context context, ArrayList<Step> dataList) {
        super(context);
        this.dataList = dataList;
        init();

    }

    void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);


        mTextPaint = new TextPaint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        int screenWidth = DensityUtil.getScreenWidth(getContext());

        circleRad = screenWidth / 18.0f;
        marginLeft = circleRad * 2.0f;

        horizonLineLenth = (screenWidth - 6.0f * circleRad - marginLeft * 2.0f) / 2.0f;

        Log.e("test", "screenWidth:" + screenWidth + ",circleRad:" + circleRad + ",marginLeft:" + marginLeft + ",horizonLineLenth:" + horizonLineLenth);

        for (int i = 0; i < dataList.size(); i++) {
            Step step = dataList.get(i);
            if (i == 0) {
                step.drawable = new Circle(mCirclePaint, (int) (marginLeft + circleRad), (int) circleRad * 2, (int) circleRad, 5);
                step.drawable.lineLength = (int) horizonLineLenth;
                if (dataList.size() == 1) {
                    step.drawable.drawLine = false;
                } else {
                    step.drawable.drawLine = true;
                }

            } else {
                step.drawable = new Circle(mCirclePaint, (int) (dataList.get(i - 1).drawable.x + horizonLineLenth + circleRad * 2.0f), (int) circleRad * 2, (int) circleRad, 5);
                step.drawable.lineLength = (int) horizonLineLenth;
                if (i == dataList.size() - 1) {
                    step.drawable.drawLine = false;
                } else {
                    step.drawable.drawLine = true;
                }

            }

        }


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        for(int i = 0; i < dataList.size();i++){
            Step step = dataList.get(i);
            if(i != dataList.size() &&dataList.size() > 1){
                step.drawable.drawLine = true;
            }

            Circle circle = step.drawable;
//            canvas.drawCircle(circle.x,circle.y,circle.rad,circle.paint);
            circle.draw(canvas, true);

        }


    }

    void setDataList(ArrayList<Step> dataList) {
        this.dataList = dataList;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                for (Step step : dataList) {
                    if (step.drawable.region.contains((int) event.getX(), (int) event.getY())) {

                        step.nextStep = new Step("S" + 2, null, null);

                        step.drawable.drawLine = true;
                        step.drawable.onClick(step.nextStep);

                        dataList.add(step.nextStep);
                        invalidate();
                    }

                }


                break;

            case MotionEvent.ACTION_MOVE:

                break;

        }

        return super.onTouchEvent(event);
    }


}
