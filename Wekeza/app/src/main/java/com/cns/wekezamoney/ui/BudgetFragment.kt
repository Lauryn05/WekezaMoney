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
import com.cns.wekezamoney.adapters.BudgetAdapter
import com.cns.wekezamoney.model.Budget
import com.cns.wekezamoney.viewmodel.BudgetViewModel

class BudgetFragment : Fragment() {

    private lateinit var budgetName: EditText
    private lateinit var budgetAmount: EditText
    private lateinit var addBudgetButton: Button
    private lateinit var budgetList: RecyclerView
    private lateinit var budgetAdapter: BudgetAdapter
    private val budgetData: MutableList<Budget> = mutableListOf()
    private lateinit var totalBudgetsTextView: TextView


    private val viewModel: BudgetViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_budget, container, false)

        budgetName = root.findViewById(R.id.budget_name)
        budgetAmount = root.findViewById(R.id.budget_amount)
        addBudgetButton = root.findViewById(R.id.add_budget_button)
        budgetList = root.findViewById(R.id.budget_list)
        totalBudgetsTextView = root.findViewById(R.id.total_budget)

        budgetAdapter = BudgetAdapter(budgetData)
        budgetList.layoutManager = LinearLayoutManager(context)
        budgetList.adapter = budgetAdapter

        viewModel.allBudgets.observe(viewLifecycleOwner) { budgets ->
            budgetData.clear()
            budgetData.addAll(budgets)
            budgetAdapter.notifyDataSetChanged()
        }

        viewModel.totalBudgets.observe(viewLifecycleOwner) { total ->
            totalBudgetsTextView.text = "Total: $$total"
        }

        addBudgetButton.setOnClickListener {
            val name = budgetName.text.toString()
            val amount = budgetAmount.text.toString()
            if (name.isNotEmpty() && amount.isNotEmpty()) {
                try {
                    val budget = Budget(name = name, amount = amount.toDouble())
                    viewModel.insert(budget)
                } catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), "Invalid amount format", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Name and amount cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        budgetAdapter.setOnItemClickListener(object : BudgetAdapter.OnItemClickListener {
            override fun onItemClick(budget: Budget) {
                showUpdateOrDeleteDialog(budget)
            }
        })

        return root
    }

    private fun showUpdateOrDeleteDialog(budget: Budget) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Update or Delete Budget")
            .setMessage("What do you want to do with this budget?")
            .setPositiveButton("Update") { _, _ ->
                showUpdateDialog(budget)
            }
            .setNegativeButton("Delete") { _, _ ->
                viewModel.delete(budget)
            }
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun showUpdateDialog(budget: Budget) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_update_budget, null)
        val dialogName = dialogView.findViewById<EditText>(R.id.dialog_budget_name)
        val dialogAmount = dialogView.findViewById<EditText>(R.id.dialog_budget_amount)

        dialogName.setText(budget.name)
        dialogAmount.setText(budget.amount.toString())

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Update Budget")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val newName = dialogName.text.toString()
                val newAmountString = dialogAmount.text.toString()

                if (newName.isNotEmpty() && newAmountString.isNotEmpty()) {
                    try {
                        val newAmount = newAmountString.toDouble()
                        val updatedBudget = budget.copy(name = newName, amount = newAmount)
                        viewModel.update(updatedBudget)
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
