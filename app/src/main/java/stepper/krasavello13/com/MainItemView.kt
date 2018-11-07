package stepper.krasavello13.com

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout

class MainItemView : LinearLayout {
    constructor(context: Context) : super(context) {
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initialize(context)
    }

    private fun initialize(context: Context) {
        clipChildren = true
        orientation = LinearLayout.VERTICAL

        LayoutInflater.from(context).inflate(R.layout.item, this, true)
    }

    override fun onSaveInstanceState(): Parcelable? {
        Log.wtf("WTF", "Saving MainItemView state")
        return super.onSaveInstanceState()
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        Log.wtf("WTF", "Restoring MainItemView state")
        super.onRestoreInstanceState(state)
    }
}
