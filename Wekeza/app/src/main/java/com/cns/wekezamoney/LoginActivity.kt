package com.cns.wekezamoney

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

            userViewModel.checkUser(username, password) { user ->
                if (user != null) {
                    // Login successful, proceed to main activity or dashboard
                    // val intent = Intent(this, MainActivity::class.java)
                    // startActivity(intent)
                    finish() // Close login activity
                } else {
                    // Handle incorrect username/password scenario
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = "Invalid credentials"
                }
            }
        }
    }
}
