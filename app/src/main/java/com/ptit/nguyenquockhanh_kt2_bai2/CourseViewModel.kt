package com.ptit.nguyenquockhanh_kt2_bai2

import android.app.Application
import androidx.lifecycle.*
import com.ptit.nguyenquockhanh_kt2_bai2.database.AppDatabase
import com.ptit.nguyenquockhanh_kt2_bai2.database.CourseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CourseViewModel(
    application: Application
): AndroidViewModel(application) {
    private val courseDao: CourseDao = AppDatabase.buildDatabase(application).courseDao()

    private val dispatchers = AppDispatchers(Dispatchers.Main, Dispatchers.IO, Dispatchers.Default)

    private val _courses = MediatorLiveData<List<Course>>()
    val courses: LiveData<List<Course>> get() = _courses
    private var courseSource: LiveData<List<Course>> = MutableLiveData()

    lateinit var singleCourse: LiveData<Course>
    private val _coursesA = MediatorLiveData<Int>()
    val coursesA: LiveData<Int> get() = _coursesA
    private var courseASource: LiveData<Int> = MutableLiveData()

    fun saveCourse(course: Course){
        viewModelScope.launch(dispatchers.io){
            courseDao.saveCourse(course)
        }
    }

    fun getAllCourse() {
        viewModelScope.launch(dispatchers.main) {
            _courses.removeSource(courseSource)
            withContext(dispatchers.io) {
                courseSource = courseDao.getAll()
            }
            _courses.addSource(courseSource){
                _courses.value = it
            }
        }
    }

    fun getCourseById(id: Long) {
        singleCourse = courseDao.getById(id)
    }

    fun updateCourse(course: Course){
        viewModelScope.launch(dispatchers.io){
            courseDao.updateCourse(course)
        }
    }

    fun searchCourse(name: String){
        viewModelScope.launch(dispatchers.main) {
            _courses.removeSource(courseSource)
            withContext(dispatchers.io) {
                courseSource = courseDao.searchByName("%$name%")
            }
            _courses.addSource(courseSource){
                _courses.value = it
            }
        }
    }

    fun deleteCourse(course: Course){
        viewModelScope.launch(dispatchers.io) {
            courseDao.deleteCourse(course)
        }
    }

    fun countActive(){
        viewModelScope.launch(dispatchers.io){
            _coursesA.removeSource(courseASource)
            withContext(dispatchers.main){
                courseASource = courseDao.getActive()
            }
            _coursesA.addSource(courseASource){
                _coursesA.value = it
            }
        }
    }
}