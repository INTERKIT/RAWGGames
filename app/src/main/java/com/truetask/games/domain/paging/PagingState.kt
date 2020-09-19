package com.truetask.games.domain.paging

sealed class PagingState {
    object Idle : PagingState()
    object InitialLoading : PagingState()
    object Loading : PagingState()
    class Error(val throwable: Throwable) : PagingState()
}