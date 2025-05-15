package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LabTestActivity : AppCompatActivity() {

    private val packages = arrayOf(
        arrayOf("Gói 1: Khám sức khỏe tổng quát", "", "", "", "999"),
        arrayOf("Gói 2: Đo đường huyết lúc đói", "", "", "", "299"),
        arrayOf("Gói 3: Kháng thể COVID-19 - IgG", "", "", "", "899"),
        arrayOf("Gói 4: Kiểm tra tuyến giáp", "", "", "", "499"),
        arrayOf("Gói 5: Kiểm tra miễn dịch", "", "", "", "699")
    )


    private val packageDetails = arrayOf(
            """
        Đường huyết lúc đói
        Tổng phân tích tế bào máu (Hemogram)
        HbA1c
        Nghiên cứu sắt
        Kiểm tra chức năng thận
        LDH (Lactate Dehydrogenase), huyết thanh
        Hồ sơ mỡ máu
        Kiểm tra chức năng gan
        """.trimIndent(),

            "Đường huyết lúc đói",

            "Kháng thể COVID-19 - IgG",

            "Hồ sơ tuyến giáp toàn phần (T3, T4 & TSH nhạy cao)",

            """
        Tổng phân tích tế bào máu (Hemogram)
        CRP (C-reactive protein) định lượng, huyết thanh
        Nghiên cứu sắt
        Kiểm tra chức năng thận
        Vitamin D toàn phần - 25 Hydroxy
        Kiểm tra chức năng gan
        Hồ sơ mỡ máu
        """.trimIndent()
    )


    private lateinit var btnGoToCart: Button
    private lateinit var btnBack: Button
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lab_test)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnGoToCart = findViewById(R.id.buttonLTCart)
        btnBack = findViewById(R.id.buttonLTBack)
        listView = findViewById(R.id.listViewLT)

        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val list = ArrayList<HashMap<String, String>>()
        for (i in packages.indices) {
            val item = HashMap<String, String>().apply {
                put("line1", packages[i][0])
                put("line2", packages[i][1])
                put("line3", packages[i][2])
                put("line4", packages[i][3])
                put("line5", "Total Cost: ${packages[i][4]}/-")
            }
            list.add(item)
        }

        val adapter = SimpleAdapter(
            this,
            list,
            R.layout.multi_lines,
            arrayOf("line1", "line2", "line3", "line4", "line5"),
            intArrayOf(R.id.Line_a, R.id.Line_b, R.id.Line_c, R.id.Line_d, R.id.Line_e)
        )

        listView.adapter = adapter

        listView.setOnItemClickListener { adapterView, view, position, _ ->
            val intent = Intent(this, LabTestDetailsActivity::class.java).apply {
                putExtra("text1", packages[position][0])
                putExtra("text2", packageDetails[position])
                putExtra("text3", packages[position][4])
            }
            startActivity(intent)
        }

        btnGoToCart.setOnClickListener{
            val intent = Intent(this, CartLabActivity::class.java)
            startActivity(intent)
        }
    }
}
