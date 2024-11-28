package com.example.myquizapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myquizapp.data.local.entity.QuizEntity

@Dao
interface QuizDao {

    @Query("SELECT * FROM quiz")
    fun getAnswers(): List<QuizEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answer: QuizEntity)

    @Query("DELETE FROM quiz")
    fun deleteAllAnswers()

}