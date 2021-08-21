package com.android.moviewdemo.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerViewAdapter<T: ViewDataBinding>: RecyclerView.Adapter<BaseRecyclerViewAdapter.MyViewHolder<T>>() {

    lateinit var binding: T
    abstract val bindingRecyclerView: BaseRecyclerViewAdapter
    abstract fun count(): Int
    abstract fun setAccessibilityView(binding: T)
    abstract fun onBindViewHolderMethod(binding: T, holder: MyViewHolder<T>, position: Int)

    inner class BaseRecyclerViewAdapter(
        @param:LayoutRes @field:LayoutRes
        val layoutResId: Int
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<T> {
        val inflater = LayoutInflater.from(parent.context)
        val activityBinding = bindingRecyclerView
        binding = DataBindingUtil.inflate(inflater, activityBinding.layoutResId, parent, false)
        setAccessibilityView(binding)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return count()
    }

    override fun onBindViewHolder(holder: MyViewHolder<T>, position: Int) {
        onBindViewHolderMethod(binding, holder, position)
        holder.binding.executePendingBindings()
    }

    /**
     * Simple view holder for this adapter
     *
     * @param <S>
    </S> */
    class MyViewHolder<S : ViewDataBinding>(val binding: S) : RecyclerView.ViewHolder(binding.root)

    fun View.visible() { this.visibility = View.VISIBLE }
    fun View.gone() { this.visibility = View.GONE }
    fun View.inVisible() { this.visibility = View.INVISIBLE }
}