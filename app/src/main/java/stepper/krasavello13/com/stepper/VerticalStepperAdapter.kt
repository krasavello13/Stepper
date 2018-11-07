package stepper.krasavello13.com.stepper

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stepper.krasavello13.com.stepper.VerticalStepperAdapter.VerticalStepperViewHolder
import stepper.krasavello13.com.stepper.VerticalStepperItemView.StepperItemState
import stepper.krasavello13.com.stepper.VerticalStepperItemView.StepperItemState.*

abstract class VerticalStepperAdapter : RecyclerView.Adapter<VerticalStepperViewHolder>() {

    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalStepperViewHolder =
        VerticalStepperViewHolder(VerticalStepperItemView(parent.context).apply {
            contentView.inflate(viewType, true)
        })

    override fun onBindViewHolder(holder: VerticalStepperViewHolder, position: Int) {
        holder.containerView.state = getState(position)
        holder.containerView.setCircleNumber(getCircleNumber(position))
        holder.containerView.setTitle(getTitle(position))
        holder.containerView.setSummary(getSummary(position))
        holder.containerView.isEditable = isEditable(position)
        holder.containerView.showConnectorLine = showConnectorLine(position)
        onBind(holder.containerView.contentView, position)
    }

    private var focus = 0

    abstract fun getTitle(position: Int): CharSequence

    abstract fun getSummary(position: Int): CharSequence?

    abstract fun isEditable(position: Int): Boolean

    abstract fun getItemType(position: Int): Int

    abstract fun onBind(content: View, position: Int)

    override fun getItemViewType(position: Int): Int = getItemType(position)

    override fun getItemId(position: Int): Long = position.toLong()

    fun previous() {
        if (hasPrevious()) {
            jumpTo(focus - 1)
        }
    }

    fun next() {
        if (hasNext()) {
            jumpTo(focus + 1)
        }
    }

    private fun getState(position: Int): StepperItemState {
        return when {
            position == focus -> STATE_ACTIVE
            position < focus -> STATE_COMPLETE
            else -> STATE_INACTIVE
        }
    }

    private fun showConnectorLine(position: Int): Boolean = position < itemCount - 1

    private fun getCircleNumber(position: Int): Int = position + 1

    private fun jumpTo(position: Int) {
        if (focus != position) {
            val prevFocus = focus
            focus = position
            notifyItemChanged(prevFocus)
            notifyItemChanged(focus)
        }
    }

    private fun hasPrevious(): Boolean = focus > 0

    private fun hasNext(): Boolean = focus < itemCount - 1

    class VerticalStepperViewHolder(val containerView: VerticalStepperItemView) : RecyclerView.ViewHolder(containerView)
}