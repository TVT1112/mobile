package com.example.project

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class CartLabActivity : AppCompatActivity() {
    private lateinit var list: ArrayList<HashMap<String, String>>
    private lateinit var sa: SimpleAdapter
    private lateinit var tvTotal: TextView

    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var timePickerDialog: TimePickerDialog

    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private lateinit var btnCheckout: Button
    private lateinit var btnBack: Button
    private lateinit var database: Database

    // Mình đổi thành ArrayList<HashMap> cho dễ
    private lateinit var packages: ArrayList<HashMap<String, String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart_lab)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        dateButton  = findViewById(R.id.buttonCLAppDate)
        timeButton  = findViewById(R.id.buttonCLAppDate2)
        btnCheckout = findViewById(R.id.buttonCLcheckout)
        btnBack     = findViewById(R.id.buttonCLBack)
        tvTotal     = findViewById(R.id.textViewCLtotal)

        // 1) Lấy username đúng tên SharedPreferences
        val sharedPref = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
        val username   = sharedPref.getString("username", "") ?: ""

        database = Database(this, "healthcare", null, 1)

        // 2) Lấy dữ liệu từ DB
        val dbData: ArrayList<String> = database.getCartData(username, "lab")
        Toast.makeText(this, dbData.joinToString("\n"), Toast.LENGTH_LONG).show()

        // 3) Khởi tạo packages & list trước khi thêm
        packages = ArrayList()
        list     = ArrayList()

        // 4) Đi qua từng record, tách product$price, build packages và tính tổng
        var totalAmount = 0
        for (row in dbData) {
            val parts = row.split("$")
            if (parts.size >= 2) {
                val product = parts[0]
                val price   = parts[1].toIntOrNull() ?: 0
                totalAmount += price

                // Thêm vào packages
                val map = HashMap<String, String>().apply {
                    put("line1", product)
                    put("line2", "")
                    put("line3", "")
                    put("line4", "")
                    put("line5", "Cost: $price/-")
                }
                packages.add(map)
            }
        }

        // 5) Hiển thị tổng
        tvTotal.text = "Tổng: $totalAmount VNĐ"

        // 6) Đổ data vào list để làm Adapter
        for (map in packages) {
            // Đơn giản clone lại nếu cần, hoặc có thể dùng packages trực tiếp
            list.add(HashMap(map))
        }

        sa = SimpleAdapter(
            this,
            list,
            R.layout.multi_lines,
            arrayOf("line1","line2","line3","line4","line5"),
            intArrayOf(
                R.id.Line_a,
                R.id.Line_b,
                R.id.Line_c,
                R.id.Line_d,
                R.id.Line_e
            )
        )

        // 7) Gắn adapter lên ListView
        findViewById<ListView>(R.id.listViewCart).adapter = sa

        btnBack.setOnClickListener {
            startActivity(Intent(this, LabTestActivity::class.java))
        }

        // Setup Date & Time pickers
        initDatePicker()
        dateButton.setOnClickListener { datePickerDialog.show() }
        initTimePicker()
        timeButton.setOnClickListener { timePickerDialog.show() }
    }

    private fun initDatePicker() {
        val cal = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(
            this,
            AlertDialog.THEME_HOLO_DARK,
            { _, y, m, d -> dateButton.text = "$d/${m+1}/$y" },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = cal.timeInMillis
    }

    private fun initTimePicker() {
        val cal = Calendar.getInstance()
        timePickerDialog = TimePickerDialog(
            this,
            AlertDialog.THEME_HOLO_DARK,
            { _, h, min -> timeButton.text = String.format("%02d:%02d", h, min) },
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        )
    }
}
