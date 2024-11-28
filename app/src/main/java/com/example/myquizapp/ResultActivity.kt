package com.example.myquizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BuildCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquizapp.databinding.ActivityResultBinding
import com.example.myquizapp.quiz.ResultAdapter

@OptIn(BuildCompat.PrereleaseSdkCheck::class)
class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    companion object {
        const val RESULT_VALUE = "result_value"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION") val result_val: ArrayList<Int>? = intent.getIntegerArrayListExtra(RESULT_VALUE)

        if (!result_val.isNullOrEmpty()) {
            binding.resultValue.text = result_val.toString()
        }

        val result = setResultKey(result_val!!)
        Log.d("ResultActivity", "Result: $result")

        binding.recyclerView2.layoutManager = LinearLayoutManager(this)
        binding.recyclerView2.setHasFixedSize(true)
        val adapter = ResultAdapter(result)
        binding.recyclerView2.adapter = adapter

        onBackPressedCallback()
    }

    private fun setResultKey(result: ArrayList<Int>): MutableMap<Int, Int> {
        val map = mutableMapOf<Int, Int>()
        for (i in 0..<result.size) {
            if (map[result[i]]==null) {
                map[result[i]] = 1
            } else {
                map[result[i]] = map[result[i]]!! + 1
            }
        }
        return map
    }

    fun onBackPressedCallback() {
        this.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@ResultActivity, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }

}