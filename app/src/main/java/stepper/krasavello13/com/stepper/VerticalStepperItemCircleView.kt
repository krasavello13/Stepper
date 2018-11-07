package stepper.krasavello13.com.stepper

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.vertical_stepper_view_item_circle.view.*
import stepper.krasavello13.com.R

class VerticalStepperItemCircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), LayoutContainer {

    override val containerView: View = inflate(R.layout.vertical_stepper_view_item_circle, true)

    fun setBackgroundActive() {
        (ContextCompat.getDrawable(
            context,
            R.drawable.vertical_stepper_view_item_circle_active
        ) as GradientDrawable).apply {
            setColor(context.getThemeColor(R.attr.colorAccent))
        }
        setBackgroundResource(R.drawable.vertical_stepper_view_item_circle_active)
    }

    fun setBackgroundInactive() {
        setBackgroundResource(R.drawable.vertical_stepper_view_item_circle_inactive)
    }

    fun setNumber(value: Int) {
        stepperViewItemCircleIcon.gone()
        stepperViewItemCircleNumber.visible()
        stepperViewItemCircleNumber.text = value.toString()
    }

    fun setIconCheck() {
        setIconResource(R.drawable.icon_check_white_18dp)
    }

    fun setIconEdit() {
        setIconResource(R.drawable.icon_edit_white_18dp)
    }

    fun setIconResource(id: Int) {
        stepperViewItemCircleNumber.gone()
        stepperViewItemCircleIcon.visible()
        stepperViewItemCircleIcon.setImageResource(id)
    }
}