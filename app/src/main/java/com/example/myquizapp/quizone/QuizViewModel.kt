package com.example.myquizapp.quizone

import androidx.lifecycle.ViewModel
import com.example.myquizapp.utils.Answer
import com.example.myquizapp.utils.generateDummyQuestion

class QuizViewModel: ViewModel() {

    val question = generateDummyQuestion()

    var answers: MutableList<Answer> = mutableListOf()
    var answerSize = 0

    fun addAnswer(answer: Answer) {
        answers.add(answer)
        answerSize++
    }

}