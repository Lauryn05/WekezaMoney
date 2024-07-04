package com.cns.wekezamoney

import DBHelper
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cns.wekezamoney.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var dbHelper: DBHelper

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.btnDone.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Insert user into database
            val userId = dbHelper.addUser(username, password)

            if (userId != -1L) {
                // Registration successful, handle accordingly
                finish() // Close registration activity
            } else {
                // Handle registration failure
                binding.tvError.text = "Registration failed"
            }
        }
    }
}
