package com.cns.wekezamoney.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cns.wekezamoney.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class HomeFragment : Fragment() {

    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        pieChart = root.findViewById(R.id.pieChart)

        val entries = listOf(
            PieEntry(30.0f, "Food"),
            PieEntry(20.0f, "Transport"),
            PieEntry(50.0f, "Rent")
        )

        val dataSet = PieDataSet(entries, "Expenses")
        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.invalidate() // refresh

        return root
    }
}
