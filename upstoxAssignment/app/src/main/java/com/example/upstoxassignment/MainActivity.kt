package com.example.upstoxassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.upstoxassignment.adapters.HoldingsAdapter
import com.example.upstoxassignment.api.ApiHelper
import com.example.upstoxassignment.api.RetrofitBuilder
import com.example.upstoxassignment.utils.Status

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private var recyclerView: RecyclerView? = null

    private lateinit var currentValue : TextView
    private lateinit var totalInvestmentValue : TextView
    private lateinit var totalProfitLossValue : TextView
    private lateinit var profitLossValue : TextView
    private lateinit var progressBar : ProgressBar
    private lateinit var bottomInfo : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        initViews()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            MainActivityViewModel.ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[MainActivityViewModel::class.java]
    }

    private fun setupObservers() {
        viewModel.getHoldings().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        viewModel.holdingsData = it.data?.data
                        viewModel.updateData()
                        setData()
                        recyclerView?.visibility = View.VISIBLE
                        bottomInfo.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        recyclerView?.visibility = View.VISIBLE
                        bottomInfo.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView?.visibility = View.GONE
                        bottomInfo.visibility = View.GONE

                    }
                }
            }
        })
    }
    
    private fun setData() {
        recyclerView?.adapter?.notifyDataSetChanged()
        currentValue.text = viewModel.currentValue.toString()
        totalInvestmentValue.text = viewModel.totalInvestment.toString()
        totalProfitLossValue.text = viewModel.todaysPnl.toString()
        profitLossValue.text = viewModel.totalPnl.toString()
    }

    private fun initViews() {
        currentValue = findViewById<TextView>(R.id.current_value)
        totalInvestmentValue = findViewById<TextView>(R.id.total_invenstment_value)
        totalProfitLossValue = findViewById<TextView>(R.id.today_profit_loss_value)
        profitLossValue = findViewById<TextView>(R.id.profit_and_loss_value)
        bottomInfo = findViewById<ConstraintLayout>(R.id.bottomInfo)
        progressBar = findViewById<ProgressBar>(R.id.loadingProgressBar)

        recyclerView = findViewById(R.id.holdingList)
        recyclerView?.run {
            layoutManager = LinearLayoutManager(context)
            adapter = HoldingsAdapter(viewModel.holdings)
        }
    }
}