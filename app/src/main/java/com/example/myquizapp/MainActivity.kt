package com.example.myquizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myquizapp.databinding.ActivityMainBinding
import com.example.myquizapp.quiz.QuizActivity

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

//        val list: ArrayList<Int> = arrayListOf(1, 3, 4, 2)
//        val stringList: String = Gson().toJson(list)
//
//        val listType = object : TypeToken<ArrayList<Int>>() {}.type
//        val listAgain: ArrayList<Int> = Gson().fromJson(stringList, listType)
//
//        binding.textRes.text = listAgain.toString()




    }
}