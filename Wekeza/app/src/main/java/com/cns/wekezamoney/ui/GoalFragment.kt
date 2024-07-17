package com.cns.wekezamoney.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cns.wekezamoney.R
import com.cns.wekezamoney.adapters.GoalAdapter
import com.cns.wekezamoney.model.Goal
import com.cns.wekezamoney.viewmodel.GoalViewModel

class GoalFragment : BaseFragment() {

    private lateinit var goalName: EditText
    private lateinit var goalAmount: EditText
    private lateinit var addGoalButton: Button
    private lateinit var goalList: RecyclerView
    private lateinit var goalAdapter: GoalAdapter
    private val goalData: MutableList<Goal> = mutableListOf()

    private val viewModel: GoalViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_goal, container, false)

        goalName = root.findViewById(R.id.goal_name)
        goalAmount = root.findViewById(R.id.goal_amount)
        addGoalButton = root.findViewById(R.id.add_goal_button)
        goalList = root.findViewById(R.id.goal_list)

        goalAdapter = GoalAdapter(goalData)
        goalList.layoutManager = LinearLayoutManager(context)
        goalList.adapter = goalAdapter

        // Observe LiveData from ViewModel
        viewModel.allGoals.observe(viewLifecycleOwner) { goals ->
            goalData.clear()
            goalData.addAll(goals)
            goalAdapter.notifyDataSetChanged()
        }

        // Add Goal button click listener
        addGoalButton.setOnClickListener {
            val name = goalName.text.toString()
            val targetAmountString = goalAmount.text.toString()

            if (name.isNotEmpty() && targetAmountString.isNotEmpty()) {
                try {
                    val targetAmount = targetAmountString.toDouble()
                    val goal = Goal(name = name, targetAmount = targetAmount, currentAmount = null)
                    viewModel.insertGoal(goal)
                } catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), "Invalid target amount format", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Name and target amount cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle item click in RecyclerView (for updating or deleting)
        goalAdapter.setOnItemClickListener(object : GoalAdapter.OnItemClickListener {
            override fun onItemClick(goal: Goal) {
                showUpdateOrDeleteDialog(goal)
            }
        })

        return root
    }

    // Method to show dialog for updating or deleting a goal
    private fun showUpdateOrDeleteDialog(goal: Goal) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Update or Delete Goal")
            .setMessage("What do you want to do with this goal?")
            .setPositiveButton("Update") { _, _ ->
                showUpdateDialog(goal)
            }
            .setNegativeButton("Delete") { _, _ ->
                viewModel.deleteGoal(goal)
            }
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    // Method to show dialog for updating a goal
    private fun showUpdateDialog(goal: Goal) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_update_goal, null)
        val dialogName = dialogView.findViewById<EditText>(R.id.dialog_goal_name)
        val dialogAmount = dialogView.findViewById<EditText>(R.id.dialog_goal_amount)

        dialogName.setText(goal.name)
        dialogAmount.setText(goal.targetAmount.toString())

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Update Goal")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val newName = dialogName.text.toString()
                val newAmountString = dialogAmount.text.toString()

                if (newName.isNotEmpty() && newAmountString.isNotEmpty()) {
                    try {
                        val newAmount = newAmountString.toDouble()
                        val updatedGoal = goal.copy(name = newName, targetAmount = newAmount)
                        viewModel.updateGoal(updatedGoal)
                    } catch (e: NumberFormatException) {
                        Toast.makeText(requireContext(), "Invalid target amount format", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Name and target amount cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }
}
