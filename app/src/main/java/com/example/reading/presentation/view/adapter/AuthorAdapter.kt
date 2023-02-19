package com.example.reading.presentation.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.reading.databinding.ItemImageArthorBinding
import javax.inject.Inject

class AuthorAdapter @Inject constructor() :
    Adapter<AuthorAdapter.ViewHolder>() {

    private var images: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemImageArthorBinding.inflate(inflate, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        holder.bindView(image)
    }

    fun submitList(images: List<String>){
        this.images = images
    }

    class ViewHolder(private val binding: ItemImageArthorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("CheckResult")
        fun bindView(image: String) {
            Glide.with(binding.crvImage)
                .load(image)
        }
    }
}