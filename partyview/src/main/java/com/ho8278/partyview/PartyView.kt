package com.ho8278.partyview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.withTranslation

class PartyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val icon: Drawable
    private val maxPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#33AA0000")
    }
    private val minPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#3300BB00")
    }

    init {
        icon = AppCompatResources.getDrawable(context, R.drawable.icon)!!
    }

    var touchPoint = intArrayOf(-1, -1)

    val particlePool = MutableList(20) { Particle(icon, 300, 900) }

    private fun start() {
        val animator = ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener { valueAnimator ->
                particlePool.forEach { it.onProgress(valueAnimator.animatedFraction) }
                invalidate()
            }
            duration = 500L
            interpolator = AccelerateDecelerateInterpolator()
        }
        animator.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            particlePool.forEach {
                it.setPath(event.x.toInt(), event.y.toInt())
            }
            touchPoint[0] = event.x.toInt()
            touchPoint[1] = event.y.toInt()
            start()
        }

        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let { canvas -> particlePool.forEach { it.draw(canvas) } }
    }
}