package com.truetask.common.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(
    parent: ViewGroup,
    @LayoutRes layoutId: Int,
    private val onItemClicked: ((T) -> Unit)? = null
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
) {

    open fun onBind(item: T) {
        itemView.setOnClickListener {
            onItemClicked?.invoke(item)
            onItemClick(item)
        }
    }

    protected open fun onItemClick(item: T) = Unit
}