package stepper.krasavello13.com.stepper

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attach: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attach)

fun ViewGroup.getColor(@ColorRes colorRes: Int): Int = context.getColorCompat(colorRes)

fun ViewGroup.dpToPx(dp: Float): Float = context.dpToPx(dp)


fun View.gone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun Context.getColorCompat(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Context.dpToPx(dp: Float): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)

fun Context.getThemeColor(attr: Int): Int = TypedValue().run {
    val a = obtainStyledAttributes(data, intArrayOf(attr))
    val color = a.getColor(0, 0)
    a.recycle()
    color
}

fun Bundle.putData(argsBuilder: Bundle.() -> Unit) = this.apply { Bundle().apply(argsBuilder) }