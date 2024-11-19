package com.example.myquizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myquizapp.databinding.ActivityMainBinding
import com.example.myquizapp.quizone.QuizActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val RESULT_VALUE = "result_value"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startTestButton.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }

        val result_val = intent.getStringExtra(RESULT_VALUE)

        binding.resultValue.text = result_val.toString()

    }
}