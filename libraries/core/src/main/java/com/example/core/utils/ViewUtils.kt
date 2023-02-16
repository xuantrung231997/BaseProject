package com.example.core.utils

import android.app.Activity
import android.content.Context
import android.os.SystemClock
import android.text.Editable
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.AnimRes
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.core.R
import com.example.core.utils.Constants.DURATION_TIME_CLICKABLE
import com.example.core.utils.DeviceUtil.Companion.getStatusBarHeight
import com.example.core.utils.ViewUtils.lastClick
import timber.log.Timber

object ViewUtils {
    //check double click
    @kotlin.jvm.JvmStatic
    fun runLayoutAnimation(recyclerView: RecyclerView, @AnimRes resId: Int) {
        val context = recyclerView.context
        val controller =
            AnimationUtils.loadLayoutAnimation(context, resId)
        recyclerView.layoutAnimation = controller
        recyclerView.scheduleLayoutAnimation()
    }

    var lastClick = 0L
}

fun String.toast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun TextView.disableCopyPaste() {
    isLongClickable = false
    setTextIsSelectable(false)
    customSelectionActionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu): Boolean {
            return false
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {}
    }
}

fun ImageView.enableView(isEnable: Boolean) {
    isEnabled = if (isEnable) {
        setColorFilter(context.getColorCompat(R.color.color_button_common_blue))
        true
    } else {
        setColorFilter(context.getColorCompat(R.color.background_color_gray))
        false
    }
}

fun ImageView.tint(@ColorRes colorId: Int) {
    setColorFilter(context.getColorCompat(colorId))
}

fun EditText.onTextChange(content: (Editable?) -> Unit) {
    addTextChangedListener(object : android.text.TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            //do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //do nothing
        }

        override fun afterTextChanged(s: Editable?) {
            content(s)
        }
    })
}

fun ViewPager.onPageSelected(params: (Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            //do nothing
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            //do nothing
        }

        override fun onPageSelected(position: Int) {
            params(position)
        }

    })
}

fun View.setOnClickAction(listener: View.OnClickListener) {

}

/*fun TextView.setTextAsync(data: String) {
    TextViewCompat.setPrecomputedText(
        this,
        PrecomputedTextCompat.create(data, TextViewCompat.getTextMetricsParams(this))
    )
}*/

fun Activity.toastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastMessage(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun View.setOnSafeClickListener(duration: Long = DURATION_TIME_CLICKABLE, onClick: () -> Unit) {
    setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastClick >= duration) {
            onClick()
            lastClick = SystemClock.elapsedRealtime()
        }
    }
}

/**
 * Set a layout below status bar
 */
fun View.setLayoutBelowStatusBar() {
    if (this.layoutParams is MarginLayoutParams) {
        val layoutParams = this.layoutParams as MarginLayoutParams
        layoutParams.setMargins(0, getStatusBarHeight(this.context), 0, 0)
        this.requestLayout()
    }
}

fun RecyclerView.initLoadMore(
    loadMoreThreshold: Int = Constants.LoadMoreThreshold.DEFAULT,
    onRequestToLoadMoreData: (() -> Unit)? = null
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy <= 0) return

            adapter?.let {
                if (!(loadMoreThreshold >= 1 && loadMoreThreshold <= it.itemCount - 1)) return
                try {
                    if ((layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() >= it.itemCount - loadMoreThreshold) {
                        onRequestToLoadMoreData?.invoke()
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    })
}

