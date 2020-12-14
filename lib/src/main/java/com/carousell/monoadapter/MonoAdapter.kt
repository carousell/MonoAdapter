package com.carousell.monoadapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class MonoAdapter<V : ViewBinding, T>(
    private val viewProvider: (ViewGroup) -> V,
    private val binder: V.(T) -> Unit,
    diffCheck: DiffUtil.ItemCallback<T> = DefaultDiffCheck()
) : ListAdapter<T, MonoAdapter.ViewHolder<V>>(diffCheck) {

    private class DefaultDiffCheck<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return true
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }

    private class BindingWrapper(private val view: View) : ViewBinding {
        override fun getRoot() = view
    }

    class ViewHolder<V : ViewBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(viewProvider.invoke(parent))

    override fun onBindViewHolder(holder: ViewHolder<V>, position: Int) {
        binder.invoke(holder.binding, getItem(position))
    }

    companion object {

        fun <T> create(
            @LayoutRes layoutId: Int,
            binder: (View, T) -> Unit
        ): MonoAdapter<ViewBinding, T> {
            return MonoAdapter({
                BindingWrapper(LayoutInflater.from(it.context).inflate(layoutId, it, false))
            }, {
                binder.invoke(this.root, it)
            })
        }

        fun <T> create(
            @LayoutRes layoutId: Int,
            diffCheck: DiffUtil.ItemCallback<T>,
            binder: (View, T) -> Unit
        ): MonoAdapter<ViewBinding, T> {
            return MonoAdapter({
                BindingWrapper(LayoutInflater.from(it.context).inflate(layoutId, it, false))
            }, {
                binder.invoke(this.root, it)
            }, diffCheck)
        }

        inline fun <reified V : ViewBinding, T> create(noinline binder: V.(T) -> Unit): MonoAdapter<V, T> {
            return MonoAdapter({
                val method =
                    V::class.java.getMethod(
                        "inflate",
                        LayoutInflater::class.java,
                        ViewGroup::class.java,
                        Boolean::class.java
                    )
                method.invoke(null, LayoutInflater.from(it.context), it, false) as V
            }, binder)
        }

        inline fun <reified V : ViewBinding, T> create(
            diffCheck: DiffUtil.ItemCallback<T>,
            noinline binder: V.(T) -> Unit
        ): MonoAdapter<V, T> {
            return MonoAdapter({
                val method =
                    V::class.java.getMethod(
                        "inflate",
                        LayoutInflater::class.java,
                        ViewGroup::class.java,
                        Boolean::class.java
                    )
                method.invoke(null, LayoutInflater.from(it.context), it, false) as V
            }, binder, diffCheck)
        }
    }
}