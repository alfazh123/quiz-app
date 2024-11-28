package com.example.myquizapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.myquizapp.data.local.entity.QuizEntity
import com.example.myquizapp.databinding.ResultListBinding

class QuizResultAdapter(private val quizResultList: List<QuizEntity>): RecyclerView.Adapter<QuizResultAdapter.ListViewHolder>() {
    class ListViewHolder(binding: ResultListBinding): RecyclerView.ViewHolder(binding.root) {
        val result = binding.tvResultQuiz
        val date = binding.tvDate

        fun bind(quizEntity: QuizEntity) {
            result.text = quizEntity.quizAnswers
            date.text = quizEntity.quizDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layout = ResultListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return quizResultList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(quizResultList[position])
    }
}