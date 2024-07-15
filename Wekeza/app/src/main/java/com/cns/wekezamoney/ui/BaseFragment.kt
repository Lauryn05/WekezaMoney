package com.cns.wekezamoney.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cns.wekezamoney.R

@Suppress("DEPRECATION")
open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.activity_main_drawer, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_expense -> {
                findNavController().navigate(R.id.expenseFragment)
                return true
            }
            R.id.nav_budget -> {
                findNavController().navigate(R.id.budgetFragment)
                return true
            }
            R.id.nav_goal -> {
                findNavController().navigate(R.id.goalFragment)
                return true
            }
            R.id.nav_settings -> {
                findNavController().navigate(R.id.settingsFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
