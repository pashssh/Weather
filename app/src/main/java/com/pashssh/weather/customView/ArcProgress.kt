package com.pashssh.weather.customView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class ArcProgress(context: Context, attr: AttributeSet) : View(context, attr) {

    private val mCenterTextColor = Color.GRAY
    private val mCentralTextSize = 100f

    private val mBorderWidth = 30f

    private val mForegroundColor = Color.BLUE
    private val mBackgroundColor = Color.GRAY

    private var mTotalPercent = 0f
    private var mCurrentPercent = 0f

    val mBackgroundPaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 30f
        color = mBackgroundColor
        style = Paint.Style.STROKE
        isDither = true
        strokeCap = Paint.Cap.ROUND
    }

    val mForegroundPaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 30f
        isDither = true
        color = mForegroundColor
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    val mTextPaint = Paint().apply {
        isAntiAlias = true
        isDither = true
        color = mCenterTextColor
        textSize = mCentralTextSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(
            if (width > height) height else width,
            if (width > height) height else width
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val rectF = RectF(
            mBorderWidth / 2,
            mBorderWidth / 2,
            width - mBorderWidth / 2,
            height - mBorderWidth / 2
        )

        canvas?.drawArc(rectF, 135f, 270f, false, mBackgroundPaint)

//        if (mTotalPercent == 0f) return
        if (mCurrentPercent > mTotalPercent) {
            mCurrentPercent = mTotalPercent
        }
        val sweepAngle = mCurrentPercent / mTotalPercent
        canvas?.drawArc(rectF, 135f, sweepAngle * 270f, false, mForegroundPaint)


        val bounds = Rect()

        val s = "${mCurrentPercent.toInt()}%"
        mTextPaint.getTextBounds(s, 0, s.length, bounds)
        val start = (width / 2 - bounds.width() / 2).toFloat()
        val metrics = mTextPaint.fontMetrics
        val dy = metrics.bottom - metrics.top / 2 - metrics.bottom
        val baseLine = height / 2 + dy
        canvas?.drawText(s, start, baseLine, mTextPaint)
    }

    fun setTotalProgress(totalProgress: Int) {
        this.mTotalPercent = totalProgress.toFloat()
    }

    fun setCurrentProgress(currentProgress: Int) {
        this.mCurrentPercent = currentProgress.toFloat()
        invalidate()
    }

}