package de.abauer.giphy_clean_architecture.presentation.trending

import androidx.hilt.lifecycle.ViewModelInject
import de.abauer.giphy_clean_architecture.domain.model.DataResult
import de.abauer.giphy_clean_architecture.domain.usecase.TrendingGiphysUseCase
import io.uniflow.androidx.flow.AndroidDataFlow

class TrendingGiphysViewModel @ViewModelInject constructor(
    private val trendingGiphysUseCase: TrendingGiphysUseCase
) : AndroidDataFlow(defaultState = TrendingGiphysState.Loading)  {

    fun getTrendingGiphys() = action {
        when(val result = trendingGiphysUseCase.getTrendingGiphys()) {
            is DataResult.Success -> setState { TrendingGiphysState.ShowSuccess(result.value) }
            is DataResult.Error -> setState { TrendingGiphysState.ShowError }
        }
    }
}

