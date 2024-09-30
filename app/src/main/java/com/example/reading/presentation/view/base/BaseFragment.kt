package com.example.reading.presentation.view.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.reading.presentation.view.diglog.MessageDialog

abstract class BaseFragment<T : ViewBinding> : Fragment(), BaseView {
    private lateinit var _binding: T
    protected val binding: T
        get() = _binding

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it ?: return@registerForActivityResult
            getContentCallback(it)
        }
    var getContentCallback: (uri: Uri) -> Unit = {}

    val requestPermissionLaunch =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            permissionCallback(it)
        }
    var permissionCallback: (isGranted: Boolean) -> Unit = {}

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
        Log.d(javaClass.name, "onViewCreated()...")
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

    fun showMessageDialog(title: String, content: String, image: Int, onButtonClicked: () -> Unit) {
        MessageDialog.show(parentFragmentManager, title, content, image, onButtonClicked)
    }

    fun openGallery() {
        val input = "image/*"
        getContent.launch(input)
    }

}