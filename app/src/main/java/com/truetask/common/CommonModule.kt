package com.truetask.common

import android.content.Context
import androidx.room.Room
import com.truetask.BuildConfig
import com.truetask.R
import com.truetask.common.db.RawDatabase
import com.truetask.common.db.RawDatabase.Companion.DATABASE_NAME
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

object CommonModule {

    fun create() = module {
        single {
            val interceptor = HttpLoggingInterceptor()

            if (BuildConfig.DEBUG) {
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            }

            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }
        single {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(get<Context>().getString(R.string.base_url))
                .client(get())
                .build()
        }

        single {
            Room
                .databaseBuilder(get(), RawDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .setQueryExecutor(Executors.newCachedThreadPool())
                .build()
        }

        single { get<RawDatabase>().gamesDao() }
    }
}