package com.example.wangzhen.myapp.matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.wangzhen.myapp.R;

/**
 * Created by wangzhen on 2017/12/28.
 */

public class PolyView extends View {

    private Bitmap mBitmap;             // 要绘制的图片
    private Matrix mPolyMatrix;         // 测试setPolyToPoly用的Matrix

    private int testPoint = 0;
    private int triggerRadius = 180;    // 触发半径为180px

    private float[] src = new float[8];
    private float[] dst = new float[8];

    private Paint pointPaint;



    public PolyView(Context context) {
        super(context);
        init();
    }

    public PolyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PolyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init(){
        //就是改变原始图像的四个角的位置，通过setPolyToPoly 获得 更改后的矩阵

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mmlittle);

        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStrokeWidth(50);
        pointPaint.setColor(0xffd19165);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);



        mPolyMatrix = new Matrix();

        float[] temp = {0, 0,                                    // 左上
                mBitmap.getWidth(), 0,                          // 右上
                mBitmap.getWidth(), mBitmap.getHeight(),        // 右下
                0, mBitmap.getHeight()};                        // 左下
        src = temp.clone();
        dst = temp.clone();



        // 核心要点
        mPolyMatrix.setPolyToPoly(src, 0, dst, 0, 4);

//        // 此处为了更好的显示对图片进行了等比缩放和平移(图片本身有点大)
//        mPolyMatrix.postScale(0.5f, 0.5f);
//        mPolyMatrix.postTranslate(50,100);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap,mPolyMatrix,null);

        float[] dst = new float[8];
        //计算一组点基于当前Matrix变换后的位置，src作为参数传递原始数值，计算结果存放在dst中，src不变
        mPolyMatrix.mapPoints(dst,src);

        // 绘制触控点
        for (int i=0; i<testPoint*2; i+=2 ) {
            canvas.drawPoint(dst[i], dst[i+1],pointPaint);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_MOVE:

                float tempX = event.getX();
                float tempY = event.getY();

                for(int i =0; i< testPoint *2;i+=2){

                    if(Math.abs(dst[i] - tempX ) <= triggerRadius && Math.abs(dst[i+1] - tempY) <= triggerRadius){

                        dst[i]   = tempX-50;
                        dst[i+1] = tempY-50;
                        break;
                    }

                }
                resetMatrix(testPoint);
                break;
        }

        return true;
    }


    public void setTestPoint(int testPoint) {
        this.testPoint = testPoint > 4 || testPoint < 0 ? 4 : testPoint;
        dst = src.clone();
        resetMatrix(this.testPoint);
    }

    private void resetMatrix(int testPoint){
        //因为矩阵每次修改后会影响下一次的相乘结果，所以定义了src，和 dst来保存数据，每次进行矩阵的变换时都使用单位矩阵
        mPolyMatrix.reset();
        mPolyMatrix.setPolyToPoly(src,0,dst,0,testPoint);
        invalidate();

    }

}
