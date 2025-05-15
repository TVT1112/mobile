package com.example.project

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context


class LabTestDetailsActivity : AppCompatActivity() {

    private lateinit var tvPackageName: TextView
    private lateinit var tvTotalCost: TextView
    private lateinit var edDetails: EditText
    private lateinit var btnAddtocard:Button
    private lateinit var btnBack:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lab_test_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvPackageName = findViewById(R.id.textViewLTDPackageName)
        tvTotalCost = findViewById(R.id.textViewtLTDTotalcost)
        edDetails = findViewById(R.id.editTextLTDTextMultiLine)
        btnBack = findViewById(R.id.buttonLTDBack)
        btnAddtocard = findViewById(R.id.buttonLTDAddtoCart)

        edDetails.keyListener = null

        val intent = getIntent()
        tvPackageName.text = intent.getStringExtra("text1")
        edDetails.setText(intent.getStringExtra("text2"))
        tvTotalCost.text = "Tổng: ${intent.getStringExtra("text3")}"

        btnBack.setOnClickListener {
            val intent = Intent(this, LabTestActivity::class.java)
            startActivity(intent)
        }

        btnAddtocard.setOnClickListener{
            val sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username", "") ?: ""
            val product = tvPackageName.text.toString()
            val price = intent.getStringExtra("text3")?.toFloat() ?: 0f

            val db = Database(applicationContext, "healthcare", null, 1)

            if (db.checkCart(username, product) == 1) {
                Toast.makeText(applicationContext, "Đã tồn tại trong giỏ hàng", Toast.LENGTH_SHORT).show()
            } else {
                db.addCart(username, product, price, "lab")
                Toast.makeText(applicationContext, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LabTestActivity::class.java)
                startActivity(intent)
            }
        }
    }
}