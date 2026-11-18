package com.example.bookflow

import android.app.Application
import com.example.bookflow.di.AppContainer

class BookFlowApplication : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()

        container = AppContainer(this)
    }
}