package com.carousell.monoadapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

private fun <T> defaultDiffCheck() = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return true
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}

class MonoAdapter<V : ViewBinding, T>(
    private val viewProvider: (ViewGroup) -> V,
    private val binder: (V, T) -> Unit,
    diffCheck: DiffUtil.ItemCallback<T> = defaultDiffCheck()
) : ListAdapter<T, MonoAdapter.ViewHolder<V>>(diffCheck) {

    companion object {
        inline fun <reified V : ViewBinding, T> create(noinline binder: (V, T) -> Unit): MonoAdapter<V, T> {
            return MonoAdapter({
                val layoutInflater = LayoutInflater.from(it.context)
                val method =
                    V::class.java.getMethod(
                        "inflate",
                        LayoutInflater::class.java,
                        ViewGroup::class.java,
                        Boolean::class.java
                    )
                method.invoke(null, layoutInflater, it, false) as V
            }, binder)
        }
    }

    class ViewHolder<V : ViewBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(viewProvider.invoke(parent))

    override fun onBindViewHolder(holder: ViewHolder<V>, position: Int) {
        binder.invoke(holder.binding, getItem(position))
    }
}