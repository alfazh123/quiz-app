package com.example.myquizapp.data.di

import android.content.Context
import com.example.myquizapp.data.AnswerRepository
import com.example.myquizapp.data.local.QuizDatabase

object Injection {
    fun providerRepository(context: Context): AnswerRepository {
        val database = QuizDatabase.getInstance(context)
        val dao = database.dao()
        return AnswerRepository.getInstance(dao)
    }
}