package com.cns.wekezamoney.adapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cns.wekezamoney.model.Budget
import com.cns.wekezamoney.R

class BudgetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textBudgetName: TextView = itemView.findViewById(R.id.text_budget_name)
    private val textBudgetAmount: TextView = itemView.findViewById(R.id.text_budget_amount)

    @SuppressLint("StringFormatInvalid")
    fun bind(budget: Budget) {
        textBudgetName.text = budget.name
        textBudgetAmount.text = itemView.context.getString(R.string.amount_placeholder, budget.amount)
    }
}
