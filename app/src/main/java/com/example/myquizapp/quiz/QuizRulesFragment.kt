package com.example.myquizapp.quiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.myquizapp.R
import com.example.myquizapp.databinding.FragmentQuizRulesBinding
import com.example.myquizapp.utils.CustomDialog

class QuizRulesFragment : Fragment() {

    private var _binding: FragmentQuizRulesBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuizRulesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartQuiz.setOnClickListener {
            CustomDialog().showDialog({ positive ->
                if (positive == true) {
                    it.findNavController().navigate(R.id.action_quizRulesFragment_to_quizFragment)
                } else {
                    Log.d("MainActivity", "Negative")
                }
            }, requireActivity(), "Start Quiz", "Are you sure you want to start the quiz?")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}