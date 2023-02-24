package com.example.reading.presentation.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.reading.domain.model.Story

interface Interactor {
    fun findNavController(): NavController
    fun isFromSearch(): Boolean
    fun getStory(): Story?
    fun getViewModel(): ViewModel
    fun getFragment(): Fragment
}