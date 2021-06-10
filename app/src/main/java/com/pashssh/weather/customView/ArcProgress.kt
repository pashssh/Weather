package com.pashssh.weather.customView

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.pashssh.weather.R

class ArcProgress(context: Context, attr: AttributeSet) : View(context, attr) {

    private val mCenterTextColor = ContextCompat.getColor(context, R.color.colorAppText)
    private val mCentralTextSize = 65f

    private val mBorderWidth = 30f
    private val mForegroundColor = Color.argb(80,240, 240, 240)

    private val mBackgroundColor = Color.argb(70, 204, 204, 204)
    private var mTotalPercent = 0f

    private var mCurrentPercent = 0f

    private var textDescription = ""

    private val mBackgroundPaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 15f
        color = mBackgroundColor
        style = Paint.Style.STROKE
        isDither = true
        strokeCap = Paint.Cap.ROUND
    }

    private val mForegroundPaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 15f
        isDither = true
        color = mForegroundColor
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val mTextPaint = Paint().apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            typeface = resources.getFont(R.font.comfortaa_variable_font)
        }
        isAntiAlias = true
        isDither = true
        color = mCenterTextColor
        textSize = mCentralTextSize
    }

    private val mTextPaintDes = Paint().apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            typeface = resources.getFont(R.font.comfortaa_variable_font)
        }
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
        isDither = true
        color = mCenterTextColor
        textSize = mCentralTextSize / 3
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
        val baseLine = height / 2 - dy / 2
        canvas?.drawText(s,start, baseLine,mTextPaint)
        val sDes = textDescription
        val startDes = (width / 2).toFloat()
        val baseLineDes = height / 2 + dy
        canvas?.drawText("Влажность",startDes,baseLineDes, mTextPaintDes)
    }

    fun setTotalProgress(totalProgress: Int) {
        this.mTotalPercent = totalProgress.toFloat()
    }

    fun setCurrentProgress(currentProgress: Int) {
        this.mCurrentPercent = currentProgress.toFloat()
        invalidate()
    }

}