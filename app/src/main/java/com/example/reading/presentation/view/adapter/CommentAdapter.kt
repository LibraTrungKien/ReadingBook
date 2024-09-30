package com.example.reading.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reading.databinding.ItemCommentBinding
import com.example.reading.domain.model.Comment
import com.example.reading.presentation.Utils

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var data = listOf<Comment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCommentBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView()
    }

    inner class ViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView() {
            val item = data[absoluteAdapterPosition]
            binding.txtUserName.text = item.username + " • " + Utils.convertToTime(item.time)
            binding.txtContent.text = item.content
        }

    }

}