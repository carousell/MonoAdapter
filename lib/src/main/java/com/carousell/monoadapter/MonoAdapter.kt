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

    var viewRecycledCallback: ((ViewHolder<V>) -> Unit)? = null
    var viewAttachedToWindowCallback: ((ViewHolder<V>) -> Unit)? = null
    var viewDetachedFromWindowCallback: ((ViewHolder<V>) -> Unit)? = null
    var attachedToRecyclerViewCallback: ((RecyclerView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(viewProvider.invoke(parent))

    override fun onBindViewHolder(holder: ViewHolder<V>, position: Int) {
        binder.invoke(holder.binding, getItem(position))
    }

    override fun onViewRecycled(holder: ViewHolder<V>) {
        super.onViewRecycled(holder)
        viewRecycledCallback?.invoke(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        attachedToRecyclerViewCallback?.invoke(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder<V>) {
        super.onViewAttachedToWindow(holder)
        viewAttachedToWindowCallback?.invoke(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder<V>) {
        super.onViewDetachedFromWindow(holder)
        viewDetachedFromWindowCallback?.invoke(holder)
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

        fun <V : ViewBinding, T> create(
            bindingProvider: (LayoutInflater, ViewGroup?, Boolean) -> V,
            binder: V.(T) -> Unit
        ): MonoAdapter<V, T> {
            return MonoAdapter({
                bindingProvider.invoke(LayoutInflater.from(it.context), it, false)
            }, binder)
        }

        fun <V : ViewBinding, T> create(
            bindingProvider: (LayoutInflater, ViewGroup?, Boolean) -> V,
            diffCheck: DiffUtil.ItemCallback<T>,
            binder: V.(T) -> Unit
        ): MonoAdapter<V, T> {
            return MonoAdapter({
                bindingProvider.invoke(LayoutInflater.from(it.context), it, false)
            }, binder, diffCheck)
        }

    }
}