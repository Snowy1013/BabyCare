package com.snowy.babycare.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.snowy.babycare.R;

/**
 * Created by snowy on 16/3/1.
 */
public class HorizontalProgressBar extends ProgressBar {

    private static final int DEFAULT_COLOR_UNREACHED = 0xffffff;
    private static final int DEFAULT_COLOR_REACHED = 0Xff00cd00;
    private static final int DEFAULT_HEIGHT_REACHED_PROGRESS_BAR = 2;
    private static final int DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR = 2;

    /**
     * painter of all drawing things
     */
    protected Paint mPaint = new Paint();
    /**
     * height of reached progress bar
     */
    protected int mReachedProgressBarHeight = dp2px(DEFAULT_HEIGHT_REACHED_PROGRESS_BAR);

    /**
     * color of reached bar
     */
    protected int mReachedBarColor = DEFAULT_COLOR_REACHED;
    /**
     * color of unreached bar
     */
    protected int mUnReachedBarColor = DEFAULT_COLOR_UNREACHED;
    /**
     * height of unreached progress bar
     */
    protected int mUnReachedProgressBarHeight = dp2px(DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR);
    /**
     * view width except padding
     */
    protected int mRealWidth;

    protected static final int VISIBLE = 0;

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(true);

        obtainStyledAttributes(attrs);

        mPaint.setColor(mReachedBarColor);
    }

    /**
     * get the styled attributes
     *
     * @param attrs
     */
    private void obtainStyledAttributes(AttributeSet attrs)
    {
        // init values from custom attributes
        final TypedArray attributes = getContext().obtainStyledAttributes(
                attrs, R.styleable.HorizontalProgressBar);

        mReachedBarColor = attributes
                .getColor(
                        R.styleable.HorizontalProgressBar_progress_reached_color,
                        DEFAULT_COLOR_REACHED);
        mUnReachedBarColor = attributes
                .getColor(
                        R.styleable.HorizontalProgressBar_progress_unreached_color,
                        DEFAULT_COLOR_UNREACHED);
        mReachedProgressBarHeight = (int) attributes
                .getDimension(
                        R.styleable.HorizontalProgressBar_progress_reached_bar_height,
                        mReachedProgressBarHeight);
        mUnReachedProgressBarHeight = (int) attributes
                .getDimension(
                        R.styleable.HorizontalProgressBar_progress_unreached_bar_height,
                        mUnReachedProgressBarHeight);

        attributes.recycle();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        //画笔平移到指定paddingLeft， getHeight() / 2位置，注意以后坐标都为以此为0，0
        canvas.translate(getPaddingLeft(), getHeight() / 2);

        boolean noNeedBg = false;
        //当前进度和总值的比例
        float radio = getProgress() * 1.0f / getMax();
        //已到达的宽度
        float progressPosX = (int) (mRealWidth * radio);

        //如果到达最后，则未到达的进度条不需要绘制
        if (progressPosX > mRealWidth)
        {
            progressPosX = mRealWidth;
            noNeedBg = true;
        }

        // 绘制已到达的进度
        float endX = progressPosX;
        if (endX > 0)
        {
            mPaint.setColor(mReachedBarColor);
            mPaint.setStrokeWidth(mReachedProgressBarHeight);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }


        // 绘制未到达的进度条
        if (!noNeedBg)
        {
            float start = progressPosX;
            mPaint.setColor(mUnReachedBarColor);
            mPaint.setStrokeWidth(mUnReachedProgressBarHeight);
            canvas.drawLine(start, 0, mRealWidth, 0, mPaint);
        }

        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mRealWidth = w - getPaddingRight() - getPaddingLeft();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode != MeasureSpec.EXACTLY)
        {

            float textHeight = 0;
            int exceptHeight = (int) (getPaddingTop() + getPaddingBottom() + Math
                    .max(Math.max(mReachedProgressBarHeight,
                            mUnReachedProgressBarHeight), Math.abs(textHeight)));

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight,
                    MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }
}
