package com.example.upstoxassignment.data

data class Holding(
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avg_price: Double,
    val close: Double,
    val pnl: Double
)

data class HoldingsData(
    val client_id: String,
    val data: List<Data>,
    val error: Any?,
    val response_type: String,
    val timestamp: Long
)

data class Data(
    val avg_price: Double,
    val cnc_used_quantity: Int,
    val collateral_qty: Int,
    val collateral_type: String,
    val collateral_update_qty: Int,
    val company_name: String,
    val haircut: Double,
    val holdings_update_qty: Int,
    val isin: String,
    val product: String,
    val quantity: Int,
    val symbol: String,
    val t1_holding_qty: Int,
    val token_bse: String,
    val token_nse: String,
    val withheld_collateral_qty: Int,
    val withheld_holding_qty: Int,
    val ltp: Double,
    val close: Double
)