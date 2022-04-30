package ru.ssau.providerrequest.presenter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ssau.providerrequest.data.repository.NetworkRepository

class DetailViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

    val nextScreen = MutableLiveData<Boolean>(false)

    val error = MutableLiveData<Throwable>()

    fun updateRequest(id: Int, status: String, date_time: String, comment: String, account_id: String) {
        viewModelScope.launch {
            runCatching {
                networkRepository.updateRequest(
                    id = id,
                    status = status,
                    date_time = date_time,
                    comment = comment,
                    account_id = account_id
                )
            }.onSuccess { nextScreen.value = true }
                .onFailure { error.value = it }
        }
    }
}