package com.example.basicsetup.domin.usecase

import com.example.basicsetup.domin.model.Stock
import com.example.basicsetup.domin.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWatchlistUseCase @Inject constructor(
    private val repository: WatchlistRepository
){
    operator fun invoke(): Flow<List<Stock>>{
        return repository.observeWatchlist()
    }
}
