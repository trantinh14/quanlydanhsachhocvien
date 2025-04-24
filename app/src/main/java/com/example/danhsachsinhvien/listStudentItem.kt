package com.example.danhsachsinhvien

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.danhsachsinhvien.databinding.ActivityListStudentItemBinding

class listStudentItem(context: Context, private val studentData : List<Students>) : ArrayAdapter<Students>
    (context,0 , studentData) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?:LayoutInflater.from(context)
            .inflate(R.layout.activity_list_student_item, parent, false)


            val student = studentData[position]
            val identifi = view.findViewById<TextView>(R.id.studentId)
            val fullName = view.findViewById<TextView>(R.id.studentname_tv)
            val gpa = view.findViewById<TextView>(R.id.studentgpa_tv)

            identifi.text = student.Id.toString()
            fullName.text = student.firstName + ' ' + student.lastName
            gpa.text = student.GPA.toString()
        return view
    }

}