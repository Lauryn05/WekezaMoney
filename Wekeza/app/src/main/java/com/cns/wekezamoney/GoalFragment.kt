package com.cns.wekezamoney.ui.goal

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
            val amount = goalAmount.text.toString()
            if (name.isNotEmpty() && amount.isNotEmpty()) {
                val goal = Goal(name, amount.toDouble())
                goalData.add(goal)
                goalAdapter.notifyDataSetChanged()
            }
        }

        return root
    }
}
