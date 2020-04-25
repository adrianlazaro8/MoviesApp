package com.adrianlazaro.moviesapp.ui.main

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adrianlazaro.moviesapp.R
import com.adrianlazaro.moviesapp.common.PermissionRequester
import com.adrianlazaro.moviesapp.ui.detail.MovieDetailViewModel
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment() {

    private val moviesViewModel by lazy {
        ViewModelProviders.of(this)[MoviesViewModel::class.java]
    }

    private val coarsePermissionRequester by lazy {
        activity?.let {
            PermissionRequester(it, Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  = inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        moviesViewModel.uiState.observe(viewLifecycleOwner, Observer(::updateUi))
    }

    private fun updateUi(uiState: MoviesViewModel.UiState) {

        progress.visibility = getProgressVisibilty(uiState)

        when (uiState) {
            //TODO
        }
    }

    private fun getProgressVisibilty(uiState: MoviesViewModel.UiState): Int {
        return if (uiState is MoviesViewModel.UiState.Loading) View.VISIBLE else View.GONE
    }
}
