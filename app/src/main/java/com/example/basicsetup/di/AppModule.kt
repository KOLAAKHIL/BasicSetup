package com.example.basicsetup.di

import android.content.Context
import androidx.room.Room
import com.example.basicsetup.data.local.StockDao
import com.example.basicsetup.data.local.WatchlistDatabase
import com.example.basicsetup.data.remote.StockApi
import com.example.basicsetup.data.repository.WatchlistRepositoryImpl
import com.example.basicsetup.domin.repository.WatchlistRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideStockApi(): StockApi {
        return Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(StockApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): WatchlistDatabase {
        return Room.databaseBuilder(
            context,
            WatchlistDatabase::class.java,
            "watchlist_db"
        ).build()
    }

    @Provides
    fun provideStockDao(
        database: WatchlistDatabase
    ): StockDao {
        return database.stockDao()
    }

    @Provides
    @Singleton
    fun provideWatchlistRepository(
        api: StockApi,
        dao: StockDao
    ): WatchlistRepository {
        return WatchlistRepositoryImpl(api, dao)
    }
}
