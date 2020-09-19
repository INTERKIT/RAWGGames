package com.truetask.games.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.truetask.common.base.BaseViewModel
import com.truetask.games.domain.paging.MultiPagingManager
import com.truetask.games.domain.paging.PagingState
import com.truetask.games.model.Game
import com.truetask.games.model.GameCategory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListViewModel(
    private val pagingManager: MultiPagingManager
) : BaseViewModel() {

    val gamesUpdateLiveData = MutableLiveData<Pair<GameCategory, PagedList<Game>>>()
    val stateUpdateLiveData = MutableLiveData<Pair<GameCategory, PagingState>>()

    init {
        pagingManager.getGamesFlowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                gamesUpdateLiveData.value = it
            }
            .disposeOnCleared()

        pagingManager.getStateFlowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                stateUpdateLiveData.value = it
            }
            .disposeOnCleared()
    }

    fun loadGames() {
        pagingManager.loadAll()
    }

    fun refresh(category: GameCategory) {
        pagingManager.refresh(category)
    }

    override fun onCleared() {
        super.onCleared()
        pagingManager.clear()
    }
}