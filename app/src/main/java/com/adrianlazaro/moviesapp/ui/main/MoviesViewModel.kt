package com.adrianlazaro.moviesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adrianlazaro.data.repository.MoviesRepository
import com.adrianlazaro.domain.Movie
import com.adrianlazaro.moviesapp.data.database.RoomDataSource
import com.adrianlazaro.moviesapp.ui.BaseViewModel
import com.adrianlazaro.usecases.GetPopularMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getPopularMovies: GetPopularMovies,
    uiDispatcher: CoroutineDispatcher = Dispatchers.Main
) : BaseViewModel(uiDispatcher) {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState>
        get() {
            if(_uiState.value == null) refresh()
            return _uiState
        }

    fun refresh() {
        launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Content(getPopularMovies.invoke())
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Content(val movies: List<Movie>) : UiState()
    }
}