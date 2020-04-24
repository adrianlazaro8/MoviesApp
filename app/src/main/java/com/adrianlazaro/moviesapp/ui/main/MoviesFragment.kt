package com.adrianlazaro.moviesapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.adrianlazaro.moviesapp.R

class MoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  = inflater.inflate(R.layout.fragment_movies, container, false)


}
