package com.fiap.notepad

import android.app.Application
import com.facebook.stetho.Stetho
import com.fiap.notepad.di.networkModule
import com.fiap.notepad.di.repositoryModule
import com.fiap.notepad.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NotepadApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidLogger()
            androidContext(this@NotepadApplication)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    repositoryModule
                )
            )
        }
    }
}