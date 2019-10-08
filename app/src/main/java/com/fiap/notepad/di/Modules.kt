package com.fiap.notepad.di

import com.fiap.notepad.api.NotepadService
import com.fiap.notepad.repository.NotepadRepository
import com.fiap.notepad.repository.NotepadRepositoryImpl
import com.fiap.notepad.constants.IntegrationsConstants.URL_NOTEPAD_API
import com.fiap.notepad.ui.list.ListViewModel
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.fiap.notepad.ui.edit.EditViewModel
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel { ListViewModel(get()) }
    viewModel { EditViewModel(get()) }
}

val repositoryModule = module {
    single<NotepadRepository> { NotepadRepositoryImpl(get()) }
}

private fun createNetworkClient(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(URL_NOTEPAD_API)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
private fun createOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
    return builder.build()
}

val networkModule = module {
    single { createOkhttpClient() }
    single { createNetworkClient(get(), get(named("baseURL"))).create(NotepadService::class.java) }
    single(named("baseURL")) { URL_NOTEPAD_API }
}

private fun createNetworkClient(okHttpClient: OkHttpClient, baseURL: String): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun createOkhttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .addNetworkInterceptor(StethoInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
    return builder.build()
}
