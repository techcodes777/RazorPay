package com.eclatsol.razorpay

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultWithDataListener {
    lateinit var btnPayment : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Checkout.preload(applicationContext)
        val co = Checkout()
        co.setKeyID("rzp_test_VQcBqsa5POoqKz")

        btnPayment = findViewById(R.id.btnPayment)
        btnPayment.setOnClickListener {
            initPayment()
        }
    }

    private fun initPayment() {
        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","Razorpay")
            options.put("description","Software development company")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image","http://example.com/image/rzp.jpg")
            options.put("theme.color", "#DA3F0E");
            options.put("currency","INR");
//            options.put("order_id", "order_DBJOWzybf0sJbb"); //mandatory nathi
            options.put("amount","${50000/10}")//pass amount in currency subunits

            val retryObj = JSONObject();
            retryObj.put("enabled", true);  //Retry kavu che tene mate true/false
            retryObj.put("max_count", 4); //4 time tame retry kari shako shavo
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email","jemisvirani01@gmail.com")
            prefill.put("contact","9328714955")

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this, "$p1", Toast.LENGTH_SHORT).show()
    }
}