package com.example.myquizapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers")
class AnswerEntity (
    @ColumnInfo(name = "question_id")
    @PrimaryKey
    val questionId: Int,

    @ColumnInfo(name = "answer")
    val answer: String,
)