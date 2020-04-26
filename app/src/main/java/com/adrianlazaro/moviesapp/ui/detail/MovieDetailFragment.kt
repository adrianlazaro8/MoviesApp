package com.adrianlazaro.moviesapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import com.adrianlazaro.moviesapp.R
import com.adrianlazaro.moviesapp.common.app
import com.adrianlazaro.moviesapp.common.getViewModel
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieDetailFragment : Fragment() {

    companion object {
        const val MOVIE = "DetailActivity:movieId"
    }

    val args : MovieDetailFragmentArgs by navArgs()

    private lateinit var movieDetailFragmentComponent: MovieDetailFragmentComponent

    private val movieDetailViewModel by lazy {
        getViewModel { movieDetailFragmentComponent.movieDetailViewModel }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.run {
            movieDetailFragmentComponent = app.component.plus(MovieDetailFragmentModule(args.DetailActivityMovieId))
        }


    }
}
