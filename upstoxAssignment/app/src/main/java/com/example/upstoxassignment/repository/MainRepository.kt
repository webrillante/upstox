package com.example.upstoxassignment.repository

import com.example.upstoxassignment.api.ApiHelper


class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getHoldings() = apiHelper.getHoldings()
}