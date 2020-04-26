package com.adrianlazaro.moviesapp.ui.main

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.adrianlazaro.moviesapp.R
import com.adrianlazaro.moviesapp.common.PermissionRequester
import com.adrianlazaro.moviesapp.common.app
import com.adrianlazaro.moviesapp.common.getViewModel
import com.adrianlazaro.moviesapp.common.toast
import kotlinx.android.synthetic.main.fragment_movies.*
import com.adrianlazaro.moviesapp.ui.main.MoviesViewModel.UiState


class MoviesFragment : Fragment() {

    private lateinit var moviesFragmentComponent: MoviesFragmentComponent
    private lateinit var adapter: MoviesAdapter

    private val moviesViewModel by lazy {
        getViewModel { moviesFragmentComponent.moviesViewModel }
    }

    private val coarsePermissionRequester by lazy {
        activity?.let {
            PermissionRequester(it, Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        activity?.run {
            moviesFragmentComponent = app.component.plus(MoviesFragmentModule())
        }

        adapter = MoviesAdapter()
        rv.adapter = adapter
        moviesViewModel.uiState.observe(viewLifecycleOwner, Observer(::updateUi))
        requestLocationPermission()
    }

    private fun updateUi(uiState: UiState) {

        progress.visibility = getProgressVisibilty(uiState)

        when (uiState) {
            is UiState.Content -> adapter.movies = uiState.movies
            is UiState.Navigation -> findNavController().navigate(MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment())
        }
    }

    private fun getProgressVisibilty(uiState: UiState): Int {
        return if (uiState is UiState.Loading) View.VISIBLE else View.GONE
    }

    private fun requestLocationPermission() {
        coarsePermissionRequester?.request { enabled ->
            if (!enabled) {
                toast(getString(R.string.disabled_permission))
            }
        }
    }

}
