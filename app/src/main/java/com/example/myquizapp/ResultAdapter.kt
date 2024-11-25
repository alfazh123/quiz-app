package com.example.myquizapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myquizapp.databinding.LinearIndicatorBinding

class ResultAdapter(private val listResult: MutableMap<Int, Int>): RecyclerView.Adapter<ResultAdapter.ListViewHolder>() {
    class ListViewHolder(binding: LinearIndicatorBinding): RecyclerView.ViewHolder(binding.root) {
        val indicator = binding.linearProgressIndicator
        private val value = binding.valueText

        fun bind(value: Int, key: Int, length: Int) {
            this.value.text = key.toString()
            this.indicator.progress = (value * 100) / length
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: LinearIndicatorBinding = LinearIndicatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    private fun getLengthResult(): Int {
        var length = 0
        for (i in listResult.values) {
            length += i
        }
        return length
    }

    override fun getItemCount(): Int {
        return listResult.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val key = listResult.keys.elementAt(position)
        val value = listResult.values.elementAt(position)
        val length = getLengthResult()
        holder.bind(value, key, length)
    }
}