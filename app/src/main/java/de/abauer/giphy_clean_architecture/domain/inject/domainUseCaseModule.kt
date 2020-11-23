package de.abauer.giphy_clean_architecture.domain.inject

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import de.abauer.giphy_clean_architecture.domain.helper.ControlledRunner
import de.abauer.giphy_clean_architecture.domain.model.DataResult
import de.abauer.giphy_clean_architecture.domain.model.Giphy
import javax.inject.Qualifier

@InstallIn(ActivityRetainedComponent::class)
@Module
object DomainUseCaseModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TrendingGiphysUseCaseControlledRunner

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SearchGiphysUseCaseControlledRunner

    @TrendingGiphysUseCaseControlledRunner
    @Provides
    fun provideTrendingGiphysUseCaseControlledRunner(): ControlledRunner<DataResult<List<Giphy>>> {
        return ControlledRunner()
    }

    @SearchGiphysUseCaseControlledRunner
    @Provides
    fun provideSearchGiphysUseCaseControlledRunner(): ControlledRunner<DataResult<List<Giphy>>> {
        return ControlledRunner()
    }
}