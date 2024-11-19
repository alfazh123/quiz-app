package com.example.myquizapp.quiztwo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myquizapp.utils.Answer
import com.example.myquizapp.utils.QuestionV2
import com.example.myquizapp.utils.generateDummyQuestionV2

class QuizV2ViewModel: ViewModel() {

    private var _indexedValue = MutableLiveData<Int>().apply { value = 0 }
    val indexedValue: LiveData<Int> = _indexedValue

    private var _isLastQuestion = MutableLiveData<Boolean>().apply { value = false }
    val isLastQuestion: LiveData<Boolean> = _isLastQuestion

    fun isEndOfQuestion(listQuestions: ArrayList<MutableList<QuestionV2>>): Boolean {
        if (_indexedValue.value == listQuestions.size - 1) {
            _isLastQuestion.value = true
        }
        return _isLastQuestion.value ?: false
    }

    fun addIndexedValue() {
        _indexedValue.value = _indexedValue.value?.plus(1)
        answerSize = 0
    }

    val questions = generateDummyQuestionV2()

    var answers: MutableList<MutableList<Answer>> = arrayListOf()
    var answerSize = 0

    fun addAnswer(answer: Answer) {

        if (answers.isEmpty()) {
            for (i in 0 until questions.size) {
                answers.add(arrayListOf())
            }
        }

        answers[indexedValue.value!!].add(answer)
        answerSize++
    }

}