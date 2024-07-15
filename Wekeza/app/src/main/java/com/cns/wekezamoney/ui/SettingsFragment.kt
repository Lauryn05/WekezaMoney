package com.cns.wekezamoney.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.cns.wekezamoney.R
import com.cns.wekezamoney.R.id.username_edit_text
import com.cns.wekezamoney.database.DBHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SettingsFragment : BaseFragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var saveProfileButton: Button
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var enableNotificationsSwitch: Switch
    private lateinit var dbHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        usernameEditText = root.findViewById(username_edit_text)
        passwordEditText = root.findViewById(R.id.password_edit_text)
        saveProfileButton = root.findViewById(R.id.save_profile_button)
        enableNotificationsSwitch = root.findViewById(R.id.enable_notifications_switch)

        dbHelper = DBHelper(requireContext())

        // Load user profile information
        loadUserProfile()

        // Load notification preferences
        loadNotificationPreferences()

        saveProfileButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Update user profile in database
                updateUserProfile(username, password)
            } else {
                Toast.makeText(requireContext(), "Username and password cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        enableNotificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            val username = usernameEditText.text.toString().trim()
            if (isChecked) {
                // Enable notifications
                enableNotifications(username)
            } else {
                // Disable notifications
                disableNotifications(username)
            }
        }

        return root
    }

    private fun loadUserProfile() {
        GlobalScope.launch(Dispatchers.Main) {
            val user = dbHelper.getUser("current_username") // Replace "current_username" with actual logic to get current user
            user?.let {
                usernameEditText.setText(it.username)
                passwordEditText.setText(it.password)
            }
        }
    }

    private fun updateUserProfile(username: String, password: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val rowsUpdated = dbHelper.updateUser(username, password)
            if (rowsUpdated > 0) {
                Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadNotificationPreferences() {
        GlobalScope.launch(Dispatchers.Main) {
            val username = usernameEditText.text.toString().trim()
            val notificationsEnabled = dbHelper.areNotificationsEnabled(username)
            enableNotificationsSwitch.isChecked = notificationsEnabled
        }
    }

    private fun enableNotifications(username: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val user = dbHelper.getUser(username)
            user?.let {
                val id = dbHelper.enableNotifications(it.id)
                if (id > 0) {
                    Toast.makeText(requireContext(), "Notifications enabled", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to enable notifications", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun disableNotifications(username: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val user = dbHelper.getUser(username)
            user?.let {
                val rowsUpdated = dbHelper.disableNotifications(it.id)
                if (rowsUpdated > 0) {
                    Toast.makeText(requireContext(), "Notifications disabled", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to disable notifications", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
