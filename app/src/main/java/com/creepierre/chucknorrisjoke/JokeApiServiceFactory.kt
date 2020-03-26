package com.creepierre.chucknorrisjoke

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object JokeApiServiceFactory {

    @kotlinx.serialization.UnstableDefault
    fun createJokeApiService():JokeApiService {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io")
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofitBuilder.create(JokeApiService::class.java)
    }
}