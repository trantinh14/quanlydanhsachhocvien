package com.example.danhsachsinhvien

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.danhsachsinhvien.databinding.ActivityListStudentBinding
import com.google.gson.Gson

class listStudent : AppCompatActivity() {

    private lateinit var binding: ActivityListStudentBinding
    val studentsList = ArrayList<Students>()
    lateinit var adapter: ArrayAdapter<Students>

    // Khai báo launcher để nhận kết quả từ màn hình AddStudent
    private val editItemLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val editedItem = result.data?.getStringExtra("edited_item")
                val isNew = result.data?.getBooleanExtra("isNew", false) ?: false
                val isDelete = result.data?.getBooleanExtra("isDelete", false) ?: false
                val position = result.data?.getIntExtra("position", -1)

                if (editedItem != null) {
                    val student = Gson().fromJson(editedItem, Students::class.java)

                    when {
                        isNew -> {
                            // Nếu là học viên mới, thêm vào danh sách
                            studentsList.add(student)
                        }
                        isDelete && position != null && position >= 0 -> {
                            // Nếu là yêu cầu xóa, xoá học viên tại vị trí
                            studentsList.removeAt(position)
                        }
                        position != null && position >= 0 -> {
                            // Nếu là yêu cầu chỉnh sửa, cập nhật học viên tại vị trí
                            studentsList[position] = student
                        }
                    }

                    // Cập nhật adapter
                    adapter.notifyDataSetChanged()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Khởi tạo adapter cho danh sách học viên
        adapter = listStudentItem(this, studentsList)
        binding.listStudent.adapter = adapter

        // Thêm một số học viên mẫu
        studentsList.add(Students(1, "Tran", "Tinh", 18, 3.2))
        studentsList.add(Students(2, "Tran", "Nguyen", 18, 3.7))
        studentsList.add(Students(3, "Nguyen", "Thu", 18, 3.5))

        adapter.notifyDataSetChanged()

        binding.listStudent.setOnItemClickListener { parent, view, position, id ->
            val student = studentsList[position]
            val json = Gson().toJson(student)
            val intent = Intent(this, Studentdetail::class.java).apply {
                putExtra("student-detail", json)
                putExtra("position", position)
            }
            editItemLauncher.launch(intent)
        }

        binding.viewBestStudentBtn.setOnClickListener {
            val bestStudents = studentsList.filter { it.GPA >= 3.5 }

            if (bestStudents.isNotEmpty()) {
                // Cập nhật adapter với danh sách học sinh giỏi
                adapter = listStudentItem(this, bestStudents.toMutableList())
                binding.listStudent.adapter = adapter
            } else {
                Toast.makeText(this, "Không có học sinh nào có điểm GPA > 3.5", Toast.LENGTH_SHORT).show()
            }
        }

        binding.viewAllBtn.setOnClickListener {
            // Hiển thị tất cả học sinh
            adapter = listStudentItem(this, studentsList)
            binding.listStudent.adapter = adapter
        }

        // Mở màn hình AddStudent để thêm học viên mới
        binding.addBtn.setOnClickListener {
            val intent = Intent(this, AddStudent::class.java)
            editItemLauncher.launch(intent)
        }
    }
}