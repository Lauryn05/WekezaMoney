package com.cns.wekezamoney.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cns.wekezamoney.R
import com.cns.wekezamoney.model.Goal

class GoalAdapter(private val goals: List<Goal>) : RecyclerView.Adapter<GoalAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(goal: Goal)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_goal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val goal = goals[position]
        holder.bind(goal)
    }

    override fun getItemCount(): Int = goals.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.goal_name)
        private val targetAmountTextView: TextView = itemView.findViewById(R.id.goal_target_amount)
        private val currentAmountTextView: TextView = itemView.findViewById(R.id.goal_current_amount)

        @SuppressLint("SetTextI18n")
        fun bind(goal: Goal) {
            nameTextView.text = goal.name
            targetAmountTextView.text = "Target Amount: ${goal.targetAmount}"
            currentAmountTextView.text = "Current Amount: ${goal.currentAmount ?: "N/A"}"

            itemView.setOnClickListener {
                listener?.onItemClick(goal)
            }
        }
    }
}
