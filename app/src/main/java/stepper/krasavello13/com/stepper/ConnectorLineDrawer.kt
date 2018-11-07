package stepper.krasavello13.com.stepper

import android.content.Context
import android.graphics.*
import android.support.v4.content.res.ResourcesCompat
import stepper.krasavello13.com.R

internal class ConnectorLineDrawer(context: Context) {
    private val paint = Paint()

    private val line = RectF()

    init {
        val grey = ResourcesCompat.getColor(
            context.resources,
            R.color.vertical_stepper_view_grey_100,
            null
        )
        paint.color = grey
    }

    fun adjust(context: Context, width: Int, height: Int) {
        line.left = Util.dpToPx(context, 19.5f)
        line.right = Util.dpToPx(context, 20.5f)
        line.top = Util.dpToPx(context, 40f)
        line.bottom = height.toFloat()
    }

    fun draw(canvas: Canvas) {
        canvas.drawRect(line, paint)
    }
}
