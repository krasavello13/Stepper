package stepper.krasavello13.com.stepper

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.TypedValue

import java.util.concurrent.atomic.AtomicInteger

internal object Util {
    fun dpToPx(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        )
    }

    fun getThemeColor(context: Context, attr: Int): Int {
        val typedValue = TypedValue()
        val a = context.obtainStyledAttributes(
            typedValue.data,
            intArrayOf(attr)
        )
        val color = a.getColor(0, 0)
        a.recycle()
        return color
    }
}
