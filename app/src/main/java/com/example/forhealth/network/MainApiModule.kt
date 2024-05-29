package com.example.forhealth.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


private const val BASE_URL = ""

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface MainApiService
{

}

object MainApiModule
{
    val retrofitService : MainApiService by lazy {retrofit.create(MainApiService::class.java)}
}