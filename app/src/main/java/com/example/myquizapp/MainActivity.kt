package com.example.myquizapp

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myquizapp.databinding.ActivityMainBinding
import com.example.myquizapp.quizone.QuizActivity
import com.example.myquizapp.quiztwo.QuizV2Activity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startTestButton.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }

        binding.quizTwoButton.setOnClickListener {
            startActivity(Intent(this, QuizV2Activity::class.java))
        }

    }
}