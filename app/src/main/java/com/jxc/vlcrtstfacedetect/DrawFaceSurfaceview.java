package com.jxc.vlcrtstfacedetect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by widows7 on 2018/6/19.
 */

public class DrawFaceSurfaceview extends SurfaceView implements SurfaceHolder.Callback {
    /*public DrawFaceSurfaceview(Context context) {
        this(context,null);
    }*/

    String TAG = DrawFaceSurfaceview.class.getSimpleName();
    SurfaceHolder surfaceHolder;

    public DrawFaceSurfaceview(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setZOrderOnTop(true);//使surfaceview放到最顶层
//        getHolder().setFormat(PixelFormat.TRANSLUCENT);//使窗口支持透明度
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
    }

//    public DrawFaceSurfaceview(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        setZOrderOnTop(true);//使surfaceview放到最顶层
//        getHolder().setFormat(PixelFormat.TRANSLUCENT);//使窗口支持透明度
//    }

    @Override
    protected void onDraw(Canvas canvas) {

        Log.d(TAG, "onDraw");

//        canvas.drawColor(Color.TRANSPARENT);
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStrokeWidth(10);
//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(100,100,500,500,paint);

        super.onDraw(canvas);

    }

    @Override
    public void draw(Canvas canvas) {
        Log.d(TAG, "draw");
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d(TAG, "dispatchDraw");
        super.dispatchDraw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG,"surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG,"surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG,"surfaceDestroyed");
    }

    public void doDraw(Canvas canvas){
    }

    class MyThread implements Runnable{

        @Override
        public void run() {
            Canvas canvas = surfaceHolder.lockCanvas();
            doDraw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public Bitmap getBitmap(){
        Bitmap bitmap = Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        doDraw(canvas);
        return bitmap;
    }
}
