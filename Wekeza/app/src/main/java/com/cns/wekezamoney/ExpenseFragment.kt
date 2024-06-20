package com.cns.wekezamoney.ui.expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cns.wekezamoney.R

class ExpenseFragment : Fragment() {

    private lateinit var expenseName: EditText
    private lateinit var expenseAmount: EditText
    private lateinit var addExpenseButton: Button
    private lateinit var expensesList: RecyclerView
    private lateinit var expenseAdapter: ExpenseAdapter
    private val expenseData: MutableList<Expense> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_expense, container, false)

        expenseName = root.findViewById(R.id.expense_name)
        expenseAmount = root.findViewById(R.id.expense_amount)
        addExpenseButton = root.findViewById(R.id.add_expense_button)
        expensesList = root.findViewById(R.id.expenses_list)

        expenseAdapter = ExpenseAdapter(expenseData)
        expensesList.layoutManager = LinearLayoutManager(context)
        expensesList.adapter = expenseAdapter

        addExpenseButton.setOnClickListener {
            val name = expenseName.text.toString()
            val amount = expenseAmount.text.toString()
            if (name.isNotEmpty() && amount.isNotEmpty()) {
                val expense = Expense(name, amount.toDouble())
                expenseData.add(expense)
                expenseAdapter.notifyDataSetChanged()
            }
        }

        return root
    }
}
