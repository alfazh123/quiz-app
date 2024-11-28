package com.example.myquizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquizapp.data.ViewModelFactory
import com.example.myquizapp.databinding.ActivityMainBinding
import com.example.myquizapp.quiz.QuizActivity
import com.example.myquizapp.utils.QuizViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<QuizViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startTestButton.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }

        lifecycleScope.launch {
            viewModel.getAllQuizResult()

            viewModel.quizResult.observe(this@MainActivity) { result ->
                if (result.size > 0) {
                    binding.welcomeText.text = "Welcome you have taken ${result.size} quiz before"
                    binding.btnDeleteQuizResult.visibility = View.VISIBLE
                    binding.rvResults.visibility = View.VISIBLE
                    binding.rvResults.layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.rvResults.setHasFixedSize(true)
                    val adapter = QuizResultAdapter(result.take(3))
                    binding.rvResults.adapter = adapter
                } else {
                    binding.welcomeText.text = "Welcome you have not taken any quiz before"
                    binding.btnDeleteQuizResult.visibility = View.GONE
                    binding.rvResults.visibility = View.GONE
                }
                Log.d("MainActivity", "Result: ${result.map { 
                    it.quizId to it.quizAnswers
                }}")
                Log.d("MainActivity", "Result Size: ${result.size}")
            }
        }


        binding.btnDeleteQuizResult.setOnClickListener {
            lifecycleScope.launch {
                viewModel.deleteAllQuizResult()
            }
        }

    }
}