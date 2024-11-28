package com.example.myquizapp.quiz

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquizapp.MainActivity
import com.example.myquizapp.ResultAdapter
import com.example.myquizapp.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val RESULT_VALUE = "result_value"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result_val: ArrayList<Int>? = arguments?.getIntegerArrayList(RESULT_VALUE)

        val result = setResultKey(result_val!!)

        // action
        onBackPressedCallback()

        // view
        setupResult(result)
    }

    private fun setupResult(list: MutableMap<Int, Int>) {
        binding.recyclerView2.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView2.setHasFixedSize(true)
        val adapter = ResultAdapter(list)
        binding.recyclerView2.adapter = adapter
    }

    private fun onBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun setResultKey(result: ArrayList<Int>): MutableMap<Int, Int> {
        val map = mutableMapOf<Int, Int>()
        for (i in 0..<result.size) {
            for (j in 1..5) {
                if (result[i] == j) {
                    if (map[j]==null) {
                        map[j] = 1
                    } else {
                        map[j] = map[j]!! + 1
                    }
                } else {
                    if (map[j]==null) {
                        map[j] = 0
                    }
                }
            }
        }
        return map
    }
}