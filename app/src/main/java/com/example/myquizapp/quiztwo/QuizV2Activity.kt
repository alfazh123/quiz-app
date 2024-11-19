package com.example.myquizapp.quiztwo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
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

//    private var indexedValue = 0

    private val viewModel by lazy {
        ViewModelProvider(this).get(QuizV2ViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val viewModel = ViewModelProvider(this).get(QuizV2ViewModel::class.java)

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
            adapter.submitList(listQuestions[indexedValue])
            val isEnd = viewModel.isEndOfQuestion(listQuestions)
            Log.d("QuizV2Activity", "Is End: $isEnd")
            setupAction(isEnd)

//            isButtonEnabled()

            Log.d("QuizV2Activity", "Indexed Value: $indexedValue")
            Log.d("QuizV2Activity", "Length Question: ${listQuestions.size}")
            Log.d("QuizV2Activity", "Indexed Value: ${listQuestions}")
        }

        adapter.setOnClickCallback(object : QuizAdapterV2.OnItemClickCallback {
            override fun onItemClicked(answer: Int, position: QuestionV2) {
                Log.d("QuizV2Activity", "Answer: $answer of ${position.question}")
                val answerByQuestion = Answer(
                    position.question,
                    answer
                )

                viewModel.addAnswer(answerByQuestion)

                viewModel.indexedValue.observe(this@QuizV2Activity) { indexedValue ->
                    viewModel.questions.find {
                        it[indexedValue].question == position.question
                    }?.let {
                        it[indexedValue].isChecked = true
                    }
                }

                Log.d("QuizV2Activity", "Answer Size: ${viewModel.answerSize}")

                if(viewModel.answerSize >= listQuestions[viewModel.indexedValue.value!!].size) {
                    binding.submitButton.isEnabled = true
                } else {
                    binding.submitButton.isEnabled = false
                }
            }
        })
    }

    private fun setupAction(isEnd:Boolean) {
        binding.submitButton.text = if (isEnd) "Submit" else "Next"

        val list: MutableList<MutableList<Answer>> = viewModel.answers
        list.map {
            it.sortBy {
                it.question
            }
        }
//
//
        var result = ""
        list.map {
            it.forEach {
                result += "$it \n"
            }
        }

        binding.submitButton.setOnClickListener {
            if (isEnd) {
                Log.d("QuizV2Activity", "Submit ${viewModel.answers}")
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(ResultActivity.RESULT_VALUE, viewModel.answers.toString())
//                intent.putExtra(ResultActivity.RESULT_VALUE, result)
                startActivity(intent)
            } else {
                viewModel.addIndexedValue()
                binding.submitButton.isEnabled = false
            }
        }
    }

}