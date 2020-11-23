package de.abauer.giphy_clean_architecture.domain.usecase

import de.abauer.giphy_clean_architecture.domain.helper.ControlledRunner
import de.abauer.giphy_clean_architecture.domain.inject.DomainUseCaseModule.SearchGiphysUseCaseControlledRunner
import de.abauer.giphy_clean_architecture.domain.model.DataResult
import de.abauer.giphy_clean_architecture.domain.model.Giphy
import de.abauer.giphy_clean_architecture.domain.repository.SearchGiphysRepository
import javax.inject.Inject

class SearchGiphysUseCase @Inject constructor(
    private val searchGiphysRepository: SearchGiphysRepository,
    @SearchGiphysUseCaseControlledRunner private val controlledRunner: ControlledRunner<DataResult<List<Giphy>>>
) {
    suspend fun searchGiphysForText(searchInput: String): DataResult<List<Giphy>> =
        controlledRunner.cancelPreviousThenRun {
            searchGiphysRepository.searchForGiphys(searchInput)
        }
}