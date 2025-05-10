package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DoctordetailActivity : AppCompatActivity() {

    private val doctorDetails1 = arrayOf(
        arrayOf("Tên bác sĩ: Bác sĩ Ánh Sáng", "Địa chỉ bệnh viện: Quận 1, TP.HCM", "Kinh nghiệm: 6 năm", "SĐT: 9898989898", "680"),
        arrayOf("Tên bác sĩ: Bác sĩ Phúc Bình", "Địa chỉ bệnh viện: TP. Biên Hòa", "Kinh nghiệm: 8 năm", "SĐT: 7898989898", "980"),
        arrayOf("Tên bác sĩ: Bác sĩ Thắng Lợi", "Địa chỉ bệnh viện: TP. Huế", "Kinh nghiệm: 5 năm", "SĐT: 8898989898", "580"),
        arrayOf("Tên bác sĩ: Bác sĩ Đức Minh", "Địa chỉ bệnh viện: TP. Cần Thơ", "Kinh nghiệm: 6 năm", "SĐT: 9898989898", "580"),
        arrayOf("Tên bác sĩ: Bác sĩ An Phát", "Địa chỉ bệnh viện: TP. Đà Lạt", "Kinh nghiệm: 7 năm", "SĐT: 7798989898", "880")
    )


    private val doctorDetails2 = arrayOf(
        arrayOf("Tên bác sĩ: Bác sĩ Minh Hiền", "Địa chỉ bệnh viện: Quận Bình Thạnh, TP.HCM", "Kinh nghiệm: 5 năm", "SĐT: 9899899898", "600"),
        arrayOf("Tên bác sĩ: Bác sĩ Thu Hằng", "Địa chỉ bệnh viện: Quận Đống Đa, Hà Nội", "Kinh nghiệm: 15 năm", "SĐT: 7899899898", "900"),
        arrayOf("Tên bác sĩ: Bác sĩ Mai Hương", "Địa chỉ bệnh viện: TP. Nha Trang", "Kinh nghiệm: 8 năm", "SĐT: 8898999898", "300"),
        arrayOf("Tên bác sĩ: Bác sĩ Hà Vy", "Địa chỉ bệnh viện: TP. Đà Nẵng", "Kinh nghiệm: 6 năm", "SĐT: 9898000000", "500"),
        arrayOf("Tên bác sĩ: Bác sĩ Minh Ánh", "Địa chỉ bệnh viện: TP. Hải Phòng", "Kinh nghiệm: 7 năm", "SĐT: 7798989898", "800")
    )

    private val doctorDetails3 = arrayOf(
        arrayOf("Tên bác sĩ: Bác sĩ Hồng Nhung", "Địa chỉ bệnh viện: Quận Gò Vấp, TP.HCM", "Kinh nghiệm: 4 năm", "SĐT: 9899899898", "200"),
        arrayOf("Tên bác sĩ: Bác sĩ Nam Hải", "Địa chỉ bệnh viện: TP. Quy Nhơn", "Kinh nghiệm: 5 năm", "SĐT: 7899899898", "300"),
        arrayOf("Tên bác sĩ: Bác sĩ Minh Tâm", "Địa chỉ bệnh viện: TP. Buôn Ma Thuột", "Kinh nghiệm: 7 năm", "SĐT: 8898999898", "300"),
        arrayOf("Tên bác sĩ: Bác sĩ Đức Duy", "Địa chỉ bệnh viện: TP. Cần Thơ", "Kinh nghiệm: 6 năm", "SĐT: 9898000000", "500"),
        arrayOf("Tên bác sĩ: Bác sĩ Nhật Hào", "Địa chỉ bệnh viện: TP. Vũng Tàu", "Kinh nghiệm: 7 năm", "SĐT: 7798989898", "600")
    )

    private val doctorDetails4 = arrayOf(
        arrayOf("Tên bác sĩ: Bác sĩ Hoàng Quân", "Địa chỉ bệnh viện: Quận Tân Bình, TP.HCM", "Kinh nghiệm: 5 năm", "SĐT: 9899899898", "600"),
        arrayOf("Tên bác sĩ: Bác sĩ Phan Nam", "Địa chỉ bệnh viện: Quận Hai Bà Trưng, Hà Nội", "Kinh nghiệm: 15 năm", "SĐT: 7899899898", "900"),
        arrayOf("Tên bác sĩ: Bác sĩ Trí Dũng", "Địa chỉ bệnh viện: TP. Ninh Bình", "Kinh nghiệm: 8 năm", "SĐT: 8898999898", "300"),
        arrayOf("Tên bác sĩ: Bác sĩ Đạt Thành", "Địa chỉ bệnh viện: TP. Thái Nguyên", "Kinh nghiệm: 6 năm", "SĐT: 9898000000", "500"),
        arrayOf("Tên bác sĩ: Bác sĩ Anh Kiệt", "Địa chỉ bệnh viện: TP. Phan Thiết", "Kinh nghiệm: 7 năm", "SĐT: 7798989898", "800")
    )


    private val doctorDetails5 = arrayOf(
        arrayOf("Tên bác sĩ: Bác sĩ Tùng Lâm", "Địa chỉ bệnh viện: TP. Vinh", "Kinh nghiệm: 10 năm", "SĐT: 9898998988", "1600"),
        arrayOf("Tên bác sĩ: Bác sĩ Quốc Huy", "Địa chỉ bệnh viện: TP. Bắc Giang", "Kinh nghiệm: 15 năm", "SĐT: 7898998988", "1900"),
        arrayOf("Tên bác sĩ: Bác sĩ Hoàng An", "Địa chỉ bệnh viện: TP. Long Xuyên", "Kinh nghiệm: 9 năm", "SĐT: 8899998988", "1300"),
        arrayOf("Tên bác sĩ: Bác sĩ Khánh Duy", "Địa chỉ bệnh viện: TP. Tây Ninh", "Kinh nghiệm: 6 năm", "SĐT: 9898000000", "1500"),
        arrayOf("Tên bác sĩ: Bác sĩ Bảo Long", "Địa chỉ bệnh viện: TP. Trà Vinh", "Kinh nghiệm: 7 năm", "SĐT: 7798998988", "1800")
    )

    private lateinit var tv: TextView
    private lateinit var btn: Button
    private lateinit var doctor_details: Array<Array<String>>
    private lateinit var list: ArrayList<String>
    private lateinit var sa:SimpleAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctordetail)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tv = findViewById(R.id.textViewDDTitle)
        btn = findViewById(R.id.buttonDDBack)

        val it = intent
        val title = it.getStringExtra("title")
        tv.text = title

        doctor_details = when (title) {
            "Chuyên gia nội khoa" -> doctorDetails1
            "Chuyên gia dinh dưỡng" -> doctorDetails2
            "Nha sĩ" -> doctorDetails3
            "Bác sĩ phẫu thuật" -> doctorDetails4
            "Chuyên gia tim mạch" -> doctorDetails5
            else -> doctorDetails1
        }


        btn.setOnClickListener {
            startActivity(Intent(this, FindDoctorActivity::class.java))
        }

        val list = ArrayList<HashMap<String, String>>()

        for (i in doctor_details.indices) {
            val item = HashMap<String, String>()
            item["line1"] = doctor_details[i][0]
            item["line2"] = doctor_details[i][1]
            item["line3"] = doctor_details[i][2]
            item["line4"] = doctor_details[i][3]
            item["line5"] = "Cons Fees: ${doctor_details[i][4]}/-"
            list.add(item)
        }

        val sa = SimpleAdapter(
            this,
            list,
            R.layout.multi_lines,
            arrayOf("line1", "line2", "line3", "line4", "line5"),
            intArrayOf(R.id.Line_a, R.id.Line_b, R.id.Line_c, R.id.Line_d, R.id.Line_e)
        )

        val lst: ListView = findViewById(R.id.listViewDD)
        lst.adapter = sa

        lst.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, BookAppointmentActivity::class.java).apply {
                putExtra("text1", title)
                putExtra("text2", doctor_details[position][0])
                putExtra("text3", doctor_details[position][1])
                putExtra("text4", doctor_details[position][3])
                putExtra("text5", doctor_details[position][4])
            }
            startActivity(intent)
        }

    }
}
