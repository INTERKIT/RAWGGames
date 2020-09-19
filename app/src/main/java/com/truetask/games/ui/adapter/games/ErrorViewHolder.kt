package com.truetask.games.ui.adapter.games

import android.view.ViewGroup
import androidx.annotation.StringRes
import com.truetask.R
import com.truetask.common.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_paging_error.view.errorTextView
import kotlinx.android.synthetic.main.item_paging_error.view.retryButton

class ErrorViewHolder(
    parent: ViewGroup,
    private val onRetry: () -> Unit
) : BaseViewHolder<Nothing>(parent, R.layout.item_paging_error) {

    private val errorTextView = itemView.errorTextView
    private val retryButton = itemView.retryButton

    fun onBind(errorText: String) {
        errorTextView.text = errorText
        retryButton.setOnClickListener { onRetry() }
    }

    fun onBind(@StringRes errorTextRes: Int) {
        errorTextView.setText(errorTextRes)
        retryButton.setOnClickListener { onRetry() }
    }
}