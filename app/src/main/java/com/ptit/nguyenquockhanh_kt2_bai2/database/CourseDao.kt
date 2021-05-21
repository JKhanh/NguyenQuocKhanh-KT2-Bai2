package com.ptit.nguyenquockhanh_kt2_bai2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ptit.nguyenquockhanh_kt2_bai2.Course

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCourse(course: Course)

    @Update
    fun updateCourse(course: Course)

    @Delete
    fun deleteCourse(course: Course)

    @Query("SELECT * FROM course")
    fun getAll(): LiveData<List<Course>>

    @Query("SELECT * FROM course WHERE id =:id")
    fun getById(id: Long): LiveData<Course>

    @Query("SELECT * FROM course WHERE name LIKE :name")
    fun searchByName(name: String): LiveData<List<Course>>

    @Query("SELECT COUNT(*) FROM course WHERE active =:active ")
    fun getActive(active: Boolean = true): LiveData<Int>
}