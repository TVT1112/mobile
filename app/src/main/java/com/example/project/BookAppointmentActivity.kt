package com.example.project

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class BookAppointmentActivity : AppCompatActivity() {
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var timePickerDialog: TimePickerDialog
    private lateinit var datebtn:Button
    private lateinit var timebtn:Button
    private lateinit var bookbtn:Button
    private lateinit var backbtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_book_appointment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tv = findViewById<TextView>(R.id.textView2)
        val ed1 = findViewById<EditText>(R.id.editTextAppFullname)
        val ed2 = findViewById<EditText>(R.id.editTextAppAddress)
        val ed3 = findViewById<EditText>(R.id.editTextAppContact)
        val ed4 = findViewById<EditText>(R.id.editTextAppFee)

        datebtn = findViewById(R.id.buttonAppDate)
        timebtn = findViewById(R.id.buttonAppDate2)
        bookbtn = findViewById(R.id.buttonBookAppoiment)
        backbtn = findViewById(R.id.buttonAppBack)
        ed1.keyListener = null
        ed2.keyListener = null
        ed3.keyListener = null
        ed4.keyListener = null

        // Nhận dữ liệu từ Intent
        val it = intent
        val title = it.getStringExtra("text1") ?: ""
        val fullname = it.getStringExtra("text2") ?: ""
        val address = it.getStringExtra("text3") ?: ""
        val contact = it.getStringExtra("text4") ?: ""
        val fees = it.getStringExtra("text5") ?: ""

        // Hiển thị dữ liệu lên view
        tv.text = title
        ed1.setText(fullname)
        ed2.setText(address)
        ed3.setText(contact)
        ed4.setText("Cons Fees: $fees/--")

        //datepicker
        initDatePicker()
        datebtn.setOnClickListener{
            datePickerDialog.show()
        }

        //time
        initTimePicker()
        timebtn.setOnClickListener{
            timePickerDialog.show()
        }

        backbtn.setOnClickListener{
            val intent = Intent(this, FindDoctorActivity::class.java)
            startActivity(intent);
        }

        bookbtn.setOnClickListener{

        }
    }

    private fun initDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            datebtn.text = selectedDate
        }

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val style = AlertDialog.THEME_HOLO_DARK

        datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
        datePickerDialog.datePicker.minDate = cal.timeInMillis // Set ngày tối thiểu là hôm nay
    }

    private fun initTimePicker() {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
            timebtn.text = selectedTime
        }

        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)  // Sửa từ HOUR sang HOUR_OF_DAY
        val minute = cal.get(Calendar.MINUTE)

        val style = AlertDialog.THEME_HOLO_DARK  // Sửa lỗi chính tả THENE_HOLD_DARK

        timePickerDialog = TimePickerDialog(
            this,
            style,
            timeSetListener,
            hour,
            minute,
            true  // 24h format
        )
    }
}