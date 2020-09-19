package com.truetask.games

import com.truetask.games.api.GamesApi
import com.truetask.games.domain.GamesInteractor
import com.truetask.games.domain.paging.BoundaryCallbackFactory
import com.truetask.games.domain.paging.MultiPagingManager
import com.truetask.games.repository.GamesRepository
import com.truetask.games.repository.GamesRepositoryImpl
import com.truetask.games.ui.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

object GamesModule {

    fun create() = module {
        single { BoundaryCallbackFactory(get()) }
        single { MultiPagingManager(get()) }
        single {
            val gamesApi = get<Retrofit>().create(GamesApi::class.java)
            GamesRepositoryImpl(gamesApi, get())
        } bind GamesRepository::class

        factory { GamesInteractor(get()) }
        viewModel { ListViewModel(get()) }
    }
}