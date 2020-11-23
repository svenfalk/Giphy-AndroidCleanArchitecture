package de.abauer.giphy_clean_architecture.data.inject

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import de.abauer.giphy_androidcleanarchitecture.BuildConfig
import de.abauer.giphy_clean_architecture.AppDispatchers
import de.abauer.giphy_clean_architecture.data.repository.remote.SearchGiphysRemoteRepository
import de.abauer.giphy_clean_architecture.data.repository.remote.TrendingGiphysRemoteSource
import de.abauer.giphy_clean_architecture.data.repository.remote.mapper.GiphyRemoteMapper
import de.abauer.giphy_clean_architecture.data.service.ApiErrorHandler
import de.abauer.giphy_clean_architecture.data.service.ApiService
import de.abauer.giphy_clean_architecture.domain.repository.SearchGiphysRepository
import de.abauer.giphy_clean_architecture.domain.repository.TrendingGiphysRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DataRemoteModule {
    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .client(createHttpClient(context))
            .baseUrl("https://api.giphy.com/")
            .addConverterFactory(MoshiConverterFactory.create(createMoshi()))
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GiphyApiKey

    @Singleton
    @GiphyApiKey
    @Provides
    fun provideGiphyApiKey(): String {
        return BuildConfig.API_KEY
    }

    private fun createHttpClient(context: Context): OkHttpClient {
        val SERVICE_TIMEOUT_SECONDS = 60L
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(SERVICE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(SERVICE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addNetworkInterceptor(
                createHttpInspectorInterceptor(
                    context
                )
            )

        return okHttpClientBuilder.build()
    }

    private fun createHttpInspectorInterceptor(context: Context): Interceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return ChuckerInterceptor(
            context = context,
            collector = chuckerCollector,
            maxContentLength = 250000L
        )
    }

    private fun createMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}

@InstallIn(ApplicationComponent::class)
@Module
abstract class DataRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindSearchGiphysRepository(searchGiphysRepository: SearchGiphysRemoteRepository): SearchGiphysRepository

    @Singleton
    @Binds
    abstract fun bindTrendingGiphysRepository(trendingGiphysRemoteSource: TrendingGiphysRemoteSource): TrendingGiphysRepository
}


