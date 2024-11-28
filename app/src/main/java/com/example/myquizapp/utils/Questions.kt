package com.example.myquizapp.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


// Model class for Quiz

data class Question(
    val page: Int,
    val question: String,
    val number: Int,
    var isChecked: Boolean = false,
    var value: Int? = null
)

@Parcelize
data class Result(
    val indexQ: Int,
    val answer: Int
) : Parcelable

data class Answer(
    val question: String,
    val QuestionNumber: Int,
    val value: Int
)

fun generateDummyQuestionV2(): ArrayList<MutableList<Question>> {
    val dummyQuestion = ArrayList<MutableList<Question>>()
    if (dummyQuestion.isNotEmpty()) {
        return dummyQuestion
    }
    var numberQ = 1
    for (i in 1..3) {
        val questionList = mutableListOf<Question>()
        for (j in 1..5) {
            questionList.add(Question(i, "Question $numberQ", numberQ))
            numberQ++
        }
        dummyQuestion.add(questionList)
    }
    return dummyQuestion
}