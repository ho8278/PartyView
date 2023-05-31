package com.ho8278.partyview

import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.withTranslation
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class Particle(
    private val drawable: Drawable,
    private val minWidth: Int,
    private val maxWidth: Int,
) {

    private val pathMeasure = PathMeasure()
    private val path = Path()

    private val paint = Paint().apply {
        color = Color.RED
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    private var positionX = 0f
    private var positionY = 0f

    private var quadX = 0
    private var quadY = 0

    private var isAnimationDone = true

    init {
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    }

    fun setPath(startX: Int, startY: Int) {
        path.reset()

        path.moveTo(startX.toFloat(), startY.toFloat())

        var endX: Int
        val minStart = startX - minWidth / 2
        val minEnd = startX - maxWidth / 2

        val maxStart = minStart + minWidth
        val maxEnd = minEnd + maxWidth
        do {
            endX = Random.nextInt(minEnd, maxEnd)
        } while (endX in minStart..maxStart)
        val endY = Random.nextInt(startY - 200, startY)

        quadX = Random.nextInt(min(startX, endX), max(startX, endX))
        quadY = Random.nextInt(startY / 2, endY)

        path.quadTo(quadX.toFloat(), quadY.toFloat(), endX.toFloat(), endY.toFloat())
        pathMeasure.setPath(path, false)
    }

    fun onProgress(progress: Float) {
        val distance = pathMeasure.length * progress
        val position = floatArrayOf(0f, 0f)
        pathMeasure.getPosTan(distance, position, null)
        positionX = position[0]
        positionY = position[1]

        isAnimationDone = progress == 1f
    }

    fun draw(canvas: Canvas) {
        if(isAnimationDone) return
        canvas.withTranslation(positionX, positionY) {
            drawable.draw(this)
        }
//        canvas.drawPath(path, paint)
//
//        canvas.drawPoint(quadX.toFloat(), quadY.toFloat(), paint)
    }
}