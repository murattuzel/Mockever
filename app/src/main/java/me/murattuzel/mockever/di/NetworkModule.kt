package me.murattuzel.mockever.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import me.murattuzel.mockever.BuildConfig
import me.murattuzel.mockever.data.MovieService
import me.murattuzel.mockever.util.ErrorHandlingInterceptor
import me.murattuzel.mockever.util.MockResponseInterceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when {
                BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        mockResponseInterceptor: MockResponseInterceptor,
        errorHandlingInterceptor: ErrorHandlingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(errorHandlingInterceptor)
            .addInterceptor(mockResponseInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideJson() = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providesContentService(retrofit: Retrofit): MovieService = retrofit.create()
}
