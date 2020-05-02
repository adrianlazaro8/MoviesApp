package com.adrianlazaro.moviesapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.adrianlazaro.moviesapp.R
import com.adrianlazaro.moviesapp.common.app
import com.adrianlazaro.moviesapp.common.getViewModel
import com.adrianlazaro.moviesapp.common.loadUrl
import com.adrianlazaro.moviesapp.ui.main.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf

class MovieDetailFragment : Fragment() {

    private val args : MovieDetailFragmentArgs by navArgs()

    private val movieDetailViewModel : MovieDetailViewModel by lifecycleScope.viewModel(this) {
        parametersOf(args.DetailActivityMovieId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        movieDetailFavorite.setOnClickListener { movieDetailViewModel.onFavoriteClicked() }
        movieDetailViewModel.movie.observe(viewLifecycleOwner, Observer(::updateUi))
    }

    private fun updateUi(model: MovieDetailViewModel.UiModel) = with(model.movie) {
        toolbar_detail.title = title
        iv_movie.loadUrl(posterPath, true)
        tv_movie_details_summary.text = overview
        movieDetailInfo.setMovie(this)

        val icon = if (favorite) R.drawable.ic_favorite_fill else R.drawable.ic_favorite_border
        movieDetailFavorite.setImageDrawable(context?.getDrawable(icon))
    }
}
