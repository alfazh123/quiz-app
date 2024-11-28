package com.example.myquizapp.quiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquizapp.R
import com.example.myquizapp.data.ViewModelFactory
import com.example.myquizapp.databinding.FragmentQuizBinding
import com.example.myquizapp.utils.QuizViewModel
import com.example.myquizapp.utils.Answer
import com.example.myquizapp.utils.Question
import com.example.myquizapp.utils.QuizAdapter
import java.util.Calendar

//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken

class QuizFragment : Fragment() {

    private val viewModel by viewModels<QuizViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.isEnabled = false

        setupQuestion()
    }

    private fun setupQuestion() {

        val listQuestionSize = viewModel.questions.size

        val linearLayoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.layoutManager = linearLayoutManager
        val adapter = QuizAdapter()
        binding.recyclerView.adapter = adapter

        viewModel.indexedValue.observe(viewLifecycleOwner) { index ->
            adapter.submitList(viewModel.questions[index])
            val isEnd = viewModel.isEndOfQuestion(viewModel.questions)
            setupAction(isEnd)

            binding.progressNumber.text =
                getString(R.string.index_question, (index + 1).toString(), viewModel.questions.size.toString())
        }

        adapter.setOnClickCallback(object : QuizAdapter.OnItemClickCallback {
            override fun onItemClicked(answer: Int, position: Question) {
                val answerByQuestion = Answer(
                    position.question,
                    position.number,
                    answer
                )

                viewModel.addAnswer(answerByQuestion)

                Log.d("QuizV2Activity", "Answer Size: ${viewModel.answerSize} , List Question Size: ${viewModel.questions.size}")
                Log.d("QuizV2Activity", "Questions: ${viewModel.questions}")

                if (viewModel.answerSize >= viewModel.questions[viewModel.indexedValue.value!!].size) {
                    binding.submitButton.isEnabled = true
                } else {
                    binding.submitButton.isEnabled = false
                }

                val progress = if (position.number%viewModel.questions[viewModel.indexedValue.value!!].size != 0) {
                    viewModel.answerSize%viewModel.questions[viewModel.indexedValue.value!!].size
                } else  {
                    viewModel.questions[viewModel.indexedValue.value!!].size
                }

                binding.progressIndicator.progress = ((progress) * 100) / viewModel.questions[viewModel.indexedValue.value!!].size
            }
        })
    }

    private fun setupAction(isEnd:Boolean) {
        binding.submitButton.text = if (isEnd) "Submit" else "Next"

        binding.submitButton.setOnClickListener {
            if (isEnd) {
                Log.d("QuizV2Activity", "Submit ${viewModel.answers}")
                val bundle = Bundle()
                bundle.putIntegerArrayList(ResultFragment.RESULT_VALUE, viewModel.resultAnswer())
                val fragment = ResultFragment()
                fragment.arguments = bundle
                it.findNavController().navigate(R.id.action_quizFragment_to_resultFragment, bundle)

                val calendar = Calendar.getInstance()
                val idQuiz = calendar.timeInMillis
                Log.d("QuizV2Activity", "ID Quiz: $idQuiz")
                viewModel.submitAnswer()
            } else {
                viewModel.addIndexedValue()
                binding.submitButton.isEnabled = false
                binding.progressIndicator.progress = 0
            }
        }
    }
}