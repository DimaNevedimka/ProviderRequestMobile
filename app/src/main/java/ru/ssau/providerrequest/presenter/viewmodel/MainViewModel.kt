package ru.ssau.providerrequest.presenter.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ssau.providerrequest.data.entity.Data
import ru.ssau.providerrequest.data.entity.RequestsResponse
import ru.ssau.providerrequest.data.mappers.toDto
import ru.ssau.providerrequest.data.repository.NetworkRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

    var listRequests = MutableLiveData<List<Data>>()

    init {
        loadAllData()
    }

    fun loadAllData() {
        viewModelScope.launch {
            runCatching { networkRepository.allRequests() }
                .onSuccess { listRequests.value = it.data.toDto() }
                .onFailure { Log.d("LOGGER", it.toString()) }
        }
    }
}