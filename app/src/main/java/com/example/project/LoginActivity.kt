package com.example.project

import android.content.Context
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

class LoginActivity : AppCompatActivity() {

    private lateinit var edUsername:EditText;
    private lateinit var edPassword: EditText
    private lateinit var btn: Button;
    private lateinit var tv: TextView;
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edUsername = findViewById(R.id.editLoginUsername);
        edPassword = findViewById(R.id.editLoginPassword);
        btn = findViewById(R.id.buttonLogin);
        tv= findViewById(R.id.textViewNewUser);
        database = Database(this, "healthcare", null, 1)

        btn.setOnClickListener{
            val username = edUsername.text.toString();
            val password = edPassword.text.toString();

            if(username.length ==0 || password.length==0){
                Toast.makeText(applicationContext,"vui lòng nhập thông tin",Toast.LENGTH_SHORT).show();
            }
            else{
                if(database.login(username,password)==1){
                    Toast.makeText(applicationContext,"đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    val sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("username", username)
                    editor.apply()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent);
                }
                else{
                    Toast.makeText(applicationContext,"không đúng",Toast.LENGTH_SHORT).show();
                }
            }

        }

        tv.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent);
        }
    }
}