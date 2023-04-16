package com.example.reading.presentation.view

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.reading.R
import com.example.reading.databinding.FragmentRechargeBinding
import com.example.reading.presentation.zalopay.Api.CreateOrder
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener

class RechargeActivity: AppCompatActivity() {
    private lateinit var binding: FragmentRechargeBinding

    companion object {
        fun open(navController: NavController) {
            navController.navigate(R.id.actRechargeFragment)
        }
    }

    private fun BindView() {
        IsLoading()
    }

    private fun IsLoading() {
        binding.lblZpTransToken.visibility = View.INVISIBLE
        binding.txtToken.visibility = View.INVISIBLE
        binding.btnPay.visibility = View.INVISIBLE
    }

    private fun IsDone() {
        binding.lblZpTransToken.visibility = View.VISIBLE
        binding.txtToken.visibility = View.VISIBLE
        binding.btnPay.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRechargeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX)
        // bind components with ids
        BindView()
        // handle CreateOrder
        binding.btnCreateOrder.setOnClickListener {
            val orderApi = CreateOrder()
            try {
                val data = orderApi.createOrder(binding.txtAmount.text.toString())
                binding.lblZpTransToken.visibility = View.VISIBLE
                val code = data.getString("return_code")
                Toast.makeText(applicationContext, "return_code: $code", Toast.LENGTH_LONG).show()
                if (code == "1") {
                    binding.lblZpTransToken.text = "zptranstoken"
                    binding.txtToken.text = data.getString("zp_trans_token")
                    IsDone()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        binding.btnPay.setOnClickListener {
            val token = binding.txtToken.text.toString()
            ZaloPaySDK.getInstance()
                .payOrder(this, token, "demozpdk://app", object : PayOrderListener {
                    override fun onPaymentSucceeded(
                        transactionId: String,
                        transToken: String,
                        appTransID: String
                    ) {
                        Log.d("MainActivity", "onPaymentSucceeded()... ")
                        IsLoading()
                    }

                    override fun onPaymentCanceled(
                        zpTransToken: String,
                        appTransID: String
                    ) {
                        Log.d("MainActivity", "onPaymentCanceled()... ")
                    }

                    override fun onPaymentError(
                        zaloPayError: ZaloPayError,
                        zpTransToken: String,
                        appTransID: String
                    ) {
                        Log.d("MainActivity", "onPaymentError()... ")
                    }
                })
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        ZaloPaySDK.getInstance().onResult(intent)
    }
}