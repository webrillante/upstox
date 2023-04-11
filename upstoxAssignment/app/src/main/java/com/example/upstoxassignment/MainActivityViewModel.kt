package com.example.upstoxassignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.upstoxassignment.api.ApiHelper
import com.example.upstoxassignment.data.Data
import com.example.upstoxassignment.data.Holding
import com.example.upstoxassignment.repository.MainRepository
import com.example.upstoxassignment.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlin.math.roundToInt

class MainActivityViewModel(private val mainRepository: MainRepository?) : ViewModel() {

    val holdings = mutableListOf<Holding>()
    var holdingsData: List<Data>? = null
    var currentValue : Double = 0.0
    var totalInvestment : Double = 0.0
    var totalPnl : Double = 0.0
    var todaysPnl : Double = 0.0

    fun getHoldings() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository?.getHoldings()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun updateData() {
        holdings.clear()
        holdingsData?.forEach {
            val currentVal = it.ltp * it.quantity
            val investmentVal = it.avg_price * it.quantity
            val pnl = currentVal - investmentVal

            holdings.add(
                Holding(
                    symbol = it.symbol,
                    quantity = it.quantity,
                    ltp = (it.ltp* 100.0).roundToInt()/100.0,
                    avg_price = it.avg_price,
                    close = it.close,
                    pnl = (pnl * 100.0).roundToInt()/100.0
                )
            )
        }

        currentValue = holdings.sumOf { it.ltp * it.quantity }
        totalInvestment = holdings.sumOf { it.avg_price * it.quantity }
        totalPnl = currentValue - totalInvestment
        todaysPnl = holdingsData?.sumOf { (it.close - it.ltp) * it.quantity }!!
    }

    class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                return MainActivityViewModel(MainRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }

    }
}