package com.example.myquizapp.quizone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquizapp.MainActivity
import com.example.myquizapp.ResultActivity
import com.example.myquizapp.databinding.ActivityQuizBinding
import com.example.myquizapp.utils.Answer
import com.example.myquizapp.utils.Question

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewmodel = ViewModelProvider(this).get(QuizViewModel::class.java)

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
                    answer
                )

                viewmodel.question.find {
                    it.question == position.question
                }?.let {
                    it.isChecked = true
                }

//                answers.add(answersByQuestion)

                viewmodel.addAnswer(answersByQuestion)
                Log.d("QuizActivity", "Position: ${viewmodel.answerSize}")
                Log.d("QuizActivity", "Answers: ${viewmodel.question.size}")
                Log.d("QuizActivity", "Enable : ${viewmodel.answerSize < viewmodel.question.size}")

                if (viewmodel.answerSize == viewmodel.question.size) {
                    binding.btnSubmit.isEnabled = true
                } else {
                    binding.btnSubmit.isEnabled = false
                }

            }

        })

            binding.btnSubmit.setOnClickListener {
                var result = ""
                for (i in 0 until viewmodel.answers.size) {
                    result += "${viewmodel.answers[i].question} : ${viewmodel.answers[i].index}\n"
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