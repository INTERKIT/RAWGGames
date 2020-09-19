package com.truetask.games.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.truetask.R
import com.truetask.details.GameDetailsFragment
import com.truetask.games.model.GamesType
import com.truetask.games.ui.adapter.list.ListAdapter
import com.truetask.utils.add
import kotlinx.android.synthetic.main.fragment_list.listRecyclerView
import org.koin.android.ext.android.inject

class ListFragment : Fragment(R.layout.fragment_list) {

    companion object {
        fun create() = ListFragment()
    }

    private val listViewModel: ListViewModel by inject()

    private lateinit var listAdapter: ListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val initial = listOf(
            GamesType.Developer(R.string.games_valve),
            GamesType.Publisher(R.string.games_electronic_arts),
            GamesType.Genre(R.string.games_action)
        )
        listAdapter = ListAdapter(
            initial = initial,
            onGameClicked = { add(GameDetailsFragment.create(it)) },
            onRetryClicked = { listViewModel.refresh(it) }
        )

        with(listRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }

        with(listViewModel) {
            gamesUpdateLiveData.observe(viewLifecycleOwner) { gamesUpdate ->
                listAdapter.setGames(gamesUpdate)
            }

            stateUpdateLiveData.observe(viewLifecycleOwner) { stateUpdate ->
                listAdapter.setPagingState(stateUpdate)
            }

            loadGames()
        }
    }
}