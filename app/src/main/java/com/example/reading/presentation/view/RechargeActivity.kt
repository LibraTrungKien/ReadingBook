package com.example.reading.presentation.view

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.reading.R
import com.example.reading.databinding.FragmentRechargeBinding
import com.example.reading.presentation.view.base.apiCall
import com.example.reading.presentation.zalopay.Api.CreateOrder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RechargeActivity : AppCompatActivity() {
    private lateinit var binding: FragmentRechargeBinding
    private val viewModel: RechargeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRechargeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        initializeComponent()
//        initializeEvent()
//        initializeData()
//        bindView()
    }

//    private fun initializeComponent() {
//        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)
//
//        ZaloPaySDK.init(2553, Environment.SANDBOX)
//    }
//
//    private fun initializeEvent() {
//        viewModel._dataLiveData.observe(this) {
//            Toast.makeText(this, "Nạp thành công!!!", Toast.LENGTH_LONG).show()
//        }
//
//        binding.btnCreateOrder.setOnClickListener {
//            val orderApi = CreateOrder()
//            try {
//                val data = orderApi.createOrder(viewModel.getMoney().toString())
//                val code = data.getString("return_code")
//                Toast.makeText(applicationContext, "return_code: $code", Toast.LENGTH_LONG).show()
//                if (code == "1") {
//                    binding.txtToken.text = data.getString("zp_trans_token")
//                    isDone()
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//
//        binding.radGroup.setOnCheckedChangeListener { _, checkedId ->
//            viewModel.cost = when (checkedId) {
//                R.id.btnTwenty -> 22
//                R.id.btnFifty -> 70
//                R.id.btnOneHundred -> 150
//                R.id.btnFiveHundred -> 800
//                else -> 10
//            }
//        }
//
//        binding.btnPay.setOnClickListener {
//            val token = binding.txtToken.text.toString()
//            ZaloPaySDK.getInstance()
//                .payOrder(this, token, "demozpdk://app", object : PayOrderListener {
//                    override fun onPaymentSucceeded(
//                        transactionId: String,
//                        transToken: String,
//                        appTransID: String
//                    ) {
//                        isLoading()
//                        apiCall(viewModel.editAccount(), this@RechargeActivity, {
//                            Toast.makeText(
//                                this@RechargeActivity,
//                                "Nạp thành công",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }, { true })
//                    }
//
//                    override fun onPaymentCanceled(
//                        zpTransToken: String,
//                        appTransID: String
//                    ) {
//                        Log.d("MainActivity", "onPaymentCanceled()... ")
//                    }
//
//                    override fun onPaymentError(
//                        zaloPayError: ZaloPayError,
//                        zpTransToken: String,
//                        appTransID: String
//                    ) {
//                        Log.d("MainActivity", "onPaymentError()... ")
//                    }
//                })
//        }
//    }
//
//    private fun initializeData() {
//        viewModel.getAccount()
//    }
//
//    private fun bindView() {
//        isLoading()
//    }
//
//    private fun isLoading() {
//        binding.txtToken.visibility = View.INVISIBLE
//        binding.btnPay.visibility = View.INVISIBLE
//    }
//
//    private fun isDone() {
//        binding.txtToken.visibility = View.VISIBLE
//        binding.btnPay.visibility = View.VISIBLE
//    }
//
//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        ZaloPaySDK.getInstance().onResult(intent)
//    }
}