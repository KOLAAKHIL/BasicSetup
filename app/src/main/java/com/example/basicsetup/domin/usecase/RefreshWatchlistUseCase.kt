package com.example.basicsetup.domin.usecase

import com.example.basicsetup.domin.repository.WatchlistRepository
import javax.inject.Inject

class RefreshWatchlistUseCase @Inject constructor(
    private val repository: WatchlistRepository
){
    suspend operator fun invoke(): Result<Unit> {
        return repository.refreshWatchlist()
    }
}
