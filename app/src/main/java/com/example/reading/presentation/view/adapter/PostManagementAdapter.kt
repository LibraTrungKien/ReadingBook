package com.example.reading.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reading.databinding.ItemPostBinding
import com.example.reading.domain.model.Story

class PostManagementAdapter : RecyclerView.Adapter<PostManagementAdapter.ViewHolder>() {

    var onApproveClicked: (Story) -> Unit = {}
    var onDeclineClicked: (Story) -> Unit = {}
    var onItemClicked: (Story) -> Unit = {}

    var data = listOf<Story>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemPostBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView()
    }

    inner class ViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val item = data[absoluteAdapterPosition]
                onItemClicked(item)
            }

            binding.btnRefuse.setOnClickListener {
                val item = data[absoluteAdapterPosition]
                onDeclineClicked(item)
            }

            binding.btnApprove.setOnClickListener {
                val item = data[absoluteAdapterPosition]
                onApproveClicked(item)
            }
        }

        fun bindView() {
            val item = data[absoluteAdapterPosition]
            binding.txtUserName.text = item.author_id.toString()
            binding.txtStoryName.text = item.name + " - Chap " + item.chapters.last().index
            Glide.with(binding.root.context).load(item.image).into(binding.imgUser)
        }
    }

}