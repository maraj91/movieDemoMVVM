package com.android.moviewdemo.base

import android.graphics.Rect
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseKeyBoardEventActivity<T : ViewDataBinding, V : ViewModel> :
    BaseActivity<T, V>() {

    abstract fun onKeyPadOpen(height: Int)
    abstract fun onKeyPadHide()

    private var isKeypadOpen = false

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //keyboard event
        binding.root.viewTreeObserver
            .addOnGlobalLayoutListener {
                // navigation bar height
                var navigationBarHeight = 0
                var resourceId =
                    resources.getIdentifier("navigation_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    navigationBarHeight = resources.getDimensionPixelSize(resourceId)
                }
                // status bar height
                var statusBarHeight = 0
                resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    statusBarHeight = resources.getDimensionPixelSize(resourceId)
                }
                // display window size for the app layout
                val rect = Rect()
                window.decorView.getWindowVisibleDisplayFrame(rect)
                // screen height - (user app height + status + nav) ..... if non-zero, then there is a soft keyboard
                val keyboardHeight: Int = binding.root.rootView
                    .height - (statusBarHeight + navigationBarHeight + rect.height())

                if (keyboardHeight <= 0) {
                    onKeyPadHide()
                    isKeypadOpen = false
                } else {
                    isKeypadOpen = true
                    onKeyPadOpen(keyboardHeight)
                }
            }
    }

    fun isKeyboardOpen():Boolean{
        return isKeypadOpen
    }
}