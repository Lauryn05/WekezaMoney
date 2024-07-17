package com.cns.wekezamoney

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cns.wekezamoney.database.UserDatabase
import com.cns.wekezamoney.databinding.ActivityRegisterBinding
import com.cns.wekezamoney.model.User
import com.cns.wekezamoney.repository.UserRepository
import com.cns.wekezamoney.viewmodel.UserViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.Factory(UserRepository(UserDatabase.getDatabase(this).userDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDone.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = getString(R.string.fill_all_fields)
            } else {
                // Insert user into database
                val user = User(0, username, password)
                userViewModel.insertUser(user) { userId ->
                    if (userId != -1L) {
                        // Registration successful, navigate to LoginActivity
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish() // Close registration activity
                    } else {
                        // Handle registration failure
                        binding.tvError.text = getString(R.string.registration_failed)
                    }
                }
            }
        }
    }
}
