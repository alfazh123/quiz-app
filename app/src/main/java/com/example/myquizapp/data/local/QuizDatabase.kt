package com.example.myquizapp.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.myquizapp.data.local.entity.QuizEntity

@Database(entities = [QuizEntity::class], version = 1, exportSchema = false)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun dao(): QuizDao

    companion object {
        @Volatile
        private var instance: QuizDatabase? = null
        fun getInstance(context: Context): QuizDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java, "quiz_database"
                ).build()

        }
    }
}