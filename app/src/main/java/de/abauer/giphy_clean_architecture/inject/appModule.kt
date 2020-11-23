package de.abauer.giphy_clean_architecture.inject

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import de.abauer.giphy_clean_architecture.AppDispatchers
import de.abauer.giphy_clean_architecture.AppDispatchersImpl
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideAppDispatchers(): AppDispatchers {
        return AppDispatchersImpl()
    }
}