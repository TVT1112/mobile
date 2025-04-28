package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    private lateinit var edUsername: EditText;
    private lateinit var edPassword: EditText;
    private lateinit var edEmail: EditText;
    private lateinit var edConfirmPassword: EditText;
    private lateinit var btn: Button;
    private lateinit var tv: TextView;
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edUsername = findViewById(R.id.editRegUsername);
        edPassword = findViewById(R.id.editRegPassword);
        edEmail = findViewById(R.id.editRegEmail);
        edConfirmPassword= findViewById(R.id.editRegConfirmPassword);
        btn = findViewById(R.id.buttonReg);
        tv= findViewById(R.id.textViewAlreadyHaveUser);
        database = Database(this, "healthcare", null, 1)

        btn.setOnClickListener{
            val username = edUsername.text.toString();
            val password = edPassword.text.toString();
            val email = edEmail.text.toString();
            val confirm = edConfirmPassword.text.toString();

            if(username.length ==0 || password.length==0||email.length==0||confirm.length==0){
                Toast.makeText(applicationContext,"vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
            }
            else{
                if(password==confirm){
                    if(isValid(password)){
                        database.register(username,email,password);
                        Toast.makeText(applicationContext,"Đã đăng kí thành công", Toast.LENGTH_SHORT).show();
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(applicationContext,"mập khẩu phải chứa ít nhất 8 kí tự, có chữ, số, và kí tự đặc biệt", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(applicationContext,"mập khẩu và xác nhận không trùng", Toast.LENGTH_SHORT).show();
                }
            }
        }

        tv.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent);
        }
    }

    fun isValid(passwordhere: String): Boolean {
        var f1 = 0
        var f2 = 0
        var f3 = 0

        if (passwordhere.length < 8) {
            return false
        } else {
            for (p in 0..<passwordhere.length) {
                if (Character.isLetter(passwordhere[p])) {
                    f1 = 1
                }
            }

            for (r in 0..<passwordhere.length) {
                if (Character.isDigit(passwordhere[r])) {
                    f2 = 1
                }
            }

            for (s in 0..<passwordhere.length) {
                val c = passwordhere[s]
                if ((c.code >= 33 && c.code <= 46) || c.code == 64) {
                    f3 = 1
                }
            }

            if (f1 == 1 && f2 == 1 && f3 == 1) {
                return true
            }
        }
        return false
    }

}