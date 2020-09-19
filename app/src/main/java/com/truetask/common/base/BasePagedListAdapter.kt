package com.truetask.common.base

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.truetask.R
import com.truetask.games.domain.paging.PagingState
import com.truetask.games.ui.adapter.games.ErrorViewHolder
import com.truetask.games.ui.adapter.games.ProgressViewHolder

abstract class BasePagedListAdapter<T, VH : BaseViewHolder<*>>(
    diffUtil: DiffUtil.ItemCallback<T>,
    private val onRetry: () -> Unit
) : PagedListAdapter<T, VH>(diffUtil) {

    companion object {
        private val noAdditionalItemRequiredState = listOf(PagingState.Idle, PagingState.InitialLoading)
    }

    private var pagingState: PagingState = PagingState.Idle

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        when (viewType) {
            R.layout.item_paging_progress -> ProgressViewHolder(parent) as VH
            R.layout.item_paging_error -> ErrorViewHolder(parent, onRetry) as VH
            else -> onCreateChildViewHolder(parent, viewType)
        }

    override fun onBindViewHolder(holder: VH, position: Int) {
        when (holder) {
            is ErrorViewHolder -> holder.onBind(R.string.general_error_message)
            is ProgressViewHolder -> { /* do nothing */ }
            else -> onBindChildViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        onBindChildViewHolder(holder, position, payloads)
    }

    override fun getItemViewType(position: Int): Int = when {
        !stateRequiresExtraItem(pagingState) || position < itemCount - 1 -> getChildItemViewType(position)
        pagingState is PagingState.Loading -> R.layout.item_paging_progress
        else -> R.layout.item_paging_error
    }

    override fun getItemCount(): Int =
        super.getItemCount() + if (stateRequiresExtraItem(pagingState)) 1 else 0

    override fun getItem(position: Int): T? =
        if (position < super.getItemCount()) super.getItem(position) else null

    protected abstract fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): VH
    protected abstract fun onBindChildViewHolder(holder: VH, position: Int)

    protected open fun getChildItemViewType(position: Int): Int = 0
    protected open fun onBindChildViewHolder(holder: VH, position: Int, payloads: List<Any>) = Unit

    fun setPagingState(newState: PagingState) {
        if (pagingState == newState) return

        val shouldHasExtraItem = stateRequiresExtraItem(newState)
        val hasExtraItem = stateRequiresExtraItem(pagingState)

        pagingState = newState

        // since item count is a function - cache its value.
        val count = itemCount

        when {
            hasExtraItem && shouldHasExtraItem -> notifyItemChanged(count)
            hasExtraItem && !shouldHasExtraItem -> notifyItemRemoved(count)
            !hasExtraItem && shouldHasExtraItem -> notifyItemInserted(count)
        }
    }

    private fun stateRequiresExtraItem(state: PagingState) = state !in noAdditionalItemRequiredState
}