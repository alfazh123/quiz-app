package com.example.myquizapp.utils

data class Question(
    val question: String,
    val number: Int,
    var isChecked: Boolean = false,
    val value: Int? = null
)

val dummyQuestion = ArrayList<Question>()

fun generateDummyQuestion(): ArrayList<Question> {
    var numberQ = 1
    for (i in 1..10) {
        dummyQuestion.add(Question("Question $i", numberQ))
        numberQ++
    }
    return dummyQuestion
}

data class Answer(
    val question: String,
    val QuestionNumber: Int,
    val value: Int
)