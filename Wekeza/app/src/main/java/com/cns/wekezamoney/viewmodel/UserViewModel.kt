package com.cns.wekezamoney.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cns.wekezamoney.model.User
import com.cns.wekezamoney.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun insertUser(user: User, onResult: (Long) -> Unit) {
        this.viewModelScope.launch {
            val result = userRepository.insertUser(user)
            onResult(result)
        }
    }

    fun checkUser(username: String, password: String, onResult: (User?) -> Unit) {
        this.viewModelScope.launch {
            val user = userRepository.checkUser(username, password)
            onResult(user)
        }
    }

    class Factory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
