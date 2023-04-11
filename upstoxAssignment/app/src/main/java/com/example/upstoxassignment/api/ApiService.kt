package com.example.upstoxassignment.api

import com.example.upstoxassignment.data.HoldingsData
import retrofit2.http.GET

interface ApiService {

    @GET("6d0ad460-f600-47a7-b973-4a779ebbaeaf")
    suspend fun getHoldings(): HoldingsData

}