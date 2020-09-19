package com.truetask.common.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.truetask.utils.dip

class RoundedImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_RADIUS_IN_DP = 10
    }

    private var path: Path = Path()
    private var radius = dip(DEFAULT_RADIUS_IN_DP).toFloat()

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        // draw rounded image
        val rect = RectF(0f, 0f, this.width.toFloat(), this.height.toFloat())
        path.addRoundRect(rect, radius, radius, Path.Direction.CW)
        canvas.clipPath(path)

        super.onDraw(canvas)
    }
}