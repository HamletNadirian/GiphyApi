package android.rest.api.composeapi

import android.app.Application
import android.rest.api.composeapi.data.AppContainer
import android.rest.api.composeapi.data.DefaultAppContainer

class GifsApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}