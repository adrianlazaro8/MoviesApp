package com.adrianlazaro.moviesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adrianlazaro.domain.Movie
import com.adrianlazaro.moviesapp.ui.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MovieDetailViewModel(
    private val movieId: Int,
    uiDispatcher: CoroutineDispatcher = Dispatchers.Main
) : BaseViewModel(uiDispatcher) {

    private val _movie = MutableLiveData<UiModel>()
    val movie: LiveData<UiModel>
        get() {
            return _movie
        }

    class UiModel(val movie: Movie)
}