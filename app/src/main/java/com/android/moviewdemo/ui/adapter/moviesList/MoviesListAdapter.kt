package com.android.moviewdemo.ui.adapter.moviesList

import com.android.moviewdemo.R
import com.android.moviewdemo.base.BaseRecyclerViewAdapter
import com.android.moviewdemo.data.models.movies_list.MoviesData
import com.android.moviewdemo.databinding.ItemMovieListBinding
import timber.log.Timber

class MoviesListAdapter : BaseRecyclerViewAdapter<ItemMovieListBinding>() {

    lateinit var moviesList:ArrayList<MoviesData>
    lateinit var callback: MoviesCallback

    fun setData(data:ArrayList<MoviesData>){
        this.moviesList = data
        notifyDataSetChanged()
    }

    fun setListener(callback: MoviesCallback){
        this.callback = callback
    }

    override val bindingRecyclerView: BaseRecyclerViewAdapter
        get() = BaseRecyclerViewAdapter(R.layout.item_movie_list)

    override fun count(): Int {
        return moviesList.size
    }

    override fun setAccessibilityView(binding: ItemMovieListBinding) {

    }

    override fun onBindViewHolderMethod(binding: ItemMovieListBinding, holder: MyViewHolder<ItemMovieListBinding>, position: Int) {
        holder.binding.data = moviesList[position]
        holder.binding.callBack = callback
    }
}