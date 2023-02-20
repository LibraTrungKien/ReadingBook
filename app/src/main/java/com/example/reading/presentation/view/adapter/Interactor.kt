package com.example.reading.presentation.view.adapter

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.reading.domain.model.Story

interface Interactor {
    fun findNavController(): NavController
    fun isFromSearch(): Boolean
    fun getStory(): Story?
    fun getViewModel(): ViewModel
}