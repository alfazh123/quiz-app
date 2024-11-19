package com.example.myquizapp.quizone

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myquizapp.databinding.QuestionItemBinding
import com.example.myquizapp.utils.Question

class QuizAdapter(private val viewModel: QuizViewModel): ListAdapter<Question, QuizAdapter.QViewHolder>(DIFF_CALLBACK) {

    private lateinit var onAnswerClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(answer: Int, position: Question)
    }

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onAnswerClickCallback = onItemClickCallback
    }

    class QViewHolder(itemView: QuestionItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        val question: TextView = itemView.questionTextView
        val radioGroup: RadioGroup = itemView.answersRadioGroup

        fun bind(question: Question, position: Int) {
            this.question.text = question.question
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Question> =
            object : DiffUtil.ItemCallback<Question>() {
                override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
                    return oldItem.question == newItem.question
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QViewHolder {
        val binding = QuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QViewHolder, position: Int) {
        holder.bind(getItem(position), position)

        holder.radioGroup.setOnCheckedChangeListener { radioGroup, i ->

            val radioChecked = radioGroup.findViewById<RadioButton>(i)
            val indexChecked = radioGroup.indexOfChild(radioChecked)
            val valueAnswer = when (indexChecked) {
                0 -> 1
                1 -> 2
                2 -> 3
                3 -> 4
                else -> 5
            }
            onAnswerClickCallback.onItemClicked(valueAnswer, getItem(position))
        }

    }
}