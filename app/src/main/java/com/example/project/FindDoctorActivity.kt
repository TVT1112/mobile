package com.example.project

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FindDoctorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_find_doctor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val exit: CardView = findViewById(R.id.cardFDback);
        exit.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent);
        }

        val familyPhysician: CardView = findViewById(R.id.cardFDfamilyPhysician)
        familyPhysician.setOnClickListener {
            val intent = Intent(this, DoctordetailActivity::class.java)
            intent.putExtra("title", "Chuyên gia nội khoa") // truyền dữ liệu nếu cần
            startActivity(intent)
        }

        val dietician: CardView = findViewById(R.id.cardFDdietcian)
        dietician.setOnClickListener {
            val intent = Intent(this, DoctordetailActivity::class.java)
            intent.putExtra("title", "Chuyên gia dinh dưỡng") // truyền dữ liệu nếu cần
            startActivity(intent)
        }

        val dentist: CardView = findViewById(R.id.cardFDdentist)
        dentist.setOnClickListener {
            val intent = Intent(this, DoctordetailActivity::class.java)
            intent.putExtra("title", "Nha khoa") // truyền dữ liệu nếu cần
            startActivity(intent)
        }

        val surgeon: CardView = findViewById(R.id.cardFDSurgeon)
        surgeon.setOnClickListener {
            val intent = Intent(this, DoctordetailActivity::class.java)
            intent.putExtra("title", "Chuyên gia phẫu thuật") // truyền dữ liệu nếu cần
            startActivity(intent)
        }

        val cardio: CardView = findViewById(R.id.cardFDcardio)
        cardio.setOnClickListener {
            val intent = Intent(this, DoctordetailActivity::class.java)
            intent.putExtra("title", "Chuyên gia tim mạch") // truyền dữ liệu nếu cần
            startActivity(intent)
        }
    }
}