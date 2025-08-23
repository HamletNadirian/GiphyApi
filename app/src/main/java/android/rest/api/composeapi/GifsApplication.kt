package android.rest.api.composeapi

import android.app.Application
import android.rest.api.composeapi.data.AppContainer
import android.rest.api.composeapi.data.DefaultAppContainer

class GifsApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}