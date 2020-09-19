package com.truetask.games.ui.adapter.games

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.truetask.R
import com.truetask.common.base.BasePagedListAdapter
import com.truetask.common.base.BaseViewHolder
import com.truetask.games.model.Game
import com.truetask.games.model.GameCategory

class GamesAdapter(
    private val category: GameCategory,
    private val onGameClicked: (Game) -> Unit,
    onRetry: () -> Unit
) : BasePagedListAdapter<Game, BaseViewHolder<Game>>(diffUtil, onRetry) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean =
                oldItem.gameId == newItem.gameId

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean =
                oldItem == newItem
        }
    }

    override fun getChildItemViewType(position: Int): Int = when (category) {
        GameCategory.PUBLISHER -> R.layout.item_game
        else -> R.layout.item_game_large
    }

    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Game> =
        when (viewType) {
            R.layout.item_game -> GameViewHolder(parent, onGameClicked)
            else -> GameWideViewHolder(parent, onGameClicked)
        }

    override fun onBindChildViewHolder(holder: BaseViewHolder<Game>, position: Int) {
        val game = getItem(position) ?: return
        holder.onBind(game)
    }
}