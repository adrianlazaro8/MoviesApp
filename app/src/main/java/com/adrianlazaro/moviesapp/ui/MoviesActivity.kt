package com.adrianlazaro.moviesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrianlazaro.moviesapp.R

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_movies)
    }
}
