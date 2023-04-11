package com.example.upstoxassignment.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getHoldings() = apiService.getHoldings()
}