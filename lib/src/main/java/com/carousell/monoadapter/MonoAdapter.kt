package com.carousell.monoadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

private fun <T> replaceDiffCheck() = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return false
    }
}

class MonoAdapter<V : ViewBinding, T>(
    private val viewProvider: (ViewGroup) -> V,
    private val binder: (V, T) -> Unit,
    diffCheck: DiffUtil.ItemCallback<T> = replaceDiffCheck()
) : ListAdapter<T, MonoAdapter.ViewHolder<V, T>>(diffCheck) {

    class ViewHolder<V : ViewBinding, T>(
        private val viewBinding: V,
        private val binder: (V, T) -> Unit
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(t: T) {
            binder.invoke(viewBinding, t)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<V, T> {
        return ViewHolder(viewProvider.invoke(parent), binder)
    }

    override fun onBindViewHolder(holder: ViewHolder<V, T>, position: Int) {
        holder.bind(getItem(position))
    }
}