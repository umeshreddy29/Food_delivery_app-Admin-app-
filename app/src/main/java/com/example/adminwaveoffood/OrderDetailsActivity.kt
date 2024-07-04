package com.example.adminwaveoffood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwaveoffood.Models.OrderDetails
import com.example.adminwaveoffood.adapter.OrderDetailsAdapter
import com.example.adminwaveoffood.databinding.ActivityOrderDetailsBinding

class OrderDetailsActivity : AppCompatActivity() {

    private var userName: String? = null
    private var address: String? = null
    private var phoneNumber: String? = null
    private var totalPrice: String? = null
    private var foodNames: ArrayList<String> = arrayListOf()
    private var foodImages: ArrayList<String> = arrayListOf()
    private var foodQuantity: ArrayList<Int> = arrayListOf()
    private var foodPrices: ArrayList<String> = arrayListOf()

    private val binding: ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)


        binding.buttonBack.setOnClickListener {
            finish()
        }
        getDataFromIntent()
    }

    private fun getDataFromIntent() {
        val recievedOrderDetails =
            intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        recievedOrderDetails?.let { orderDetails ->

            userName = recievedOrderDetails.userName
            foodNames = recievedOrderDetails.foodNames as ArrayList<String>
            foodImages = recievedOrderDetails.foodImages as ArrayList<String>
            foodQuantity = recievedOrderDetails.foodQuantities as ArrayList<Int>
            address = recievedOrderDetails.address
            phoneNumber = recievedOrderDetails.phoneNumber
            foodPrices = recievedOrderDetails.foodPrices as ArrayList<String>
            totalPrice = recievedOrderDetails.totalPrice
            setUserDetail()
            setAdapter()
        }
    }

    private fun setAdapter() {
        binding.orderDetailsRecyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = OrderDetailsAdapter(this, foodNames, foodImages, foodQuantity, foodPrices)
        binding.orderDetailsRecyclerview.adapter = adapter
    }

    private fun setUserDetail() {
        binding.name.text = userName
        binding.address.text = address
        binding.phone.text = phoneNumber
        binding.totalAmount.text = totalPrice
    }
}