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
import com.cns.wekezamoney.database.UserDatabase
import com.cns.wekezamoney.model.User
import com.cns.wekezamoney.repository.UserRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class SettingsFragment : BaseFragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var saveProfileButton: Button
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var enableNotificationsSwitch: Switch
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        usernameEditText = root.findViewById(R.id.username_edit_text)
        passwordEditText = root.findViewById(R.id.password_edit_text)
        saveProfileButton = root.findViewById(R.id.save_profile_button)
        enableNotificationsSwitch = root.findViewById(R.id.enable_notifications_switch)

        val userDao = UserDatabase.getDatabase(requireContext()).userDao()
        userRepository = UserRepository(userDao)

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
            if (isChecked) {
                // Enable notifications
                enableNotifications()
            } else {
                // Disable notifications
                disableNotifications()
            }
        }

        return root
    }

    private fun loadUserProfile() {
        GlobalScope.launch(Dispatchers.Main) {
            val currentUser = getCurrentUser() // Fetch current user from Room
            currentUser?.let {
                usernameEditText.setText(it.username)
                passwordEditText.setText(it.password)
            }
        }
    }

    private suspend fun getCurrentUser(): User? {
        // get current user's credentials
        return userRepository.checkUser("current_username", "current_password")
    }

    private fun updateUserProfile(username: String, password: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val rowsUpdated = userRepository.updateUser(User(0, username, password))
            if (rowsUpdated > 0) {
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                }
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadNotificationPreferences() {
        GlobalScope.launch(Dispatchers.Main) {
            val currentUser = getCurrentUser()
            currentUser?.let {
                val notificationsEnabled = userRepository.areNotificationsEnabled(it.id)
                enableNotificationsSwitch.isChecked = notificationsEnabled
            }
        }
    }

    private fun enableNotifications() {
        GlobalScope.launch(Dispatchers.IO) {
            val currentUser = getCurrentUser()
            currentUser?.let {
                val rowsUpdated = userRepository.enableNotifications(it.id)
                if (rowsUpdated > 0) {
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Notifications enabled", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Failed to enable notifications", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun disableNotifications() {
        GlobalScope.launch(Dispatchers.IO) {
            val currentUser = getCurrentUser()
            currentUser?.let {
                val rowsUpdated = userRepository.disableNotifications(it.id)
                if (rowsUpdated > 0) {
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Notifications disabled", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Failed to disable notifications", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
