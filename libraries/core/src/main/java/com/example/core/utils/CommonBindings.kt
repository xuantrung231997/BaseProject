package com.example.core.utils

import android.view.View
import android.widget.Button
import android.widget.ViewFlipper
import androidx.databinding.BindingAdapter
import com.example.core.network.Resource

@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("selected")
fun View.selected(value: Boolean) {
    isSelected = value
}


@BindingAdapter("setButtonEnable")
fun Button.setButtonEnable(isButtonEnable: Boolean) {
    isEnabled = isButtonEnable
    alpha = if (isButtonEnable) {
        1f
    } else {
        0.5f
    }
}

@BindingAdapter("displayChild")
fun ViewFlipper.setDisplayChild(resource: Resource<Any>?) {
    resource?.let {
        displayedChild = when (it) {
            is Resource.Success<*> -> {
                Constants.MODE_SUCCESS
            }
            is Resource.Loading -> {
                Constants.MODE_LOADING
            }
            is Resource.NoData -> {
                Constants.MODE_NO_DATA
            }
            is Resource.Error -> {
                Constants.MODE_ERROR
            }
        }
    }
}
