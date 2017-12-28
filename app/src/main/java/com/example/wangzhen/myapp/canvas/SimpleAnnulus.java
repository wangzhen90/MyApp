package com.example.wangzhen.myapp.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wangzhen on 2017/12/28.
 */

public class SimpleAnnulus extends View {
    private int mWidth;
    private int mHeight;

    public SimpleAnnulus(Context context) {
        super(context);
    }

    public SimpleAnnulus(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);


        canvas.translate(mWidth/2,mHeight/2);

        float rL = Math.min(mHeight,mWidth)/2;
        float rS = rL * 0.8f;

        canvas.drawCircle(0,0,rL,paint);
        canvas.drawCircle(0,0,rS,paint);
        paint.setColor(Color.GRAY);
        for(int i =0; i < 360; i+=10){

            //旋转坐标轴，然后在y轴上话一条短线
            canvas.drawLine(0,rS,0,rL,paint);
            canvas.rotate(10);
        }



    }
}
