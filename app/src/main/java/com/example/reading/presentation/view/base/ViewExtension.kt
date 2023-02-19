package com.example.reading.presentation.view.base

import android.view.View

fun View.isVisibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}