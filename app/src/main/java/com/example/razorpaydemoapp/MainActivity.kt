package com.example.razorpaydemoapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultListener {

    // Declare variables for EditText, Button, and TextView
    lateinit var amtEdt: EditText
    lateinit var payBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize variables
        amtEdt = findViewById(R.id.edit_amount)
        payBtn = findViewById(R.id.btn_pay)

        // Set click listener for the payment button
        payBtn.setOnClickListener {

            // Get the amount from the EditText
            val amt = amtEdt.text.toString()

            // Round off the amount and convert it to an integer (multiplying by 100)
            val amount = Math.round(amt.toFloat() * 100).toInt()

            // Initialize Razorpay Checkout
            val checkout = Checkout()

            // Set the Razorpay API Key (replace with your actual key)
            checkout.setKeyID("ABC")

            // Initialize a JSON object for payment details
            val obj = JSONObject()
            try {
                // Put the merchant name
                obj.put("name", "Razorpay Payment Demo")

                // Put the payment description
                obj.put("description,", "Just Checking the Integration of RazorPay")

                // Set the theme color (leave empty for default)
                obj.put("theme.color", "green")

                // Put the currency (Indian Rupees in this case)
                obj.put("currency", "INR")

                // Put the payment amount
                obj.put("amount", amount)

                // Open the Razorpay checkout activity with the provided payment details
                checkout.open(this@MainActivity, obj)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    // Implementation of the PaymentResultListener interface method for payment success
    override fun onPaymentSuccess(s: String?) {

        // Display a toast indicating payment success
        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();
    }

    // Implementation of the PaymentResultListener interface method for payment error
    override fun onPaymentError(p0: Int, s: String?) {
        // Display a toast indicating payment failure with the error message
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }
}