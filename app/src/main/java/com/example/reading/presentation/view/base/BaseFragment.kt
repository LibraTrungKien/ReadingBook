package com.example.reading.presentation.view.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment(), BaseView {
    private lateinit var _binding: T
    protected val binding: T
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = createViewBinding()
        return binding.root
    }

    abstract fun createViewBinding(): T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeView()
        initializeComponent()
        initializeEvents()
        initializeData()
        bindView()
    }

    override fun initializeView() {
    }

    override fun initializeComponent() {
    }

    override fun initializeEvents() {
    }

    override fun initializeData() {
    }

    override fun bindView() {
    }

    fun openGoogleSearch(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(Intent.createChooser(intent, "Mở với"))
    }

}