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
) : ListAdapter<T, MonoAdapter.ViewHolder<V>>(diffCheck) {

    class ViewHolder<V : ViewBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(viewProvider.invoke(parent))

    override fun onBindViewHolder(holder: ViewHolder<V>, position: Int) {
        binder.invoke(holder.binding, getItem(position))
    }
}