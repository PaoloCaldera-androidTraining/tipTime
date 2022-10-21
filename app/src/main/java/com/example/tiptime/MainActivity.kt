package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    // Declare the binding variable so that can be used all over the class
    private lateinit var binding: ActivityMainBinding

    private var tip = 0.0

    companion object {
        private const val LOG_TAG = "MainActivity::class"
    }

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
        Such class allows to inflate the layout by indicating the root element (Constraint Layout)
         */

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // calculate the tip when the button is clicked
        binding.calculateButton.setOnClickListener {
            calculateTip()
        }

        // handle the keyboard hiding when the enter key is pressed
        binding.costOfServiceValue.setOnKeyListener { v, keyCode, _ ->
            handleKeyEvent(v, keyCode)
        }
    }


    private fun calculateTip() {

        // Get the decimal input value
        val costOfService = binding.costOfServiceValue.text.toString().toDoubleOrNull()

        // If the provided value is null, clear the tip amount and then notify the user
        if (costOfService == null || costOfService == 0.0) {
            displayTip(0.0)
            Toast.makeText(this, "Cost of service value not provided!", Toast.LENGTH_SHORT).show()
            return
        }

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

        displayTip(tip)
    }


    private fun displayTip(tipToDisplay: Double) {
        // Format the tip with a currency, according to the phone language selected by the user
        val formattedTip = NumberFormat.getCurrencyInstance().format(tipToDisplay)
        binding.tipAmount.text = getString(R.string.tip_amount, formattedTip)
    }


    // Hide the keyboard when the enter command is selected
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {

        // KEYCODE_ENTER is the code associated to the enter button of the soft keyboard
        if (keyCode == KeyEvent.KEYCODE_ENTER) {

            // create an InputMethodManager object to handle the keyboard operations
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            // command for hiding the keyboard
            // The token of the interested view, the TextInputEditText, is passed
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

            return true
        }

        return false
    }
}