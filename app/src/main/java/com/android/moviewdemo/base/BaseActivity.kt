package com.android.moviewdemo.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.moviewdemo.ui.dialogs.MyProgressDialog
import com.android.moviewdemo.utils.network.NetworkAvailable
import com.android.moviewdemo.utils.network.NetworkMonitorUtil
import dagger.android.AndroidInjection
import javax.inject.Inject


abstract class BaseActivity<T : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    private lateinit var progressDialog: MyProgressDialog
    lateinit var binding: T
    private lateinit var viewModel: VM
    abstract val bindingActivity: ActivityBinding
    abstract val toolbar: Toolbar?
    private lateinit var networkMonitor: NetworkMonitorUtil
    var networkAvailable: MutableLiveData<NetworkAvailable> = MutableLiveData()

    abstract fun setAccessibilityView(binding: T)

    abstract fun onCreateActivity(savedInstanceState: Bundle?)

    protected abstract fun subscribeToEvents(vm: VM)

    fun getViewModel() = viewModel

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        networkMonitor = NetworkMonitorUtil(this)
        val activityBinding = bindingActivity
        binding = DataBindingUtil.setContentView(this, activityBinding.layoutResId)
        viewModel = viewModelProvider.create(activityBinding.clazz)
        progressDialog = MyProgressDialog(this)
        onCreateActivity(savedInstanceState)
        setAccessibilityView(binding)
        subscribeToEvents(viewModel)
        //setup toolbar
        toolbar?.let {toolbar->
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setSupportActionBar(toolbar)
        }
        //network callback
        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                networkAvailable.value = type?.let {type->
                    NetworkAvailable(isAvailable, type)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

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

    inner class ActivityBinding(
        @param:LayoutRes @field:LayoutRes
        val layoutResId: Int, val clazz: Class<VM>
    )

    fun setItemsVisibility(menu: Menu, exception: MenuItem, visible: Boolean?) {
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            if (item !== exception) {
                item.isVisible = visible!!
            }
        }
    }
}