package com.oreilly.hellokotlin.astro

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://api.open-notify.org/"

private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
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