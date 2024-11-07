package com.premierdarkcoffee.sales.cronos.di

import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.service.DataService
import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.service.ProductService
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.DataServiceable
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.ProductServiceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DependencyInjection {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    allowStructuredMapKeys = true
                    prettyPrint = true
                })
            }
            install(Logging) { level = LogLevel.ALL }
        }
    }

    @Singleton
    @Provides
    fun provideProductServiceable(httpClient: HttpClient): ProductServiceable {
        return ProductService(httpClient)
    }

    @Singleton
    @Provides
    fun provideDataServiceable(httpClient: HttpClient): DataServiceable {
        return DataService(httpClient)
    }
}
