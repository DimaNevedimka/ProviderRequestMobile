package ru.ssau.providerrequest.di

import ru.ssau.providerrequest.data.repository.NetworkRepository
import org.koin.dsl.module

val domainModule = module {

    single<NetworkRepository> {
        NetworkRepository(api = get())
    }
}