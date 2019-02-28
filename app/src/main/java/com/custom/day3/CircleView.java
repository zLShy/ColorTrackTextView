package com.custom.day3;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhangli on 2019/2/27.
 */

public class CircleView extends View {
    private int mOutColor = Color.BLUE;
    private int mInnerColor = Color.RED;
    private int mTextColor = Color.RED;
    private int mCicleWidth = 15;
    private int mTextSzie = 16;
    private Paint mOutpaint;
    private Paint mInnerPaint;
    private Paint mTextPaint;
    private int mStepMax = 100;
    private int mCurrentSteps = 50;
    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CircleView);

        mOutColor = typedArray.getColor(R.styleable.CircleView_outCircleColor,mOutColor);
        mInnerColor = typedArray.getColor(R.styleable.CircleView_innerCircleColor,mInnerColor);
        mTextColor = typedArray.getColor(R.styleable.CircleView_circleTextColor,mTextColor);
        mCicleWidth = (int) typedArray.getDimension(R.styleable.CircleView_bordWidth,mCicleWidth);
        mTextSzie = typedArray.getDimensionPixelSize(R.styleable.CircleView_circleTextSize,mTextSzie);
        typedArray.recycle();
        //外圆弧画笔
        mOutpaint = new Paint();
        mOutpaint.setAntiAlias(true);
        mOutpaint.setStyle(Paint.Style.STROKE);
        mOutpaint.setStrokeCap(Paint.Cap.ROUND);
        mOutpaint.setStrokeWidth(mCicleWidth);
        mOutpaint.setColor(mOutColor);
        //内圆弧画笔
        mInnerPaint = new Paint();
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStrokeWidth(mCicleWidth);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setAntiAlias(true);
        //文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSzie);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = dp2px(40);
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            mHeight = dp2px(40);
        }

        setMeasuredDimension(mWidth>mHeight?mHeight:mWidth,mWidth>mHeight?mHeight:mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画外圆弧
        RectF rectF = new RectF(mCicleWidth/2,mCicleWidth/2,getWidth()-mCicleWidth/2,getHeight()-mCicleWidth/2);
        canvas.drawArc(rectF,135,270,false,mOutpaint);

        //画内圆弧
        if (mStepMax == 0) return;
        float sweepAngle = (float) mCurrentSteps / mStepMax;
        canvas.drawArc(rectF,135,sweepAngle*270,false,mInnerPaint);
        //画文字
        String mCurrent = String.valueOf(mCurrentSteps);
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(mCurrent,0,mCurrent.length(),bounds);
        Paint.FontMetricsInt mFontMetrics =  mTextPaint.getFontMetricsInt();
        int dx = getWidth()/2 - bounds.width()/2;
        int dy = (mFontMetrics.bottom - mFontMetrics.top) /2 - mFontMetrics.bottom;
        int baseLine = getHeight()/2 + dy;
        canvas.drawText(mCurrent,dx,baseLine,mTextPaint);
    }

    private int dp2px(float defValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (scale * defValue + 0.5f);
    }

    public void setmStepMax(int mStepMax) {
        this.mStepMax = mStepMax;
    }

    public synchronized void setmCurrentSteps(int mCurrentSteps) {
        this.mCurrentSteps = mCurrentSteps;
        invalidate();
    }
}
