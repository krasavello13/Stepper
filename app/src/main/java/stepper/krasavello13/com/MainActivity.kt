package stepper.krasavello13.com

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import stepper.krasavello13.com.stepper.VerticalStepperView



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stepper_list.setStepperAdapter(MainStepperAdapter(this))
    }
}
