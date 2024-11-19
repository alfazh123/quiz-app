package com.example.myquizapp.utils

data class Question(
    val question: String,
    var isChecked: Boolean = false,
    val value: Int? = null
)

val dummyQuestion = ArrayList<Question>()

fun generateDummyQuestion(): ArrayList<Question> {
    for (i in 1..10) {
        dummyQuestion.add(Question("Question $i"))
    }
    return dummyQuestion
}

data class Answer(
    val question: String,
    val index: Int
)