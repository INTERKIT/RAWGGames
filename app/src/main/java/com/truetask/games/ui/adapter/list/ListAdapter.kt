package com.truetask.games.ui.adapter.list

import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.truetask.games.model.Game
import com.truetask.games.model.GameCategory
import com.truetask.games.model.GamesType
import com.truetask.games.domain.paging.PagingState

class ListAdapter(
    initial: List<GamesType>,
    private val onGameClicked: (Game) -> Unit,
    private val onRetryClicked: (GameCategory) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = initial

    fun setGames(new: Pair<GameCategory, PagedList<Game>>) {
        val (category, games) = new
        val index = data.indexOfFirst { it.isSame(category) }
        notifyItemChanged(index, games)
    }

    fun setPagingState(new: Pair<GameCategory, PagingState>) {
        val (category, state) = new
        val index = data.indexOfFirst { it.isSame(category) }
        notifyItemChanged(index, state)
    }

    override fun getItemViewType(position: Int): Int =
        data[position].getCategory().ordinal

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val category = GameCategory.values()[viewType]

        return ListViewHolder(
            parent = parent,
            category = category,
            onGameClicked = onGameClicked,
            onRetryClicked = { onRetryClicked.invoke(category) }
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ListViewHolder).onBind(data[position])
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        when {
            payloads.isEmpty() -> super.onBindViewHolder(holder, position, payloads)
            else -> onBindViewHolder(holder, payloads)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun onBindViewHolder(holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        payloads.forEach { data ->
            when (data) {
                is PagingState -> (holder as ListViewHolder).update(data)
                is PagedList<*> -> (holder as ListViewHolder).update(data as PagedList<Game>)
            }
        }
    }
}