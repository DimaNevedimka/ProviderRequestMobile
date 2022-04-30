package ru.ssau.providerrequest.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.ssau.providerrequest.presenter.viewmodel.AuthenticationViewModel
import ru.ssau.providerrequest.presenter.viewmodel.DetailViewModel
import ru.ssau.providerrequest.presenter.viewmodel.MainViewModel

val appModule = module {

    viewModel<AuthenticationViewModel> {
        AuthenticationViewModel(networkRepository = get())
    }

    viewModel<MainViewModel> {
        MainViewModel(networkRepository = get())
    }

    viewModel<DetailViewModel> {
        DetailViewModel(networkRepository = get())
    }
}