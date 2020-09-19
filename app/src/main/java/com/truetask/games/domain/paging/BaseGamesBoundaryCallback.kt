package com.truetask.games.domain.paging

import androidx.paging.PagedList
import com.truetask.games.domain.paging.boundary.BoundaryCallbackContract.Companion.PAGE_SIZE
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseGamesBoundaryCallback<T> : PagedList.BoundaryCallback<T>(), Clearable {

    protected val state = BehaviorProcessor.create<PagingState>()

    private val isInitialInProgress = AtomicBoolean()
    private var hasNext: Boolean = false
    private var page: Int = 1

    private var disposable: Disposable? = null

    init {
        state.offer(PagingState.Idle)
    }

    abstract fun onItemsLoaded(page: Int, pageSize: Int): Single<Boolean>

    override fun onZeroItemsLoaded() {
        Timber.d("Starting initialLoading")

        if (!isInitialInProgress.compareAndSet(false, true)) {
            Timber.d("Initial loading is already in progress.")
            return
        }

        onItemsLoaded(page, PAGE_SIZE)
            .doOnSubscribe { state.offer(PagingState.InitialLoading) }
            .doFinally { isInitialInProgress.compareAndSet(true, false) }
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { hasNext ->
                    this.hasNext = hasNext
                    state.offer(PagingState.Idle)
                    Timber.d("Initial loading is completed")
                },
                onError = {
                    Timber.e(it, "Error loading initial data")
                    state.offer(PagingState.Error(it))
                }
            )
            .also { disposable = it }
    }

    override fun onItemAtEndLoaded(itemAtEnd: T) {
        Timber.d("Starting page $itemAtEnd loading")

        if (!hasNext) {
            Timber.w("No data for next page. Canceling load")
            state.offer(PagingState.Idle)
            return
        }

        onItemsLoaded(page++, PAGE_SIZE)
            .doOnSubscribe { state.offer(PagingState.Loading) }
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { hasNext ->
                    this.hasNext = hasNext
                    state.offer(PagingState.Idle)
                    Timber.d("Loading for page $page is completed")
                },
                onError = {
                    Timber.e(it, "Error loading data for page $page")
                    state.offer(PagingState.Error(it))
                }
            )
            .also { disposable = it }
    }

    override fun clear() {
        disposable?.dispose()
    }
}