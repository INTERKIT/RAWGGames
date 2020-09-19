package com.truetask.games.ui.adapter.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.truetask.R
import com.truetask.games.domain.paging.PagingState
import com.truetask.games.model.Game
import com.truetask.games.model.GameCategory
import com.truetask.games.model.GamesType
import com.truetask.games.ui.adapter.games.GamesAdapter
import kotlinx.android.synthetic.main.item_list.view.emptyTextView
import kotlinx.android.synthetic.main.item_list.view.itemsRecyclerView
import kotlinx.android.synthetic.main.item_list.view.titleTextView

class ListViewHolder(
    parent: ViewGroup,
    private val category: GameCategory,
    private val onGameClicked: (Game) -> Unit,
    private val onRetryClicked: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
) {

    private val titleTextView = itemView.titleTextView
    private val itemsRecyclerView = itemView.itemsRecyclerView
    private val emptyTextView = itemView.emptyTextView

    private val gamesAdapter: GamesAdapter by lazy {
        GamesAdapter(
            category = category,
            onGameClicked = onGameClicked,
            onRetry = { onRetryClicked() }
        )
    }

    fun onBind(type: GamesType) {
        titleTextView.setText(type.title)

        with(itemsRecyclerView) {
            layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = gamesAdapter
        }

        val isEmpty = type.games.isNullOrEmpty() && type.state !is PagingState.Error
        setEmpty(isEmpty)

        update(type.state)
        update(type.games)
    }

    fun update(new: PagedList<Game>?) {
        setEmpty(new.isNullOrEmpty())
        val games = new ?: return
        gamesAdapter.submitList(games)
    }

    fun update(state: PagingState) {
        gamesAdapter.setPagingState(state)

        if (state is PagingState.Error) setEmpty(false)
    }

    private fun setEmpty(isEmpty: Boolean) {
        emptyTextView.isVisible = isEmpty
    }
}