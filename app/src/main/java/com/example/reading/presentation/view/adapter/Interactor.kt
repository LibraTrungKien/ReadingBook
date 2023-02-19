package com.example.reading.presentation.view.adapter

import androidx.navigation.NavController
import com.example.reading.domain.model.Story

interface Interactor {
    fun findNavController(): NavController
    fun getStory(): Story?
}