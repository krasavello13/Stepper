package stepper.krasavello13.com

import android.content.Context
import android.view.View
import android.widget.Button
import stepper.krasavello13.com.stepper.VerticalStepperAdapter

class MainStepperAdapter(context: Context) : VerticalStepperAdapter(context) {
    override fun getCount(): Int = 7

    override fun getTitle(position: Int): CharSequence {
        return "Title $position"
    }

    override fun getSummary(position: Int): CharSequence? {
        return "Summary $position"
    }

    override fun isEditable(position: Int): Boolean {
        return position == 1
    }

    override fun getItem(position: Int): Void? {
        return null
    }

    override fun onCreateContentView(context: Context, position: Int): View {
        val content = MainItemView(context)
        val actionContinue = content.findViewById<Button>(R.id.action_continue)
        actionContinue.isEnabled = position < count - 1
        actionContinue.setOnClickListener { next() }

        val actionBack = content.findViewById<Button>(R.id.action_back)
        actionBack.isEnabled = position > 0
        actionBack.findViewById<Button>(R.id.action_back).setOnClickListener { previous() }

        return content
    }
}
