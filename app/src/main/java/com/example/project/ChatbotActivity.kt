package com.example.project

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class ChatbotActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var editText: EditText
    private lateinit var buttonSend: Button
    private lateinit var adapter: ArrayAdapter<String>
    private val messages = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chatbot)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listView    = findViewById(R.id.listViewMessages)
        editText    = findViewById(R.id.editTextMessage)
        buttonSend  = findViewById(R.id.buttonSend)

        // Dùng layout tùy chỉnh item_message.xml để có text trắng
        adapter = object : ArrayAdapter<String>(
            this,
            R.layout.item_message,
            R.id.messageText,
            messages
        ) {}
        listView.adapter = adapter

        buttonSend.setOnClickListener {
            val userMessage = editText.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                messages.add("Bạn: $userMessage")
                adapter.notifyDataSetChanged()
                editText.text.clear()
                getBotResponse(userMessage)
            }
        }
    }

    private fun getBotResponse(userInput: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = callGeminiApi(userInput)
                withContext(Dispatchers.Main) {
                    messages.add("Trợ lý: $response")
                    adapter.notifyDataSetChanged()
                    listView.smoothScrollToPosition(messages.size - 1)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    messages.add("Lỗi: ${e.message}")
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun callGeminiApi(prompt: String): String {
        return try {
            val apiKey = "AIzaSyBaZW9xbIV9s39A9IsxbS4vJ1rIZsycF_Q"
            val url = URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=$apiKey")

            val requestBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply { put("text", prompt) })
                        })
                    })
                })
            }

            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json; charset=UTF-8")
                doOutput = true
                connectTimeout = 15_000
                readTimeout = 15_000
                outputStream.bufferedWriter().use { it.write(requestBody.toString()) }
            }

            if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                val response = conn.inputStream.bufferedReader().use { it.readText() }
                parseGeminiResponse(response)
            } else {
                "Lỗi HTTP ${conn.responseCode}: ${conn.responseMessage}"
            }
        } catch (e: Exception) {
            "Lỗi kết nối: ${e.localizedMessage}"
        }
    }

    private fun parseGeminiResponse(jsonResponse: String): String {
        return try {
            val data = JSONObject(jsonResponse)
            data.getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text")
        } catch (e: Exception) {
            "Lỗi parse phản hồi: ${e.localizedMessage}"
        }
    }
}
