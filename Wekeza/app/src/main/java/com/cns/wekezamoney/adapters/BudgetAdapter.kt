// BudgetAdapter.kt
package com.cns.wekezamoney.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cns.wekezamoney.R
import com.cns.wekezamoney.model.Budget

class BudgetAdapter(private val budgetList: List<Budget>) : RecyclerView.Adapter<BudgetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_budget, parent, false)
        return BudgetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val budget = budgetList[position]
        holder.bind(budget)
    }

    override fun getItemCount(): Int {
        return budgetList.size
    }
}
