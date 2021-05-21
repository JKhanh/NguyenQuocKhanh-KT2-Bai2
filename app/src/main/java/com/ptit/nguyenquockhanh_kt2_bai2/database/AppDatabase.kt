package com.ptit.nguyenquockhanh_kt2_bai2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ptit.nguyenquockhanh_kt2_bai2.Course

@Database(
    entities = [Course::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun courseDao(): CourseDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun buildDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "HocOnline.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}