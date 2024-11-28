package com.example.myquizapp.data

import com.example.myquizapp.data.local.entity.AnswerEntity
import com.example.myquizapp.data.local.QuizDao

class AnswerRepository(
    private val dao: QuizDao
) {

    fun getAnswers(): List<AnswerEntity> {
        return dao.getAnswers()
    }

    fun insertAnswer(answer: AnswerEntity) {
        dao.insertAnswer(answer)
    }

    fun deleteAllAnswers() {
        dao.deleteAllAnswers()
    }

    companion object {
        @Volatile
        private var instance: AnswerRepository? = null
        fun getInstance(dao: QuizDao): AnswerRepository =
            instance ?: synchronized(this) {
                instance ?: AnswerRepository(dao)
            }.also { instance = it }
    }

}