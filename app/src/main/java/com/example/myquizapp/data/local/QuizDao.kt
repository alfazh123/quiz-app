package com.example.myquizapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myquizapp.data.local.entity.AnswerEntity

@Dao
interface QuizDao {

    @Query("SELECT * FROM answers")
    fun getAnswers(): List<AnswerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnswer(answer: AnswerEntity)

    @Query("DELETE FROM answers")
    fun deleteAllAnswers()

}