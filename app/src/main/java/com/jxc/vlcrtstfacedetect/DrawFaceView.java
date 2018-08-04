package com.jxc.vlcrtstfacedetect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by widows7 on 2018/6/20.
 */

public class DrawFaceView extends View {

    public Canvas mCanvas;

    public Paint mPaint = new Paint();

    private int left,top,right,bottom = 0;

    public DrawFaceView(Context context) {
        this(context,null);
    }

    public DrawFaceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawFaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        Log.e("TAG",mCanvas.toString());
        mCanvas.drawRect(left,top,right,bottom,mPaint);
    }

    private void initPaint(){
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public void drawRectOnFace(float left, float top, float right, float bottom){
        Log.e("TAG",mCanvas.toString());
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        mCanvas.drawRect(left,top,right,bottom,mPaint);
    }

    public void setXY(int left,int top,int right,int bottom){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
//        invalidate();
    }
}
