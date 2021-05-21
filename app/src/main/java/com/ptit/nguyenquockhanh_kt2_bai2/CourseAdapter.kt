package com.ptit.nguyenquockhanh_kt2_bai2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ptit.nguyenquockhanh_kt2_bai2.databinding.ItemCourseBinding
import java.util.concurrent.Executors

class CourseAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val courseClickListener: CourseClickListener
): ListAdapter<Course, CourseAdapter.CourseViewHolder>(
    AsyncDifferConfig.Builder(
        CourseDiffCallback()
    )
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()
) {
    class CourseViewHolder(
        private val binding: ItemCourseBinding,
        private val lifecycleOwner: LifecycleOwner,
        private val courseClickListener: CourseClickListener
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(course: Course){
            binding.course = course
            binding.clickListener = courseClickListener
            binding.lifecycleOwner = lifecycleOwner

            binding.active.text = if(course.active) "ACTIVE" else "INACTIVE"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCourseBinding.inflate(layoutInflater, parent, false)
        return CourseViewHolder(binding, lifecycleOwner, courseClickListener)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = getItem(position)
        holder.bind(course)
    }
}

class CourseDiffCallback: DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem == newItem
    }

}
