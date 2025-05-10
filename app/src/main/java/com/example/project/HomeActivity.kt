package com.example.project

import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("username","").toString();
        Toast.makeText(applicationContext, "Chào mừng "+username, Toast.LENGTH_SHORT).show();

        val exit: CardView = findViewById(R.id.cardExit)
        exit.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply() // Lưu thay đổi
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent);

            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show()
            finish() // Kết thúc activity (có thể chuyển về màn login nếu cần)
        }

        val findDoctor: CardView = findViewById(R.id.cardFindDoctor)
        findDoctor.setOnClickListener{
            val intent = Intent(this, FindDoctorActivity::class.java)
            startActivity(intent);
        }

        val labTest: CardView = findViewById(R.id.cardLabTest)
        labTest.setOnClickListener{
            val intent = Intent(this, LabTestActivity::class.java)
            startActivity(intent);
        }
    }
}
