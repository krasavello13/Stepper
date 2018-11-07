package stepper.krasavello13.com

import android.view.View
import android.widget.Button
import stepper.krasavello13.com.stepper.VerticalStepperAdapter

class MainStepperAdapter : VerticalStepperAdapter() {

    override fun getItemType(position: Int): Int = R.layout.item

    override fun getItemCount(): Int = 3

    override fun getTitle(position: Int): CharSequence = "Title $position"

    override fun getSummary(position: Int): CharSequence? = "Summary $position"

    override fun isEditable(position: Int): Boolean = position == 1

    override fun onBind(content: View, position: Int) {
        val actionContinue = content.findViewById<Button>(R.id.action_continue)
        actionContinue.isEnabled = position < itemCount - 1
        actionContinue.setOnClickListener { next() }

        val actionBack = content.findViewById<Button>(R.id.action_back)
        actionBack.isEnabled = position > 0
        actionBack.findViewById<Button>(R.id.action_back).setOnClickListener { previous() }
    }
}