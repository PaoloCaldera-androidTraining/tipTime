package com.example.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    // Declare the binding variable so that can be used all over the class
    private lateinit var binding: ActivityMainBinding

    var tip = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Usual approach for inflating the layout:
            setContentView(R.layout.activity_main)
         With this approach, findViewById() must be used for each view of the layout
         to be referenced.

        Use instead *VIEW BINDING*
        A binding object is created to provide a reference to all the views of the layout.
        When view binding is enabled, a class related to a specific layout is created: in this
        case, activity_main.xml creates a class called ActivityMainBinding.
        Such class allows to inflate the layout by indicating the root element (Costraint Layout)
         */

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // calculate the tip when the button is clicked
        binding.calculateButton.setOnClickListener {
            calculateTip()
        }
    }

    private fun calculateTip() {

        // Get the decimal input value
        val costOfService = binding.costOfService.text.toString().toDouble()

        // Get the tip percentage selected by the user
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_amazing -> 0.2
            R.id.option_good -> 0.18
            else -> 0.15
        }

        tip = costOfService * tipPercentage

        // Round up the tip value according on the switch mode
        if (binding.roundUpSwitch.isChecked) {
            tip = ceil(tip)
        }

        // Format the currency according to the phone language selected by the user
        binding.tipAmount.text =
            getString(R.string.tip_amount, NumberFormat.getCurrencyInstance().format(tip))
    }
}