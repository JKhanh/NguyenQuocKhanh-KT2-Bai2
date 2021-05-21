package com.ptit.nguyenquockhanh_kt2_bai2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Course(
    val name: String,
    val startDate: String,
    val major: String,
    val active: Boolean
){
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}
