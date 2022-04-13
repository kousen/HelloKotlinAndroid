package com.oreilly.hellokotlin.astro

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "http://api.open-notify.org/"

@OptIn(ExperimentalSerializationApi::class)
private val retrofit = Retrofit.Builder()
        //.addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

interface AstroService {
    @GET("astros.json")
    suspend fun getAstroResult(): AstroResult
}

object AstroApi {
    // property delegate called "lazy"
    val retrofitService: AstroService by lazy {
        retrofit.create(AstroService::class.java)
    }
}