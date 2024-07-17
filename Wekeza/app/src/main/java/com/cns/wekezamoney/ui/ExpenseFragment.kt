package com.cns.wekezamoney.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cns.wekezamoney.R
import com.cns.wekezamoney.adapters.ExpenseAdapter
import com.cns.wekezamoney.model.Expense
import com.cns.wekezamoney.viewmodel.ExpenseViewModel

class ExpenseFragment : Fragment() {

    private lateinit var expenseName: EditText
    private lateinit var expenseAmount: EditText
    private lateinit var addExpenseButton: Button
    private lateinit var expensesList: RecyclerView
    private lateinit var expenseAdapter: ExpenseAdapter
    private val expenseData: MutableList<Expense> = mutableListOf()

    private val viewModel: ExpenseViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
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

        viewModel.allExpenses.observe(viewLifecycleOwner) { expenses ->
            expenseData.clear()
            expenseData.addAll(expenses)
            expenseAdapter.notifyDataSetChanged()
        }

        addExpenseButton.setOnClickListener {
            val name = expenseName.text.toString()
            val amount = expenseAmount.text.toString()
            if (name.isNotEmpty() && amount.isNotEmpty()) {
                try {
                    val expense = Expense(name = name, amount = amount.toDouble())
                    viewModel.insert(expense)
                } catch (e: NumberFormatException) {
                    // Handle invalid amount format
                    Toast.makeText(requireContext(), "Invalid amount format", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Handle empty fields
                Toast.makeText(requireContext(), "Name and amount cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }
}
