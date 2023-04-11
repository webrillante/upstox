package com.example.upstoxassignment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.upstoxassignment.R
import com.example.upstoxassignment.data.Holding

class HoldingsAdapter(private val holdings: List<Holding>): RecyclerView.Adapter<HoldingsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return holdings.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = holdings[position]

        holder.symbol.text = item.symbol.toString()
        holder.quantity.text = item.quantity.toString()
        holder.ltp.text = item.ltp.toString()
        holder.pnl.text = item.pnl.toString()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val symbol = itemView.findViewById(R.id.symbol) as TextView
        val quantity = itemView.findViewById(R.id.quantity) as TextView
        val ltp = itemView.findViewById(R.id.ltp) as TextView
        val pnl = itemView.findViewById(R.id.pnl) as TextView
    }

}