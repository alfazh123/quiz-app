package com.example.myquizapp.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myquizapp.data.AnswerRepository
import com.example.myquizapp.data.local.entity.QuizEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class QuizViewModel(val repository: AnswerRepository): ViewModel() {

    private var _indexedValue = MutableLiveData<Int>().apply { value = 0 }
    val indexedValue: LiveData<Int> = _indexedValue

    private var _isLastQuestion = MutableLiveData<Boolean>().apply { value = false }

    fun isEndOfQuestion(listQuestions: ArrayList<MutableList<Question>>): Boolean {
        if (_indexedValue.value == listQuestions.size - 1) {
            _isLastQuestion.value = true
        }
        return _isLastQuestion.value ?: false
    }

    fun addIndexedValue() {
        _indexedValue.value = _indexedValue.value?.plus(1)
        answerSize = 0
    }

    val questions = arrayListOf<MutableList<Question>>()

    init {
        if (questions.isEmpty()) {
            questions.addAll(generateDummyQuestionV2())
        }
    }

    var answers: MutableList<MutableList<Answer>> = arrayListOf()
    var answerSize = 0

    fun addAnswer(answer: Answer) {

        if (answers.isEmpty()) {
            for (i in 0 until questions.size) {
                answers.add(arrayListOf())
            }
        }

        questions[_indexedValue.value!!].find {
            it.question == answer.question
        }?.let {
            if (it.isChecked) {
                return
            } else {
                it.isChecked = true
                it.value = answer.value

                answers[_indexedValue.value!!].add(answer)
                answerSize++
            }
        }
    }

    fun submitAnswer() {
        val result = resultAnswer().joinToString(",")
        val calendar = Calendar.getInstance()
        val idQuiz = calendar.timeInMillis
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val date = dateFormat.format(Date())
        val quizEntity = QuizEntity(
            idQuiz.toString(),
            result,
            date
        )

        try {
            viewModelScope.launch {
                repository.submitQuizAnswer(quizEntity)
            }
        } catch (e: Exception) {
            Log.e("QuizV2Activity", "Error: ${e.message}")
        }

        questions.clear()
        answers.clear()
    }

    private val _quizResult = MutableLiveData<List<QuizEntity>>()
    val quizResult: LiveData<List<QuizEntity>> = _quizResult

    suspend fun getAllQuizResult() {
        _quizResult.value = repository.getAnswers()
    }

    suspend fun deleteAllQuizResult() {
        repository.deleteAllAnswers()
        _quizResult.value = emptyList()
    }

    private fun sortAnswer(): MutableList<MutableList<Answer>> {
        val sortedAnswer = mutableListOf<MutableList<Answer>>()
        for (i in 0 until questions.size) {
            sortedAnswer.add(answers[i].sortedBy { it.QuestionNumber }.toMutableList())
        }

        return sortedAnswer
    }

    fun resultAnswer(): ArrayList<Int> {
        val sortedAnswer = sortAnswer()

        val resultMap = arrayListOf<Result>()
        val resultValue = arrayListOf<Int>()
        for (i in 0 until sortedAnswer.size) {
            for (j in 0 until sortedAnswer[i].size) {
                val res = Result(
                    sortedAnswer[i][j].QuestionNumber,
                    sortedAnswer[i][j].value
                )
                resultValue.add(sortedAnswer[i][j].value)
                resultMap.add(res)
            }
        }

        return resultValue
    }

}