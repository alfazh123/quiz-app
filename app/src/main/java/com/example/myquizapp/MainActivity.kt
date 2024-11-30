package com.example.myquizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquizapp.data.ViewModelFactory
import com.example.myquizapp.databinding.ActivityMainBinding
import com.example.myquizapp.quiz.QuizActivity
import com.example.myquizapp.utils.CustomDialog
import com.example.myquizapp.utils.QuizViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<QuizViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvResults.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvResults.setHasFixedSize(true)

        binding.startTestButton.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }

        lifecycleScope.launch {
            viewModel.getAllQuizResult()

            viewModel.quizResult.observe(this@MainActivity) { result ->
                if (result.size > 0) {
                    binding.startTestButton.text = "Retake Quiz"
                    hasQuizResult(true)
                    val adapter = QuizResultAdapter(result.take(3))
                    binding.rvResults.adapter = adapter
                } else {
                    binding.startTestButton.text = "Take Quiz"
                    hasQuizResult(false)
                }
            }
        }


        binding.btnDeleteQuizResult.setOnClickListener {
            lifecycleScope.launch {
                viewModel.deleteAllQuizResult()
            }
        }

    }

    private fun hasQuizResult(isHasQuizResult: Boolean) {
        if (isHasQuizResult) {
            binding.quizLayout.visibility = View.VISIBLE
            binding.emptyLayout.visibility = View.GONE
        } else {
            binding.quizLayout.visibility = View.GONE
            binding.emptyLayout.visibility = View.VISIBLE
        }

    }
}