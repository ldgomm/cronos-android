package com.premierdarkcoffee.sales.cronos.feature.product.data.remote.service

import android.content.ContentValues.TAG
import android.util.Log
import com.premierdarkcoffee.sales.cronos.feature.product.data.remote.dto.ProductDto
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.MessageResponse
import com.premierdarkcoffee.sales.cronos.feature.product.domain.serviceable.ProductServiceable
import com.premierdarkcoffee.sales.sales.feature.product.domain.model.product.request.PostProductRequest
import com.premierdarkcoffee.sales.sales.feature.product.domain.model.product.request.PutProductRequest
import com.premierdarkcoffee.sales.sales.util.constant.Categories
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode.Companion.Accepted
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.Found
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.NotModified
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductService @Inject constructor(private val httpClient: HttpClient) : ProductServiceable {

    override fun getProducts(
        endpoint: String,
        apiKey: String
    ): Flow<Result<List<ProductDto>>> {
        return flow {
            try {
                val response: HttpResponse = httpClient.get(endpoint) {
//                    header(HttpHeaders.Authorization, "Bearer $apiKey")
                    header(HttpHeaders.Authorization, "Bearer 6Y-ft0-grCS4-CJLxBSj-MXktZaqEqjD")
                }
                emit(Result.success(response.body()))
            } catch (e: Exception) {
                Log.d(TAG, "ProductService | We have no products: ${e.message}")
                emit(Result.failure(e))
            }
        }
    }

    override fun getCategories(endpoint: String): Flow<Result<Categories>> {
        return flow {
            try {
                val response: HttpResponse = httpClient.get(endpoint)
                emit(Result.success(response.body()))
            } catch (e: Exception) {
                Log.d(TAG, "ProductService | We have no products: ${e.message}")
                emit(Result.failure(e))
            }
        }
    }

    override fun addProduct(
        url: String,
        request: PostProductRequest,
        apiKey: String
    ): Flow<Result<MessageResponse>> {
        return flow {
            try {
                val response: HttpResponse = httpClient.post(url) {

//                    header(HttpHeaders.Authorization, "Bearer $apiKey")
                    header(HttpHeaders.Authorization, "Bearer 6Y-ft0-grCS4-CJLxBSj-MXktZaqEqjD")


                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
                when (response.status) {
                    Created -> {
                        val responseBody = response.body<MessageResponse>()
                        Log.d(TAG, "AddEditProductService | Product added: $responseBody")
                        emit(Result.success(responseBody))
                    }

                    Found -> {
                        val responseBody = response.body<MessageResponse>()
                        Log.d(TAG, "AddEditProductService | Product already exists: $responseBody")
                        emit(Result.success(responseBody))
                    }

                    NotFound -> {
                        Log.d(TAG, "AddEditProductService | Error with status code: ${response.status}")
                        emit(Result.failure(RuntimeException("Error occurred: Server responded with status code ${response.status}")))
                    }

                    else -> {
                        Log.d(TAG, "AddEditProductService | Unexpected response status: ${response.status}")
                        emit(Result.failure(Exception("Unexpected response status: ${response.status}")))
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "AddEditProductService | Failed to post product: ${e.message}")
                emit(Result.failure(e))
            }
        }
    }

    override fun updateProduct(
        url: String,
        request: PutProductRequest,
        apiKey: String
    ): Flow<Result<MessageResponse>> {
        return flow {
            try {
                val response: HttpResponse = httpClient.put(url) {

//                    header(HttpHeaders.Authorization, "Bearer $apiKey")
                    header(HttpHeaders.Authorization, "Bearer 6Y-ft0-grCS4-CJLxBSj-MXktZaqEqjD")

                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
                when (response.status) {
                    Accepted -> {
                        val responseBody = response.body<MessageResponse>()
                        Log.d(TAG, "AddEditProductService | Product added: $responseBody")
                        emit(Result.success(responseBody))
                    }

                    BadRequest, Unauthorized, Forbidden, NotFound, InternalServerError, NotModified -> {
                        Log.d(TAG, "AddEditProductService | Error with status code: ${response.status}")
                        emit(Result.failure(RuntimeException("Error occurred: Server responded with status code ${response.status}")))
                    }

                    else -> {
                        Log.d(TAG, "AddEditProductService | Unexpected response status: ${response.status}")
                        emit(Result.failure(Exception("Unexpected response status: ${response.status}")))
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "AddEditProductService | Failed to post product: ${e.message}")
                emit(Result.failure(e))
            }
        }
    }
}
