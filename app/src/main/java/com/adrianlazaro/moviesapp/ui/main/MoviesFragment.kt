package com.adrianlazaro.moviesapp.ui.main

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.adrianlazaro.moviesapp.R
import com.adrianlazaro.moviesapp.common.PermissionRequester
import com.adrianlazaro.moviesapp.common.toast
import kotlinx.android.synthetic.main.fragment_movies.*
import com.adrianlazaro.moviesapp.ui.main.MoviesViewModel.UiState
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel


class MoviesFragment : Fragment() {

    private lateinit var adapter: MoviesAdapter

    private val moviesViewModel : MoviesViewModel by lifecycleScope.viewModel(this)

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
        adapter = MoviesAdapter{
            findNavController().navigate(MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(it.id));
        }
        initSwipeRefresh()
        rv.adapter = adapter
        requestLocationPermission()
    }

    private fun initSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            moviesViewModel.refresh()
        }
    }

    private fun updateUi(uiState: UiState) {
        progress.visibility = View.GONE
        when (uiState) {
            is UiState.Loading -> progress.visibility = View.VISIBLE
            is UiState.Refresh -> {
                swipeRefreshLayout.isRefreshing = true
                moviesViewModel.requestMovies()
            }
            is UiState.Content -> {
                adapter.movies = uiState.movies
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun requestLocationPermission() {
        coarsePermissionRequester?.request { enabled ->
            if (!enabled) {
                toast(getString(R.string.disabled_permission))
            }
            moviesViewModel.uiState.observe(viewLifecycleOwner, Observer(::updateUi))
        }
    }
}
