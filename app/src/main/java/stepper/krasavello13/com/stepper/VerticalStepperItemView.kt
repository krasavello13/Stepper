package stepper.krasavello13.com.stepper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.vertical_stepper_view_item.view.*
import stepper.krasavello13.com.R
import stepper.krasavello13.com.stepper.VerticalStepperItemView.StepperItemState.*

class VerticalStepperItemView constructor(context: Context) : FrameLayout(context), LayoutContainer {

    init {
        setWillNotDraw(false)
        clipChildren = false
        clipToPadding = false

        val padding = dpToPx(8f).toInt()
        setPadding(padding, padding, padding, 0)
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

    override val containerView: View = inflate(R.layout.vertical_stepper_view_item, true)

    private val connector: ConnectorLineDrawer = ConnectorLineDrawer(context)

    private var number: Int = 0

    var showConnectorLine = true
        set(show) {
            field = show
            setMarginBottom(state == STATE_ACTIVE)
        }

    var isEditable = false
        set(editable) {
            field = editable
            if (state == STATE_COMPLETE)
                if (editable) {
                    stepperViewItemCircle.setIconEdit()
                } else {
                    stepperViewItemCircle.setIconCheck()
                }
        }

    var state = STATE_INACTIVE
        set(state) {
            field = state
            when (state) {
                STATE_INACTIVE -> setStateInactive()
                STATE_ACTIVE -> setStateActive()
                else -> setStateComplete()
            }
        }

    var contentView: ViewGroup
        get() = stepperViewItemContent
        set(view) {
            stepperViewItemContent.removeAllViews()
            stepperViewItemContent.addView(view, MATCH_PARENT, WRAP_CONTENT)
        }

    fun setCircleNumber(number: Int) {
        this.number = number

        if (state != STATE_COMPLETE) {
            stepperViewItemCircle.setNumber(number)
        }
    }

    fun setTitle(title: CharSequence?) {
        stepperViewItemTitle.text = title
    }

    fun setSummary(summary: CharSequence?) {
        stepperViewItemSummary.text = summary

        if (state == STATE_COMPLETE) {
            stepperViewItemSummary.visible()
        }
    }

    private fun setStateInactive() {
        stepperViewItemCircle.setIconEdit()
        setMarginBottom(false)
        stepperViewItemCircle.setNumber(number)
        stepperViewItemCircle.setBackgroundInactive()
        stepperViewItemTitle.setTextColor(getColor(R.color.vertical_stepper_view_black_38))
        stepperViewItemTitle.setTypeface(stepperViewItemTitle.typeface, Typeface.NORMAL)
        stepperViewItemSummary.gone()
        stepperViewItemContent.gone()
    }

    private fun setStateActive() {
        stepperViewItemCircle.setIconEdit()
        setMarginBottom(true)
        stepperViewItemCircle.setNumber(number)
        stepperViewItemCircle.setBackgroundActive()
        stepperViewItemTitle.setTextColor(getColor(R.color.vertical_stepper_view_black_87))
        stepperViewItemTitle.setTypeface(stepperViewItemTitle.typeface, Typeface.BOLD)
        stepperViewItemSummary.gone()
        stepperViewItemContent.visible()
    }

    private fun setStateComplete() {
        setMarginBottom(false)
        stepperViewItemCircle.setBackgroundActive()
        if (isEditable) {
            stepperViewItemCircle.setIconEdit()
        } else {
            stepperViewItemCircle.setIconCheck()
        }
        stepperViewItemTitle.setTextColor(getColor(R.color.vertical_stepper_view_black_87))
        stepperViewItemTitle.setTypeface(stepperViewItemTitle.typeface, Typeface.BOLD)
        stepperViewItemSummary.visibility = if (stepperViewItemSummary.text.isEmpty()) View.GONE else View.VISIBLE
        stepperViewItemContent.gone()
    }

    private fun setMarginBottom(active: Boolean) {
        (stepperViewItemContainer.layoutParams as ViewGroup.MarginLayoutParams).apply {
            bottomMargin = when {
                !showConnectorLine -> 0
                active -> dpToPx(48f).toInt()
                else -> dpToPx(40f).toInt()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (this.showConnectorLine) {
            connector.draw(canvas)
        }
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        connector.adjust(context, width, height)
    }

    enum class StepperItemState(val state: Int) {

        STATE_INACTIVE(0),
        STATE_ACTIVE(1),
        STATE_COMPLETE(2),
        NONE(-1);

        companion object {
            fun getState(state: Int): StepperItemState {
                values().forEach {
                    if (it.state == state) {
                        return it
                    }
                }
                return NONE
            }
        }
    }

    inner class ConnectorLineDrawer(val context: Context) {

        private val line = RectF()
        private val paint = Paint().apply {
            color = context.getColorCompat(R.color.vertical_stepper_view_grey_100)
        }

        fun adjust(context: Context, width: Int, height: Int) {
            line.left = context.dpToPx(19.5f)
            line.right = context.dpToPx(20.5f)
            line.top = context.dpToPx(40f)
            line.bottom = height.toFloat()
        }

        fun draw(canvas: Canvas) {
            canvas.drawRect(line, paint)
        }
    }
}
