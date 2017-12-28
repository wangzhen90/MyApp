package com.example.wangzhen.myapp.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by wangzhen on 2017/12/28.
 */

public class SimplePie extends View {

    int[] percent = new int[]{60,120,180};
    int[] colors = {Color.RED,Color.GREEN,Color.BLUE};
    Paint mPaint;

    private int mWidth;
    private int mHeight;

    private float mStartAngle= 0f;


    public SimplePie(Context context) {
        super(context);
        init(context);
    }

    public SimplePie(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    void init(Context context){

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

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

        //移动原点到view的中心
        canvas.translate(mWidth/2,mHeight/2);

        //根据View的宽高设置半径
        float r = Math.min(mHeight,mWidth) /2.0f;

        //设置饼图的绘制区域
        RectF rectF = new RectF();
        rectF.set(-r,-r,r,r);

        float startAngle = 0;


        for(int i = 0; i < percent.length; i++){
            float currentAngle = percent[i];
            mPaint.setColor(colors[i]);
            /**
            ﻿   startAngle  开始角度
             sweepAngle    扫过角度
             useCenter     是否使用中心
             */

            canvas.drawArc(rectF,startAngle,currentAngle,true,mPaint);

            startAngle += currentAngle;
        }


    }
}
