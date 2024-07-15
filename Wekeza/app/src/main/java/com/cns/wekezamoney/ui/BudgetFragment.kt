package com.cns.wekezamoney.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cns.wekezamoney.R
import com.cns.wekezamoney.adapters.BudgetAdapter
import com.cns.wekezamoney.model.Budget

class BudgetFragment : BaseFragment() {

    private lateinit var budgetName: EditText
    private lateinit var budgetAmount: EditText
    private lateinit var addBudgetButton: Button
    private lateinit var budgetList: RecyclerView
    private lateinit var budgetAdapter: BudgetAdapter
    private val budgetData: MutableList<Budget> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_budget, container, false)

        budgetName = root.findViewById(R.id.budget_name)
        budgetAmount = root.findViewById(R.id.budget_amount)
        addBudgetButton = root.findViewById(R.id.add_budget_button)
        budgetList = root.findViewById(R.id.budget_list)

        budgetAdapter = BudgetAdapter(budgetData)
        budgetList.layoutManager = LinearLayoutManager(context)
        budgetList.adapter = budgetAdapter

        addBudgetButton.setOnClickListener {
            val name = budgetName.text.toString()
            val amount = budgetAmount.text.toString()
            if (name.isNotEmpty() && amount.isNotEmpty()) {
                try {
                    val budget = Budget(name, amount.toDouble())
                    budgetData.add(budget)
                    budgetAdapter.notifyDataSetChanged()
                } catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), "Invalid budget amount format", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Name and budget amount cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }
}
