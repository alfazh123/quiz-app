package com.example.myquizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myquizapp.databinding.ActivityMainBinding
import com.example.myquizapp.quiztwo.QuizV2Activity
import com.example.myquizapp.testfragment.QuizActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.quizTwoButton.setOnClickListener {
            startActivity(Intent(this, QuizV2Activity::class.java))
        }

        binding.startTestButton.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }


    }
}