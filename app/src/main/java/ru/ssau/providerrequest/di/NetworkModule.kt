package ru.ssau.providerrequest.di

import com.google.gson.GsonBuilder
import ru.ssau.providerrequest.data.service.NetworkServiceApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideRetrofit() }
    single { provideNetworkServiceApi( retrofit = get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://ssau.roundcloud.ru")
        .addConverterFactory(GsonConverterFactory.create(provideGSON()))
        .build()
}

fun provideNetworkServiceApi(retrofit: Retrofit): NetworkServiceApi =
    retrofit.create(NetworkServiceApi::class.java)

fun provideGSON() = GsonBuilder().setLenient().create()