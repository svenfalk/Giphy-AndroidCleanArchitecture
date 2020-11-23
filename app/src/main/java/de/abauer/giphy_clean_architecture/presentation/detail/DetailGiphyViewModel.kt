package de.abauer.giphy_clean_architecture.presentation.detail

import androidx.hilt.lifecycle.ViewModelInject
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.data.UIState

class DetailGiphyViewModel @ViewModelInject constructor() : AndroidDataFlow(defaultState = UIState.Empty)  {

    fun onGiphyReceived(giphyUrl: String) = action {
        setState(DetailGiphyState.LoadGiphy(giphyUrl))
    }

    fun onShareButtonClick(giphyUrl: String) = action {
        setState(DetailGiphyState.ShareGiphy(giphyUrl))
    }

}
