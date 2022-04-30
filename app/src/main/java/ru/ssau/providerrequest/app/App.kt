package ru.ssau.providerrequest.app

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import ru.ssau.providerrequest.di.appModule
import ru.ssau.providerrequest.di.domainModule
import ru.ssau.providerrequest.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("5798593c-66e7-44e8-b6a5-04e51f538f0a");
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, domainModule, networkModule))
        }
    }
}