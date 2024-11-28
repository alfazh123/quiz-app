package com.example.myquizapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz")
class QuizEntity (
    @ColumnInfo(name = "quiz_id")
    @PrimaryKey
    val quizId: String,

    @ColumnInfo(name = "quiz_answers")
    val quizAnswers: String,

    @ColumnInfo(name = "quiz_date")
    val quizDate: String
)