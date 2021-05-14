package com.oreilly.hellokotlin.ui

import androidx.lifecycle.*
import com.oreilly.hellokotlin.astro.AstroApi
import com.oreilly.hellokotlin.astro.AstroResult
import com.oreilly.hellokotlin.db.User
import com.oreilly.hellokotlin.db.UserRepository
import kotlinx.coroutines.launch

class WelcomeViewModel(private val repository: UserRepository) : ViewModel() {
    val astroResult: LiveData<AstroResult> = liveData {
        val result = AstroApi.retrofitService.getAstroResult()
        emit(result)
    }

    val allUsers: LiveData<List<User>> = repository.allUsers.asLiveData()

    fun insert(name: String) = viewModelScope.launch {
        repository.insertUser(name)
    }

    fun delete(user: User) = viewModelScope.launch {
        repository.deleteUser(user)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAllUsers()
    }
}

class WelcomeViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}