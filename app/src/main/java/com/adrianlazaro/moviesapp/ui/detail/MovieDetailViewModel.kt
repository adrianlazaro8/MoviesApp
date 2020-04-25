package com.adrianlazaro.moviesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adrianlazaro.domain.Movie

class MovieDetailViewModel {

    private val _movie = MutableLiveData<UiModel>()
    val movie: LiveData<UiModel>
        get() {
            return _movie
        }

    class UiModel(val movie: Movie)
}