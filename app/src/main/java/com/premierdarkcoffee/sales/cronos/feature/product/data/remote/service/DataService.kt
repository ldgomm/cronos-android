package com.premierdarkcoffee.sales.cronos.feature.product.data.remote.service

import android.content.ContentValues.TAG
import android.util.Log
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.DataServiceable
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.Group
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.Found
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataService @Inject constructor(private val httpClient: HttpClient) : DataServiceable {

    override fun addGroup(
        url: String,
        group: Group
    ): Flow<Result<Group>> {
        return flow {
            try {
                val response: HttpResponse = httpClient.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(group)
                }
                when (response.status) {
                    Created -> {
                        val responseBody = response.body<Group>()
                        Log.d(TAG, "DataService | Product added: $responseBody")
                        emit(Result.success(responseBody))
                    }

                    Found -> {
                        val responseBody = response.body<Group>()
                        Log.d(TAG, "DataService | Product already exists: $responseBody")
                        emit(Result.success(responseBody))
                    }

                    NotFound -> {
                        Log.d(TAG, "DataService | Error with status code: ${response.status}")
                        emit(Result.failure(RuntimeException("Error occurred: Server responded with status code ${response.status}")))
                    }

                    else -> {
                        Log.d(TAG, "DataService | Unexpected response status: ${response.status}")
                        emit(Result.failure(Exception("Unexpected response status: ${response.status}")))
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "DataService | Failed to post product: ${e.message}")
                emit(Result.failure(e))
            }
        }
    }

    override fun getGroups(url: String): Flow<Result<List<Group>>> {
        return flow {
            try {
                val response: HttpResponse = httpClient.get(url)
//                Log.d(TAG, "ProductService | We have products: ${response.body<List<Group>>()}")
                emit(Result.success(response.body()))
            } catch (e: Exception) {
                Log.d(TAG, "ProductService | We have no products: ${e.message}")
                emit(Result.failure(e))
            }
        }
    }
}
