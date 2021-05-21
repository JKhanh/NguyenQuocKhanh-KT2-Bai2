package com.ptit.nguyenquockhanh_kt2_bai2

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ptit.nguyenquockhanh_kt2_bai2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProviders.of(this).get(CourseViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this
        viewModel.getAllCourse()
//        viewModel.countActive()
        setContentView(binding.root)

//        viewModel.coursesA.observe(this){
//            if(it != null){
//                binding.txtCourseNo.text = "$it + active Course"
//            }
//        }

        val adapter = CourseAdapter(this,
            object : CourseClickListener {
                override fun onClick(course: Course) {
                    val intent = Intent(this@MainActivity, MainActivity2::class.java)
                    intent.putExtra("Course", course.id)
                    startActivity(intent)
                }
            })

        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(this)

        binding.fab.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchCourse(it)
                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}