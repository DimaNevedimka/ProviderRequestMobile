package ru.ssau.providerrequest.presenter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.ssau.providerrequest.data.repository.NetworkRepository
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val networkRepository: NetworkRepository) : ViewModel(){

    val nextScreen = MutableLiveData<Boolean>(false)

    val error = MutableLiveData<Throwable>()

    fun authenticate(login: String, password: String){
        viewModelScope.launch {
            kotlin.runCatching {
                networkRepository.authenticate(login, password)
            }.onSuccess {
                nextScreen.value = it.auth
            }.onFailure {
                error.value = it
            }
        }
    }
}