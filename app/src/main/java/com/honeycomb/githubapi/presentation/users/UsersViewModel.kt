package com.honeycomb.githubapi

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honeycomb.githubapi.domain.common.BaseResult
import com.honeycomb.githubapi.domain.users.entity.UsersEntity
import com.honeycomb.githubapi.domain.users.usecase.UsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(application: Application,private val usersUseCase: UsersUseCase) : ViewModel(){

    var currentUserName = ""
    private val state = MutableStateFlow<UsersState>(UsersState.Init)
    val mState: StateFlow<UsersState> get() = state

    private val products = MutableStateFlow<List<UsersEntity>>(mutableListOf())
    val mUsers: StateFlow<List<UsersEntity>> get() = products

    private fun setLoading(){
        state.value = UsersState.IsLoading(true)
    }

    private fun hideLoading(){
        state.value = UsersState.IsLoading(false)
    }

    private fun showToast(message: String){
        state.value = UsersState.ShowToast(message)
    }

    fun fetchAllSports(userName : String){
        viewModelScope.launch {
            usersUseCase.execute(userName)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.toString())
                }
                .collect { result ->
                    hideLoading()
                    when(result){
                        is BaseResult.Success -> {
                            products.value = result.data
                        }
                        is BaseResult.Error -> {
                            showToast(result.toString())
                        }
                    }

                }
        }
    }
}

sealed class UsersState {
    object Init : UsersState()
    data class IsLoading(val isLoading: Boolean) : UsersState()
    data class ShowToast(val message : String) : UsersState()
}