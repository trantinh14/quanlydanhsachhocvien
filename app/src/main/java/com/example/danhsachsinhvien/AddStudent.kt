package com.example.danhsachsinhvien

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.danhsachsinhvien.databinding.ActivityAddStudentBinding
import com.google.gson.Gson

class AddStudent : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding
    private var isEdit = false
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nhận dữ liệu từ intent
        val studentJson = intent.getStringExtra("studentedit")
        isEdit = intent.getBooleanExtra("isEdit", false)
        position = intent.getIntExtra("positon", -1)

        // Nếu là edit thì pre-fill thông tin học viên
        if (isEdit && studentJson != null) {
            val studentInfo = Gson().fromJson(studentJson, Students::class.java)
            binding.idEt.setText(studentInfo.Id.toString())
            binding.firstnameEt.setText(studentInfo.firstName)
            binding.lastnameEt.setText(studentInfo.lastName)
            binding.ageEt.setText(studentInfo.age.toString())
            binding.gpascoreEt.setText(studentInfo.GPA.toString())

            // Không cho phép sửa ID khi edit
            binding.idEt.isEnabled = false
        }

        // Xử lý khi nhấn Submit
        binding.submitBtn.setOnClickListener {
            val id = binding.idEt.text.toString()
            val firstName = binding.firstnameEt.text.toString()
            val lastName = binding.lastnameEt.text.toString()
            val age = binding.ageEt.text.toString()
            val gpaScore = binding.gpascoreEt.text.toString()

            if (id.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty()
                && age.isNotEmpty() && gpaScore.isNotEmpty()) {

                val student = Students(
                    id.toInt(),
                    firstName,
                    lastName,
                    age.toInt(),
                    gpaScore.toDouble()
                )

                // Trả kết quả về màn hình ListStudent
                val resultIntent = Intent().apply {
                    putExtra("edited_item", Gson().toJson(student)) // Trả lại thông tin học viên
                    putExtra("isNew", true) // Đánh dấu là học viên mới
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()  // Kết thúc Activity và trả kết quả
            } else {
                Toast.makeText(this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }
}