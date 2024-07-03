package com.cns.wekezamoney.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cns.wekezamoney.R
import com.cns.wekezamoney.adapters.GoalAdapter
import com.cns.wekezamoney.model.Goal

class GoalFragment : Fragment() {

    private lateinit var goalName: EditText
    private lateinit var goalAmount: EditText
    private lateinit var addGoalButton: Button
    private lateinit var goalList: RecyclerView
    private lateinit var goalAdapter: GoalAdapter
    private val goalData: MutableList<Goal> = mutableListOf()

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

        addGoalButton.setOnClickListener {
            val name = goalName.text.toString()
            val targetAmountString = goalAmount.text.toString()

            if (name.isNotEmpty() && targetAmountString.isNotEmpty()) {
                try {
                    val targetAmount = targetAmountString.toDouble()
                    val goal = Goal(name, targetAmount, null) // currentAmount is initially null
                    goalData.add(goal)
                    goalAdapter.notifyDataSetChanged()
                } catch (e: NumberFormatException) {
                    // Handle if targetAmountString cannot be parsed to Double
                    // For example, show a toast message
                    Toast.makeText(requireContext(), "Invalid target amount format", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Handle if name or targetAmountString is empty
                // For example, show a toast message
                Toast.makeText(requireContext(), "Name and target amount cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }
}
