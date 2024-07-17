package com.cns.wekezamoney.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
    private lateinit var totalExpensesTextView: TextView
    private val expenseData: MutableList<Expense> = mutableListOf()

    private val viewModel: ExpenseViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_expense, container, false)

        expenseName = root.findViewById(R.id.expense_name)
        expenseAmount = root.findViewById(R.id.expense_amount)
        addExpenseButton = root.findViewById(R.id.add_expense_button)
        expensesList = root.findViewById(R.id.expenses_list)
        totalExpensesTextView = root.findViewById(R.id.total_expenses)


        expenseAdapter = ExpenseAdapter(expenseData)
        expensesList.layoutManager = LinearLayoutManager(context)
        expensesList.adapter = expenseAdapter

        viewModel.allExpenses.observe(viewLifecycleOwner) { expenses ->
            expenseData.clear()
            expenseData.addAll(expenses)
            expenseAdapter.notifyDataSetChanged()
        }

        viewModel.totalExpenses.observe(viewLifecycleOwner) { total ->
            totalExpensesTextView.text = "Total: $$total"
        }

        addExpenseButton.setOnClickListener {
            val name = expenseName.text.toString()
            val amount = expenseAmount.text.toString()
            if (name.isNotEmpty() && amount.isNotEmpty()) {
                try {
                    val expense = Expense(name = name, amount = amount.toDouble())
                    viewModel.insert(expense)
                } catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), "Invalid amount format", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Name and amount cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        expenseAdapter.setOnItemClickListener(object : ExpenseAdapter.OnItemClickListener {
            override fun onItemClick(expense: Expense) {
                showUpdateOrDeleteDialog(expense)
            }
        })

        return root
    }

    private fun showUpdateOrDeleteDialog(expense: Expense) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Update or Delete Expense")
            .setMessage("What do you want to do with this expense?")
            .setPositiveButton("Update") { _, _ ->
                showUpdateDialog(expense)
            }
            .setNegativeButton("Delete") { _, _ ->
                viewModel.delete(expense)
            }
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun showUpdateDialog(expense: Expense) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_update_expense, null)
        val dialogName = dialogView.findViewById<EditText>(R.id.dialog_expense_name)
        val dialogAmount = dialogView.findViewById<EditText>(R.id.dialog_expense_amount)

        dialogName.setText(expense.name)
        dialogAmount.setText(expense.amount.toString())

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Update Expense")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val newName = dialogName.text.toString()
                val newAmountString = dialogAmount.text.toString()

                if (newName.isNotEmpty() && newAmountString.isNotEmpty()) {
                    try {
                        val newAmount = newAmountString.toDouble()
                        val updatedExpense = expense.copy(name = newName, amount = newAmount)
                        viewModel.update(updatedExpense)
                    } catch (e: NumberFormatException) {
                        Toast.makeText(requireContext(), "Invalid amount format", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Name and amount cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }
}
