package com.example.danhsachsinhvien

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.danhsachsinhvien.databinding.ActivityStudentdetailBinding
import com.google.gson.Gson

class Studentdetail : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityStudentdetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentdetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val student = intent.getStringExtra("student-detail")
        val detailStudent = Gson().fromJson(student, Students::class.java)
        val position = intent.getIntExtra("position", -1 )



        binding.nameTv.text = detailStudent.lastName + detailStudent.firstName
        binding.ageTv.text = detailStudent.age.toString()
        binding.scoregpaTv.text = detailStudent.GPA.toString()


        binding.deleteBtn.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("isDelete", true)   // Đánh dấu là xóa
                putExtra("position", position) // Truyền vị trí của học viên cần xóa
            }
            setResult(RESULT_OK, resultIntent) // Trả kết quả về ListStudentActivity
            finish()  // Đóng Activity
        }
        binding.editBtn.setOnClickListener {
            val studentEdit = Intent(this, AddStudent::class.java).apply {
                putExtra("studentedit", student)
                putExtra("positon", position)
                putExtra("isEdit", true)
            }
            studentEdit.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
            startActivity(studentEdit)
            finish()
        }
    }
}