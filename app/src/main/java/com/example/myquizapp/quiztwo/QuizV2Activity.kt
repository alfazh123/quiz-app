package com.example.myquizapp.quiztwo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquizapp.MainActivity
import com.example.myquizapp.R
import com.example.myquizapp.ResultActivity
import com.example.myquizapp.databinding.ActivityQuizV2Binding
import com.example.myquizapp.utils.Answer
import com.example.myquizapp.utils.QuestionV2
import com.example.myquizapp.utils.generateDummyQuestionV2

class QuizV2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizV2Binding

    private lateinit var viewModel: QuizV2ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(QuizV2ViewModel::class.java)

        binding = ActivityQuizV2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.submitButton.isEnabled = false

        val listQuestions = viewModel.questions

        Log.d("QuizV2Activity", "Questions: $listQuestions")

        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager

        val adapter = QuizAdapterV2()
        binding.recyclerView.adapter = adapter
        viewModel.indexedValue.observe(this) { indexedValue ->
            adapter.submitList(viewModel.questions[indexedValue])
            val isEnd = viewModel.isEndOfQuestion(viewModel.questions)
            setupAction(isEnd)

            binding.progressNumber.text = "${indexedValue + 1}/${viewModel.questions.size}"

        }

        adapter.setOnClickCallback(object : QuizAdapterV2.OnItemClickCallback {
            override fun onItemClicked(answer: Int, position: QuestionV2) {
                Log.d("QuizV2Activity", "Answer: $answer of ${position.question}")
                val answerByQuestion = Answer(
                    position.question,
                    position.number,
                    answer
                )

                viewModel.addAnswer(answerByQuestion)

                Log.d("QuizV2Activity", "Question: ${viewModel.questions}")

                if (viewModel.answerSize >= listQuestions[viewModel.indexedValue.value!!].size) {
                    binding.submitButton.isEnabled = true
                } else {
                    binding.submitButton.isEnabled = false
                }

                val progress = if (position.number%viewModel.questions[viewModel.indexedValue.value!!].size != 0) {
                    viewModel.answerSize%viewModel.questions[viewModel.indexedValue.value!!].size
                } else  {
                    viewModel.questions[viewModel.indexedValue.value!!].size
                }

                binding.progressIndicator.progress = ((progress) * 100) / listQuestions[viewModel.indexedValue.value!!].size
            }
        })
    }

    private fun setupAction(isEnd:Boolean) {
        binding.submitButton.text = if (isEnd) "Submit" else "Next"

        binding.submitButton.setOnClickListener {
            if (isEnd) {
                Log.d("QuizV2Activity", "Submit ${viewModel.answers}")
                val intent = Intent(this, ResultActivity::class.java)
                intent.putIntegerArrayListExtra(ResultActivity.RESULT_VALUE, viewModel.resultAnswer())
                startActivity(intent)
            } else {
                viewModel.addIndexedValue()
                binding.submitButton.isEnabled = false
                binding.progressIndicator.progress = 0
            }
        }
    }

}