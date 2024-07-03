package com.cns.wekezamoney.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cns.wekezamoney.R
import com.cns.wekezamoney.model.Goal

class GoalAdapter(private val goals: List<Goal>) : RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_goal, parent, false)
        return GoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goals[position]
        holder.bind(goal)
    }

    override fun getItemCount(): Int {
        return goals.size
    }

    class GoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.goal_name)
        private val targetAmountTextView: TextView = itemView.findViewById(R.id.goal_target_amount)
        private val currentAmountTextView: TextView = itemView.findViewById(R.id.goal_current_amount)

        fun bind(goal: Goal) {
            nameTextView.text = goal.name
            targetAmountTextView.text = goal.targetAmount.toString()
            currentAmountTextView.text = goal.currentAmount.toString()
        }
    }
}
