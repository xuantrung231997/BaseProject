package com.example.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

/**
 * Use for tab screen, webView, google map...
 */
abstract class SaveViewBaseFragment<BD : ViewDataBinding, VM : BaseViewModel>(@LayoutRes id: Int) :
    BaseFragment<BD, VM>(id) {

    private var mContainerView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mContainerView == null) {
            mContainerView = super.onCreateView(inflater, container, savedInstanceState)
        } else {
            val parent = mContainerView?.parent
            if (parent != null) {
                (parent as? ViewGroup)?.removeView(mContainerView)
            }
        }
        return mContainerView
    }

    override fun onDestroy() {
        mContainerView = null
        super.onDestroy()
    }

}