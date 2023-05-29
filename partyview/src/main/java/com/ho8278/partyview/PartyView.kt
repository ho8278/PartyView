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

    val particle = Particle(icon, 300, 100)
    var touchPoint = intArrayOf(-1, -1)

    fun start() {
        val animator = ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                particle.onProgress(it.animatedFraction)
                invalidate()
            }
            duration = 500L
            interpolator = AccelerateDecelerateInterpolator()
        }
        animator.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            Log.d("AAA", "????????")
            particle.setPath(event.x.toInt(), event.y.toInt())
            touchPoint[0] = event.x.toInt()
            touchPoint[1] = event.y.toInt()
            start()
        }

        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let { particle.draw(it) }

        //draw max rect
        if(touchPoint[0] >= 0 && touchPoint[1] >= 0) {
            canvas?.withTranslation(touchPoint[0] - 150f,touchPoint[1] - 150f) {
                drawRect(0f,0f,300f,300f, maxPaint)
            }
            canvas?.withTranslation(touchPoint[0] - 50f, touchPoint [1] - 50f) {
                drawRect(0f,0f,100f,100f, minPaint)
            }
        }
    }
}