package com.oblessing.filteringmatches.core

import android.app.Application
import com.airbnb.mvrx.Mavericks
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() = super.onCreate().also {
        Mavericks.initialize(this)
    }
}