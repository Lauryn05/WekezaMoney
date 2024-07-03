package com.cns.wekezamoney.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cns.wekezamoney.R
import com.cns.wekezamoney.model.Expense

class ExpenseAdapter(private val expenses: List<Expense>) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.bind(expense)
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.expense_name)
        private val amountTextView: TextView = itemView.findViewById(R.id.expense_amount)

        fun bind(expense: Expense) {
            nameTextView.text = expense.name
            amountTextView.text = expense.amount.toString()
        }
    }
}
