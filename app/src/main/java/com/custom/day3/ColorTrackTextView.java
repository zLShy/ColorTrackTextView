package com.custom.day3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zhangli on 2019/2/27.
 */

@SuppressLint("AppCompatCustomView")
public class ColorTrackTextView extends TextView {
    private Paint mOriginPaint;
    private Paint mChangePaint;
    private float mProgress = 0.0f;
    private Drections mDrection = Drections.LEFT_TO_RIGHT;
    public enum Drections {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }
    public ColorTrackTextView(Context context) {
        this(context,null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context,attrs);
    }

    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ColorTrackTextView);

        mOriginPaint = getPaintByColor(typedArray.getColor(R.styleable.ColorTrackTextView_originColor,getTextColors().getDefaultColor()));
        mChangePaint = getPaintByColor(typedArray.getColor(R.styleable.ColorTrackTextView_changeColor,getTextColors().getDefaultColor()));

        typedArray.recycle();
    }


    private Paint getPaintByColor(int color) {

        Paint paint = new Paint();
        paint.setColor(color);
        //抗锯齿
        paint.setAntiAlias(true);
        //防抖动
        paint.setDither(true);
        paint.setTextSize(getTextSize());
        return paint;
    }
    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.clipRect() 区域裁剪api
        int middle = (int) (getWidth() * mProgress);
        if (mDrection == Drections.LEFT_TO_RIGHT) {
            paintRectText(canvas,0,middle,mChangePaint);
            paintRectText(canvas,middle,getWidth(),mOriginPaint);
        }else {
            paintRectText(canvas,getWidth()-middle,getWidth(),mChangePaint);
            paintRectText(canvas,0,getWidth()-middle,mOriginPaint);
        }
    }

    private void paintRectText(Canvas canvas,int start,int end,Paint paint) {

        canvas.save();//保存画布
        //裁剪画布区域绘制
        canvas.clipRect(start,0,end,getHeight());
        //画文字
        String texts = getText().toString();
        //基线和起始位置计算
        Rect rect = new Rect();
        paint.getTextBounds(texts,0,texts.length(),rect);
        Paint.FontMetricsInt mFontMetrics = paint.getFontMetricsInt();
        int dx = getWidth()/2 - rect.width()/2 ;
        int dy = (mFontMetrics.bottom - mFontMetrics.top) / 2 - mFontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy ;
        canvas.drawText(texts,dx,baseLine,paint);
        canvas.restore();//释放画布
    }

    public void setmDrection(Drections mDrection) {
        this.mDrection = mDrection;
    }

    public synchronized void setmProgress(float progress) {
        this.mProgress = progress;
        invalidate();
    }
    public void setmOriginPaint(int color) {
        this.mOriginPaint.setColor(color);
    }

    public void setmChangePaint(int color) {
        this.mChangePaint.setColor(color);
    }
}
