package stepper.krasavello13.com.stepper

import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import stepper.krasavello13.com.R

class VerticalStepperItemCircleView : FrameLayout {
    private var number: TextView? = null

    private var icon: ImageView? = null

    constructor(context: Context) : super(context) {
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initialize(context)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initialize(context)
    }

    private fun initialize(context: Context) {
        LayoutInflater.from(context).inflate(
            R.layout.vertical_stepper_view_item_circle,
            this,
            true
        )

        number = findViewById<View>(R.id.vertical_stepper_view_item_circle_number) as TextView
        icon = findViewById<View>(R.id.vertical_stepper_view_item_circle_icon) as ImageView
    }

    fun setBackgroundActive() {
        val drawable = ContextCompat
            .getDrawable(
                context,
                R.drawable.vertical_stepper_view_item_circle_active
            ) as GradientDrawable?
        drawable!!.setColor(
            Util
                .getThemeColor(context, R.attr.colorAccent)
        )
        setBackgroundResource(R.drawable.vertical_stepper_view_item_circle_active)
    }

    fun setBackgroundInactive() {
        setBackgroundResource(R.drawable.vertical_stepper_view_item_circle_inactive)
    }

    fun setNumber(value: Int) {
        icon!!.visibility = View.GONE
        number!!.visibility = View.VISIBLE
        number!!.text = value.toString()
    }

    fun setIconCheck() {
        setIconResource(R.drawable.icon_check_white_18dp)
    }

    fun setIconEdit() {
        setIconResource(R.drawable.icon_edit_white_18dp)
    }

    fun setIconResource(id: Int) {
        number!!.visibility = View.GONE
        icon!!.visibility = View.VISIBLE
        icon!!.setImageResource(id)
    }
}
