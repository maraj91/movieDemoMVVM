package com.android.moviewdemo.base

import androidx.lifecycle.ViewModel
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


abstract class BaseDialogFragment<T : ViewDataBinding, VM : ViewModel> : DialogFragment() {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    lateinit var binding: T
    lateinit var viewModel: VM
    lateinit var mContext: Context
    abstract val dialogBinding: DialogBinding
    abstract fun setAccessibilityView(binding: T)

    abstract fun onCreateDialogFragment(savedInstanceState: Bundle?)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        AndroidSupportInjection.inject(this)
        val dialogBinding = dialogBinding
        binding = DataBindingUtil.inflate(inflater, dialogBinding.layoutResId, container, false)
        viewModel = viewModelProvider.create(dialogBinding.clazz)
        onCreateDialogFragment(savedInstanceState)
        subscribeToEvents(viewModel)
        setAccessibilityView(binding)
        return binding.root
    }

    protected abstract fun subscribeToEvents(vm: VM)

    inner class DialogBinding(
        @param:LayoutRes @field:LayoutRes
        val layoutResId: Int, val clazz: Class<VM>
    )

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}
