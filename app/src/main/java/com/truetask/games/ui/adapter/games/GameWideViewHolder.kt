package com.truetask.games.ui.adapter.games

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.truetask.R
import com.truetask.common.base.BaseViewHolder
import com.truetask.games.model.Game
import kotlinx.android.synthetic.main.item_game_large.view.backgroundImageView
import kotlinx.android.synthetic.main.item_game_large.view.titleTextView

class GameWideViewHolder(
    parent: ViewGroup,
    onGameClicked: (Game) -> Unit
) : BaseViewHolder<Game>(parent, R.layout.item_game_large, onGameClicked) {

    private val backgroundImageView = itemView.backgroundImageView
    private val titleTextView = itemView.titleTextView

    override fun onBind(item: Game) {
        super.onBind(item)

        if (item.imageUrl.isNotEmpty()) {
            Glide
                .with(backgroundImageView)
                .load(item.imageUrl)
                .into(backgroundImageView)
        }

        titleTextView.text = item.name
    }
}