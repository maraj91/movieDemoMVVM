package com.android.moviewdemo.base

import android.app.Activity
import androidx.lifecycle.ViewModel
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.annotation.LayoutRes
import android.view.*
import androidx.lifecycle.ViewModelProvider
import com.android.moviewdemo.ui.dialogs.MyProgressDialog
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


abstract class BaseFragment<T : ViewDataBinding, VM : ViewModel> : androidx.fragment.app.Fragment() {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    lateinit var binding: T
    lateinit var viewModel: VM
    var mContext: Context? = null
    var mActivity: Activity? = null
    lateinit var progressDialog: MyProgressDialog
    abstract val fragmentBinding: FragmentBinding

    abstract fun onCreateFragment(savedInstanceState: Bundle?)

    abstract fun setAccessibilityView(binding: T)

    protected abstract fun subscribeToEvents(vm: VM)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = context as Activity?
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        AndroidSupportInjection.inject(this)
        val fragmentBinding = fragmentBinding
        binding = DataBindingUtil.inflate(inflater, fragmentBinding.layoutResId, container, false)
        viewModel = viewModelProvider.create(fragmentBinding.clazz)
        progressDialog = MyProgressDialog(context)
        onCreateFragment(savedInstanceState)
        subscribeToEvents(viewModel)
        setAccessibilityView(binding)
        return binding.root
    }

    inner class FragmentBinding(
        @param:LayoutRes @field:LayoutRes
        val layoutResId: Int, val clazz: Class<VM>
    )

    fun showProgress() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
        progressDialog.show()
    }

    fun dismissProgress() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}