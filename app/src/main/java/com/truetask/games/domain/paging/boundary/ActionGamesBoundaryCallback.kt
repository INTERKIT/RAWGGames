package com.truetask.games.domain.paging.boundary

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toFlowable
import com.truetask.games.domain.GamesInteractor
import com.truetask.games.domain.paging.BaseGamesBoundaryCallback
import com.truetask.games.domain.paging.PagingState
import com.truetask.games.domain.paging.boundary.BoundaryCallbackContract.Companion.DEBOUNCE_TIMEOUT_MS
import com.truetask.games.domain.paging.boundary.BoundaryCallbackContract.Companion.PAGE_SIZE
import com.truetask.games.model.Game
import com.truetask.games.model.GameCategory
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Each boundary callback should have a possibility to easily change config for paged list.
 * Therefore we are not moving `boilerplate` code into base class here
 * */
class ActionGamesBoundaryCallback(
    private val gamesInteractor: GamesInteractor
) : BaseGamesBoundaryCallback<Game>(), BoundaryCallbackContract {

    private var onGamesUpdate: ((GameCategory, PagedList<Game>) -> Unit)? = null
    private var onStateUpdate: ((GameCategory, PagingState) -> Unit)? = null

    private var dataSource: DataSource<*, Game>? = null
    private var disposable: Disposable? = null

    override val category: GameCategory = GameCategory.GENRE

    override fun onItemsLoaded(page: Int, pageSize: Int): Single<Boolean> =
        gamesInteractor.loadGames(
            page = page,
            size = pageSize,
            category = category
        )

    override fun onGamesUpdated(callback: (GameCategory, PagedList<Game>) -> Unit) {
        onGamesUpdate = callback
    }

    override fun onStateUpdated(callback: (GameCategory, PagingState) -> Unit) {
        onStateUpdate = callback
    }

    override fun load() {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        val pagedOperations = gamesInteractor
            .getByCategory(category)
            .toFlowable(config, boundaryCallback = this)

        Flowables
            .combineLatest(state, pagedOperations)
            .debounce { (state, list) -> getPagingStateDebounce(state, list) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { (state, games) ->
                    dataSource = games.dataSource

                    onGamesUpdate?.invoke(category, games)
                    onStateUpdate?.invoke(category, state)
                },
                onError = { Timber.wtf(it, "Unexpected error while observing paged list.") }
            )
            .also { disposable = it }
    }

    override fun refresh() {
        dataSource?.invalidate()
    }

    override fun clear() {
        super.clear()
        disposable?.dispose()
    }

    private fun getPagingStateDebounce(state: PagingState, list: PagedList<Game>): Flowable<Long> =
        if (state is PagingState.Idle && list.isEmpty() || state is PagingState.Error) {
            Flowable.timer(DEBOUNCE_TIMEOUT_MS, TimeUnit.MILLISECONDS)
        } else {
            Flowable.empty()
        }
}