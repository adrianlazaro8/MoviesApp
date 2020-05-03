package com.adrianlazaro.moviesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adrianlazaro.domain.Movie
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
            if(_uiState.value == null) requestMovies()
            return _uiState
        }

    fun requestMovies() {
        launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Content(getPopularMovies.invoke())
        }
    }

    fun refresh(){
        _uiState.value = UiState.Refresh
    }

    sealed class UiState {
        object Loading : UiState()
        object Refresh : UiState()
        data class Content(val movies: List<Movie>) : UiState()
    }
}