package stepper.krasavello13.com.stepper

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

abstract class VerticalStepperAdapter(context: Context) : BaseAdapter() {

    var focus = 0
        private set

    val contentViews = SparseArray<View>(count)

    init {
        for (i in 0 until count) {
            getContentView(context, i)
        }
    }

    override fun isEnabled(position: Int): Boolean {
        return isEditable(position) && getState(position) == VerticalStepperItemView.STATE_COMPLETE
    }

    abstract fun getTitle(position: Int): CharSequence

    abstract fun getSummary(position: Int): CharSequence?

    abstract fun isEditable(position: Int): Boolean

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val context = parent.context
        val itemView: VerticalStepperItemView

        itemView = if (convertView == null) {
            VerticalStepperItemView(context)
        } else {
            (convertView as VerticalStepperItemView?)!!
        }

        applyData(context, itemView, position)

        return itemView
    }

    fun getState(position: Int): Int {
        return if (position == focus)
            VerticalStepperItemView.STATE_ACTIVE
        else if (position < focus)
            VerticalStepperItemView.STATE_COMPLETE
        else
            VerticalStepperItemView.STATE_INACTIVE
    }

    private fun getCircleNumber(position: Int): Int {
        return position + 1
    }

    private fun showConnectorLine(position: Int): Boolean {
        return position < count - 1
    }

    abstract fun onCreateContentView(context: Context, position: Int): View

    private fun getContentView(context: Context, position: Int): View? {
        val id = getItemId(position).toInt()
        var contentView: View? = contentViews.get(id)

        contentView = onCreateContentView(context, position)
        contentViews.put(id, contentView)

        return contentView
    }

    fun invalidateContentView(position: Int) {
        val id = getItemId(position).toInt()
        contentViews.remove(id)
        notifyDataSetChanged()
    }

    private fun applyData(context: Context, itemView: VerticalStepperItemView, position: Int) {
        val currentContentView = itemView.contentView
        val contentView = getContentView(context, position)

        if (currentContentView != contentView) {
            // Make sure the content view isn't attached to a foreign parent
            contentView?.parent?.let {
                val parent = it as ViewGroup
                parent.removeView(it)


            }
        }

        itemView.contentView = getContentView(context, position)

        itemView.state = getState(position)
        itemView.setCircleNumber(getCircleNumber(position))
        itemView.setTitle(getTitle(position))
        itemView.setSummary(getSummary(position)!!)
        itemView.isEditable = isEditable(position)
        itemView.showConnectorLine = showConnectorLine(position)
    }

    fun jumpTo(position: Int) {
        if (focus != position) {
            focus = position
            notifyDataSetChanged()
        }
    }

    fun hasPrevious(): Boolean {
        return focus > 0
    }

    fun previous() {
        if (hasPrevious()) {
            jumpTo(focus - 1)
        }
    }

    operator fun hasNext(): Boolean {
        return focus < count - 1
    }

    operator fun next() {
        if (hasNext()) {
            jumpTo(focus + 1)
        }
    }
}