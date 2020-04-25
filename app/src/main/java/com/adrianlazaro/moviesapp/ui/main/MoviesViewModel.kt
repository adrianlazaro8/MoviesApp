package com.adrianlazaro.moviesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adrianlazaro.domain.Movie

class MoviesViewModel : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState : LiveData<UiState>
        get() {
            return _uiState
        }

    sealed class UiState {
        object Loading : UiState()
        class Content(val movies: List<Movie>) : UiState()
        class Navigation(val movie: Movie) : UiState()
        object RequestLocationPermission : UiState()
    }
}