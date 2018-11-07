package stepper.krasavello13.com.stepper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.support.v4.content.res.ResourcesCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import stepper.krasavello13.com.R

class VerticalStepperItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    var showConnectorLine = true
        set(show) {
            field = show
            setMarginBottom(this.state == STATE_ACTIVE)
        }

    var isEditable = false
        set(editable) {
            field = editable

            if (this.state == STATE_COMPLETE)
                if (isEditable)
                    circle!!.setIconEdit()
                else
                    circle!!.setIconCheck()
        }

    private var circle: VerticalStepperItemCircleView? = null

    private var number: Int = 0

    private var wrapper: LinearLayout? = null

    private var title: TextView? = null

    private var summary: TextView? = null

    private var contentWrapper: FrameLayout? = null

    private var connector: ConnectorLineDrawer? = null

    var state = STATE_INACTIVE
        set(state) {
            field = state

            if (state == STATE_INACTIVE)
                setStateInactive()
            else if (state == STATE_ACTIVE)
                setStateActive()
            else
                setStateComplete()
        }

    var contentView: View?
        get() = contentWrapper?.getChildAt(0)
        set(view) {
            contentWrapper?.removeAllViews()
            contentWrapper?.addView(view, MATCH_PARENT, WRAP_CONTENT)
        }

    init {
        setWillNotDraw(false)
        clipChildren = false
        clipToPadding = false

        val padding = Util.dpToPx(context, 8f).toInt()
        setPadding(padding, padding, padding, 0)

        LayoutInflater.from(context).inflate(R.layout.vertical_stepper_view_item, this, true)

        circle = findViewById<View>(R.id.vertical_stepper_view_item_circle) as VerticalStepperItemCircleView
        wrapper = findViewById<View>(R.id.vertical_stepper_view_item_wrapper) as LinearLayout
        title = findViewById<View>(R.id.vertical_stepper_view_item_title) as TextView
        summary = findViewById<View>(R.id.vertical_stepper_view_item_summary) as TextView
        contentWrapper = findViewById<View>(R.id.vertical_stepper_view_item_content_wrapper) as FrameLayout

        connector = ConnectorLineDrawer(context)
    }

    fun setCircleNumber(number: Int) {
        this.number = number

        if (this.state != STATE_COMPLETE)
            circle!!.setNumber(number)
    }

    fun setTitle(title: CharSequence) {
        this.title!!.text = title
    }

    fun setSummary(summary: CharSequence) {
        this.summary!!.text = summary

        if (this.state == STATE_COMPLETE)
            this.summary!!.visibility = View.VISIBLE
    }

    private fun setStateInactive() {
        circle!!.setIconEdit()
        setMarginBottom(false)
        circle!!.setNumber(number)
        circle!!.setBackgroundInactive()
        title!!.setTextColor(
            ResourcesCompat.getColor(
                resources,
                R.color.vertical_stepper_view_black_38,
                null
            )
        )
        title!!.setTypeface(title!!.typeface, Typeface.NORMAL)
        summary!!.visibility = View.GONE
        contentWrapper!!.visibility = View.GONE
    }

    private fun setStateActive() {
        circle!!.setIconEdit()
        setMarginBottom(true)
        circle!!.setNumber(number)
        circle!!.setBackgroundActive()
        title!!.setTextColor(
            ResourcesCompat.getColor(
                resources,
                R.color.vertical_stepper_view_black_87, null
            )
        )
        title!!.setTypeface(title!!.typeface, Typeface.BOLD)
        summary!!.visibility = View.GONE
        contentWrapper!!.visibility = View.VISIBLE
    }

    private fun setStateComplete() {
        setMarginBottom(false)
        circle!!.setBackgroundActive()

        if (isEditable)
            circle!!.setIconEdit()
        else
            circle!!.setIconCheck()

        title!!.setTextColor(
            ResourcesCompat.getColor(
                resources,
                R.color.vertical_stepper_view_black_87, null
            )
        )
        title!!.setTypeface(title!!.typeface, Typeface.BOLD)
        summary!!.visibility = if (TextUtils.isEmpty(summary!!.text))
            View.GONE
        else
            View.VISIBLE
        contentWrapper!!.visibility = View.GONE
    }

    private fun setMarginBottom(active: Boolean) {
        val params = wrapper!!.layoutParams as ViewGroup.MarginLayoutParams

        if (!showConnectorLine)
            params.bottomMargin = 0
        else if (active)
            params.bottomMargin = Util.dpToPx(context, 48f).toInt()
        else
            params.bottomMargin = Util.dpToPx(context, 40f).toInt()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (this.showConnectorLine)
            connector!!.draw(canvas)
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        connector!!.adjust(context, width, height)
    }

    companion object {
        var STATE_INACTIVE = 0

        var STATE_ACTIVE = 1

        var STATE_COMPLETE = 2
    }
}
