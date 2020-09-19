package com.truetask.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.bumptech.glide.Glide
import com.truetask.R
import com.truetask.games.model.Game
import com.truetask.utils.popBackStack
import kotlinx.android.synthetic.main.fragment_details.gameImageView
import kotlinx.android.synthetic.main.fragment_details.gameTitleTextView
import kotlinx.android.synthetic.main.fragment_details.navigateBack
import kotlinx.android.synthetic.main.fragment_details.ratingBar
import kotlinx.android.synthetic.main.fragment_details.ratingTextView
import kotlinx.android.synthetic.main.fragment_details.ratingsCountTextView
import kotlinx.android.synthetic.main.fragment_details.releasedDate
import kotlinx.android.synthetic.main.fragment_details.screenshotsRecyclerView

class GameDetailsFragment : Fragment(R.layout.fragment_details) {

    companion object {
        private const val EXTRA_GAME = "EXTRA_GAME"
        fun create(game: Game): GameDetailsFragment {
            val fragment = GameDetailsFragment()

            val bundle = Bundle()
            bundle.putParcelable(EXTRA_GAME, game)

            fragment.arguments = bundle
            return fragment
        }
    }

    private val game: Game by lazy { requireArguments().getParcelable<Game>(EXTRA_GAME) as Game }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigateBack.setOnClickListener { popBackStack() }

        if (game.imageUrl.isNotEmpty()) {
            Glide
                .with(gameImageView)
                .load(game.imageUrl)
                .into(gameImageView)
        }

        gameTitleTextView.text = game.name
        releasedDate.text = getString(R.string.games_released_format, game.releaseDate)
        ratingTextView.text = game.rating.toString()
        ratingsCountTextView.text = getString(R.string.games_ratings_format, game.ratingsCount)
        ratingBar.rating = game.rating

        with(screenshotsRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            adapter = ScreenshotsAdapter(game.screenshots)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }
    }
}