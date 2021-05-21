package com.ptit.nguyenquockhanh_kt2_bai2

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.ptit.nguyenquockhanh_kt2_bai2.databinding.ActivityMain2Binding
import java.util.*

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var viewModel: CourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        viewModel = ViewModelProviders.of(this).get(CourseViewModel::class.java)
        setContentView(binding.root)

        val id: Long = intent.getLongExtra("Course", 0)
        if (id != 0L) {
            viewModel.getCourseById(id)
            viewModel.singleCourse.observe(this) { course ->
                if (course != null) {
                    binding.etName.setText(course.name)
                    binding.etDate.setText(course.startDate)
                    binding.snMajor.setSelection(when(course.major){
                            "Tieng anh" -> 0
                            "CNTT" -> 1
                            "Kinh te" -> 2
                            "Truyen Thong" -> 3
                        else -> 0
                    })
                    binding.cbActive.isChecked = course.active
                }
            }
        }

        binding.etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this, { _, year, month, day ->
                    binding.etDate.setText("$day/${month+1}/${year}")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text
            val date = binding.etDate.text
            val major = binding.snMajor.selectedItem.toString()
            val active = binding.cbActive.isChecked

            if (name.isNullOrBlank() || date.isNullOrBlank()) {
                Toast.makeText(this, "Some field cannot be null!!", Toast.LENGTH_LONG)
                    .show()
            } else {
                val course = Course(
                    name.toString(),
                    date.toString(),
                    major,
                    active
                ).apply {
                    this.id = id
                }
                if (id != 0L) {
                    viewModel.updateCourse(course)
                } else {
                    viewModel.saveCourse(course)
                }

                this.finish()
            }
        }


        binding.btnDelete.setOnClickListener {
            val name = binding.etName.text
            val date = binding.etDate.text
            val major = binding.snMajor.selectedItem.toString()
            val active = binding.cbActive.isChecked

            if (id == 0L || active) {
                Toast.makeText(this, "Cannot delete this!", Toast.LENGTH_LONG).show()
            } else {
                val course = Course(
                    name.toString(),
                    date.toString(),
                    major,
                    active
                ).apply {
                    this.id = id
                }
                this.finish()
                viewModel.deleteCourse(course)
            }
        }
    }
}