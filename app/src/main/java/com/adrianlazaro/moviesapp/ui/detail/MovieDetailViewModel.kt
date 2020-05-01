package com.adrianlazaro.moviesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adrianlazaro.domain.Movie
import com.adrianlazaro.moviesapp.ui.BaseViewModel
import com.adrianlazaro.usecases.GetMovieById
import com.adrianlazaro.usecases.ToggleMovieFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieId: Int,
    private val findMovieById: GetMovieById,
    private val toggleMovieFavorite: ToggleMovieFavorite,
    uiDispatcher: CoroutineDispatcher = Dispatchers.Main
) : BaseViewModel(uiDispatcher) {

    private val _movie = MutableLiveData<UiModel>()
    val movie: LiveData<UiModel>
        get() {
            if (_movie.value == null) findMovie()
            return _movie
        }

    private fun findMovie() = launch {
        _movie.value = UiModel(findMovieById.invoke(movieId))
    }

    fun onFavoriteClicked() = launch {
        _movie.value?.movie?.let {
            _movie.value = UiModel(toggleMovieFavorite.invoke(it))
        }
    }

    data class UiModel(val movie: Movie)
}