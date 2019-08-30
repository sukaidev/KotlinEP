package com.sukaidev.core.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import com.sukaidev.core.R
import org.jetbrains.anko.dimen

/**
 * Created by sukaidev on 2019/08/14.
 *
 */
class RoundRectImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    val radius = dimen(R.dimen.common_radius).toFloat()

    //设置圆角为左上和右上
    private val radiusArray:FloatArray = floatArrayOf(radius,radius,radius,radius,0.0f,0.0f,0.0f,0.0f)

    /**
     * 绘制圆角
     */
    private fun drawRoundAngle(paramCanvas: Canvas) {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        val path = Path()
        path.addRoundRect(RectF(0.0f, 0.0f, width.toFloat(), height.toFloat()), this.radiusArray, Path.Direction.CW)
        path.fillType = Path.FillType.INVERSE_WINDING
        paramCanvas.drawPath(path, paint)
    }


    override fun draw(paramCanvas: Canvas) {
        var bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        var localCanvas = Canvas(bitmap)
        if (bitmap.isRecycled) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            localCanvas = Canvas(bitmap)
        }
        super.draw(localCanvas)
        drawRoundAngle(localCanvas)
        val paint = Paint()
        paint.xfermode = null
        paramCanvas.drawBitmap(bitmap, 0.0f, 0.0f, paint)
        bitmap.recycle()
    }
}