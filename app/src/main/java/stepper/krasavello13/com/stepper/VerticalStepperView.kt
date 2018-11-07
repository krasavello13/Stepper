package stepper.krasavello13.com.stepper

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.widget.ListView
import java.util.*

class VerticalStepperView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ListView(context, attrs, defStyleAttr, defStyleRes) {

    init {
        divider = null
        onItemClickListener = OnItemClickListener { _, view, position, id ->  (adapter as VerticalStepperAdapter).jumpTo(position) }
    }

    fun setStepperAdapter(adapter: VerticalStepperAdapter) = setAdapter(adapter)

    //override fun getAdapter(): VerticalStepperAdapter = super.getAdapter() as VerticalStepperAdapter

    override fun onSaveInstanceState(): Parcelable? {
        val contentViews = (adapter as VerticalStepperAdapter).contentViews
        val containers = ArrayList<Bundle>(contentViews.size())

        for (i in 0 until contentViews.size()) {
            val id = contentViews.keyAt(i)
            val contentView = contentViews.valueAt(i)
            val container = SparseArray<Parcelable>(1)
            contentView.saveHierarchyState(container)

            val bundle = Bundle(2)
            bundle.putInt("id", id)
            bundle.putSparseParcelableArray("container", container)

            containers.add(bundle)
        }

        val bundle = Bundle(3)
        bundle.putParcelable("super", super.onSaveInstanceState())
        bundle.putParcelableArrayList("containers", containers)
        bundle.putInt("focus", (adapter as VerticalStepperAdapter).focus)

        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle) {
            super.onRestoreInstanceState(state.getParcelable("super"))
            val containers = state.getParcelableArrayList<Bundle>("containers")
            val contentViews = (adapter as VerticalStepperAdapter).contentViews

            for (contentViewBundle in containers!!) {
                val id = contentViewBundle.getInt("id")
                val container = contentViewBundle
                    .getSparseParcelableArray<Parcelable>("container")

                val contentView = contentViews.get(id)
                contentView?.restoreHierarchyState(container)
            }

            (adapter as VerticalStepperAdapter).jumpTo(state.getInt("focus"))
        } else
            super.onRestoreInstanceState(state)
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }
}
