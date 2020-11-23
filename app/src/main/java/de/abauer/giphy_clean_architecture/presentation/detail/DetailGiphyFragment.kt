package de.abauer.giphy_clean_architecture.presentation.detail

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import de.abauer.giphy_androidcleanarchitecture.R
import de.abauer.giphy_androidcleanarchitecture.databinding.FragmentGiphyDetailBinding
import de.abauer.giphy_clean_architecture.data.inject.GlideApp
import io.uniflow.androidx.flow.onStates
import viewLifecycleLazy

@AndroidEntryPoint
class DetailGiphyFragment : Fragment(R.layout.fragment_giphy_detail) {

    private val giphyDetailFragmentArgs: DetailGiphyFragmentArgs by navArgs()
    private val detailGiphyViewModel by viewModels<DetailGiphyViewModel>()

    private val binding by viewLifecycleLazy { FragmentGiphyDetailBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initStateHandling()

        detailGiphyViewModel.onGiphyReceived(giphyDetailFragmentArgs.giphyUrl)

        binding.fabDetail.setOnClickListener {
            detailGiphyViewModel.onShareButtonClick(giphyDetailFragmentArgs.giphyUrl)
        }
    }

    private fun initStateHandling() {
        onStates(detailGiphyViewModel) { state ->
            when (state) {
                is DetailGiphyState.LoadGiphy -> {
                    loadGiphy(state.giphyUrl)
                }
                is DetailGiphyState.ShareGiphy -> {
                    createShareChooser(state.giphyUrl)
                }
            }
        }
    }

    private fun loadGiphy(url: String) {
        context?.let {
            GlideApp.with(it).asGif().load(url).into(binding.imageViewDetailGiphy)
        }
    }

    private fun createShareChooser(url: String) {
        ShareCompat.IntentBuilder.from(activity as Activity)
            .setType("text/plain")
            .setChooserTitle(getString(R.string.share_giphy))
            .setText(url)
            .startChooser()
    }
}