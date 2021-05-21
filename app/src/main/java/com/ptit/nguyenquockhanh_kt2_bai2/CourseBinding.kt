package com.ptit.nguyenquockhanh_kt2_bai2

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("app:course")
fun setCourse(recyclerView: RecyclerView, entries: List<Course>?){
    if(entries == null || recyclerView.adapter == null){
        return
    }
    (recyclerView.adapter as CourseAdapter).submitList(
        entries
    )
}