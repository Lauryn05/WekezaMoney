package com.cns.wekezamoney.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cns.wekezamoney.R
import com.cns.wekezamoney.model.Budget

class BudgetAdapter(private val budgets: List<Budget>) : RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(budget: Budget)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_budget, parent, false)
        return BudgetViewHolder(view)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val budget = budgets[position]
        holder.bind(budget)
    }

    override fun getItemCount(): Int = budgets.size

    inner class BudgetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.text_budget_name)
        private val amountTextView: TextView = itemView.findViewById(R.id.text_budget_amount)

        @SuppressLint("SetTextI18n")
        fun bind(budget: Budget) {
            nameTextView.text = budget.name
            amountTextView.text = budget.amount.toString()

            itemView.setOnClickListener {
                listener?.onItemClick(budget)
            }
        }
    }
}
