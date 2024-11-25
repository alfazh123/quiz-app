package com.example.myquizapp.utils


// this is the data class for the question2activity

data class QuestionV2(
    val page: Int,
    val question: String,
    val number: Int,
    var isChecked: Boolean = false,
    var value: Int? = null
)

val dummyQuestionV2 = ArrayList<MutableList<QuestionV2>>()

fun generateDummyQuestionV2(): ArrayList<MutableList<QuestionV2>> {
    var numberQ = 1
    for (i in 1..3) {
        val questionList = mutableListOf<QuestionV2>()
        for (j in 1..5) {
            questionList.add(QuestionV2(i, "Question $numberQ", numberQ))
            numberQ++
        }
        dummyQuestionV2.add(questionList)
    }
    return dummyQuestionV2
}