package de.abauer.giphy_clean_architecture.domain.usecase

import de.abauer.giphy_clean_architecture.domain.helper.ControlledRunner
import de.abauer.giphy_clean_architecture.domain.inject.DomainUseCaseModule.TrendingGiphysUseCaseControlledRunner
import de.abauer.giphy_clean_architecture.domain.model.DataResult
import de.abauer.giphy_clean_architecture.domain.model.Giphy
import de.abauer.giphy_clean_architecture.domain.repository.TrendingGiphysRepository
import javax.inject.Inject

class TrendingGiphysUseCase @Inject constructor(
    private val trendingGiphysRepository: TrendingGiphysRepository,
    @TrendingGiphysUseCaseControlledRunner private val controlledRunner: ControlledRunner<DataResult<List<Giphy>>>
) {
    suspend fun getTrendingGiphys(): DataResult<List<Giphy>> =
        controlledRunner.joinPreviousOrRun {
            trendingGiphysRepository.getTrending()
        }
}