package com.cns.wekezamoney

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cns.wekezamoney.database.UserDatabase
import com.cns.wekezamoney.databinding.ActivityLoginBinding
import com.cns.wekezamoney.repository.UserRepository
import com.cns.wekezamoney.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.Factory(UserRepository(UserDatabase.getDatabase(this).userDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDone.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = getString(R.string.fill_all_fields)
            } else {
                userViewModel.checkUser(username, password) { user ->
                    if (user != null) {
                        // Save user ID to SharedPreferences
                        saveCurrentUserId(user.id)

                        // Login successful, proceed to DashboardActivity
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finish() // Close login activity
                    } else {
                        // Handle incorrect username/password scenario
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = getString(R.string.invalid_credentials)
                    }
                }
            }
        }
    }

    private fun saveCurrentUserId(userId: Long) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("currentUserId", userId)
        editor.apply()
    }
}
