package com.example.myquizapp.data

import com.example.myquizapp.data.local.QuizDao
import com.example.myquizapp.data.local.entity.QuizEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnswerRepository(
    private val dao: QuizDao
) {

    suspend fun getAnswers(): List<QuizEntity>  = withContext(Dispatchers.IO){
        dao.getAnswers()
    }

    suspend fun submitQuizAnswer(answer: QuizEntity) = withContext(Dispatchers.IO) {
        dao.insertAnswer(answer)
    }

    suspend fun deleteAllAnswers() = withContext(Dispatchers.IO) {
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