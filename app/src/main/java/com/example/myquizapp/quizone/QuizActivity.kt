package com.example.myquizapp.quizone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquizapp.ResultActivity
import com.example.myquizapp.databinding.ActivityQuizBinding
import com.example.myquizapp.utils.Answer
import com.example.myquizapp.utils.Question

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding

    private val viewmodel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val viewmodel = ViewModelProvider(this).get(QuizViewModel::class.java)

//        val answers: MutableList<Answer> = mutableListOf()

        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager

        val adapter = QuizAdapter(viewmodel)
        binding.recyclerView.adapter = adapter
        adapter.submitList(viewmodel.question)

        adapter.setOnClickCallback(object : QuizAdapter.OnItemClickCallback {
            override fun onItemClicked(answer: Int, position: Question) {
                Log.d("QuizActivity", "Answer: $answer of ${position.question}")
                val answersByQuestion = Answer(
                    position.question,
                    position.number,
                    answer
                )

                viewmodel.question.find {
                    it.question == position.question
                }?.let {
                    it.isChecked = true
                }

//                answers.add(answersByQuestion)

                viewmodel.addAnswer(answersByQuestion)

                if (viewmodel.answerSize == viewmodel.question.size) {
                    binding.btnSubmit.isEnabled = true
                } else {
                    binding.btnSubmit.isEnabled = false
                }

            }

        })

        setupAction()

    }

    private fun setupAction() {
        binding.btnSubmit.setOnClickListener {
            var result = ""
            for (i in 0 until viewmodel.answers.size) {
                result += "${viewmodel.answers[i].QuestionNumber} : ${viewmodel.answers[i].value}\n"
            }
            Log.d("QuizActivity", "Result ViewModel: ${viewmodel.answers}")
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            Log.d("QuizActivity", "Result: ${viewmodel.answers}")
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(ResultActivity.RESULT_VALUE, result)
            startActivity(intent)
        }
    }
}