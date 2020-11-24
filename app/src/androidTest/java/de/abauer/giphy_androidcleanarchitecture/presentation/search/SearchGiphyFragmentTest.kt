package de.abauer.giphy_androidcleanarchitecture.presentation.search

import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import de.abauer.giphy_androidcleanarchitecture.R
import de.abauer.giphy_androidcleanarchitecture.launchFragmentInHiltContainer
import de.abauer.giphy_clean_architecture.data.inject.DataRepositoryModule
import de.abauer.giphy_clean_architecture.domain.model.DataResult
import de.abauer.giphy_clean_architecture.domain.model.Giphy
import de.abauer.giphy_clean_architecture.domain.repository.SearchGiphysRepository
import de.abauer.giphy_clean_architecture.domain.repository.TrendingGiphysRepository
import de.abauer.giphy_clean_architecture.presentation.search.SearchGiphysFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@UninstallModules(DataRepositoryModule::class)
@HiltAndroidTest
class SearchGiphyFragmentTest {
    private val giphy = Giphy(height = "200", width = "200",size = "11701",url = "https://media4.giphy.com/media/I80Jfc1CYKFmuz3AMM/200w.gif?cid=bdab041a7dhnopcbz6c7i31k39m1wrr9s7xkmqqtek03zt2o&rid=200w.gif")

    @BindValue
    @JvmField
    val searchGiphysRepository = object : SearchGiphysRepository {
        override suspend fun searchForGiphys(searchInput: String): DataResult<List<Giphy>> {
            return DataResult.Success(listOf(giphy, giphy, giphy))
        }
    }

    @BindValue
    @JvmField
    val trendingGiphysRepository = object : TrendingGiphysRepository {
        override suspend fun getTrending(): DataResult<List<Giphy>> {
            return DataResult.Success(emptyList())
        }
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        // Populate @Inject fields in test class
        hiltRule.inject()
    }

    @Test
    fun doSomething() {
        launchFragmentInHiltContainer<SearchGiphysFragment>(null, R.style.AppTheme)

    }
}