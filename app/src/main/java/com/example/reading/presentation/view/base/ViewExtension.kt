package com.example.reading.presentation.view.base

import android.view.View

fun View.isVisibleOrGone(isVisible: Boolean) {
    if (isVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}