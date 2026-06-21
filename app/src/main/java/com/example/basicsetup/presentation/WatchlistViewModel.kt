package com.example.basicsetup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.basicsetup.domin.usecase.GetWatchlistUseCase
import com.example.basicsetup.domin.usecase.RefreshWatchlistUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val getWatchlistUseCase: GetWatchlistUseCase,
    private val refreshWatchlistUseCase: RefreshWatchlistUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WatchlistUiState())
    val uiState: StateFlow<WatchlistUiState> = _uiState.asStateFlow()

    init {
        observeCachedStocks()
        refreshWatchlist()
    }

    private fun observeCachedStocks() {
        viewModelScope.launch {
            getWatchlistUseCase().collect { stocks ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        stocks = stocks
                    )
                }
            }
        }
    }

    fun refreshWatchlist() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isRefreshing = true,
                    errorMessage = null
                )
            }

            val result = refreshWatchlistUseCase()

            _uiState.update {
                it.copy(
                    isRefreshing = false,
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
        }
    }
}
