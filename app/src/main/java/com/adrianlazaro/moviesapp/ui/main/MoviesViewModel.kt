package com.adrianlazaro.moviesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adrianlazaro.data.repository.MoviesRepository
import com.adrianlazaro.domain.Movie
import com.adrianlazaro.moviesapp.data.database.RoomDataSource
import com.adrianlazaro.usecases.GetPopularMovies

class MoviesViewModel(
    private val getPopularMovies: GetPopularMovies
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState>
        get() {
            return _uiState
        }

    sealed class UiState {
        object Loading : UiState()
        class Content(val movies: ArrayList<Movie>) : UiState()
        class Navigation(val movie: Movie) : UiState()
    }
}