package com.example.myquizapp.quiztwo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myquizapp.R
import com.example.myquizapp.databinding.QuestionItemBinding
import com.example.myquizapp.utils.Question
import com.example.myquizapp.utils.QuestionV2

class QuizAdapterV2: ListAdapter<QuestionV2, QuizAdapterV2.QuizAdapterViewHolder>(DIFF_CALLBACK) {

    private lateinit var onAnswerClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(answer: Int, position: QuestionV2)
    }

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onAnswerClickCallback = onItemClickCallback
    }

    class QuizAdapterViewHolder(binging: QuestionItemBinding): RecyclerView.ViewHolder(binging.root) {
        val question = binging.questionTextView
        val radioGroup = binging.answersRadioGroup
        val checkIndicator = binging.checkedIndicator

        fun bind(question: QuestionV2, position: Int) {
            this.question.text = question.question
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizAdapterViewHolder {
        val binding = QuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuizAdapterViewHolder(binding)
    }

    @Suppress("DEPRECATION")
    override fun onBindViewHolder(holder: QuizAdapterViewHolder, position: Int) {
        holder.bind(getItem(position), position)

        holder.radioGroup.setOnCheckedChangeListener(null)
        holder.radioGroup.clearCheck()
        holder.checkIndicator.setImageResource(R.drawable.ic_uncheck)

        holder.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val radioChecked = radioGroup.findViewById<RadioButton>(i)
            val indexItem = radioGroup.indexOfChild(radioChecked)
            val valueAnswer = when (indexItem) {
                0 -> 1
                1 -> 2
                2 -> 3
                3 -> 4
                else -> 5
            }

            holder.checkIndicator.setImageResource(
                if (radioChecked.isChecked) {
                    R.drawable.ic_check
                } else {
                    R.drawable.ic_uncheck
                }
            )

            onAnswerClickCallback.onItemClicked(valueAnswer, getItem(position))
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<QuestionV2> =
            object : DiffUtil.ItemCallback<QuestionV2>() {
                override fun areItemsTheSame(oldItem: QuestionV2, newItem: QuestionV2): Boolean {
                    return oldItem.page == newItem.page
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: QuestionV2, newItem: QuestionV2): Boolean {
                    return oldItem == newItem
                }
            }
    }
}