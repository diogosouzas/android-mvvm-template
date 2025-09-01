package com.arch.template.di

import android.content.Context
import androidx.room.Room
import com.arch.template.data.local.dao.UserDao
import com.arch.template.data.local.database.TemplateDatabase
import com.arch.template.data.remote.api.UserApiService
import com.arch.template.data.repository.DefaultUserRepository
import com.arch.template.data.repository.UserRepository
import com.arch.template.domain.usecase.GetUsersUseCase
import com.arch.template.domain.usecase.SyncUsersUseCase
import com.arch.template.domain.usecase.UserUseCases
import com.arch.template.ui.home.HomeViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import timber.log.Timber

val appModule = module {

    // JSON
    single {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    // OkHttpClient
    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor { message ->
                    Timber.tag("OkHttp").d(message)
                }.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // Example API
            .client(get())
            .addConverterFactory(
                get<Json>().asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    // API Service
    single<UserApiService> {
        get<Retrofit>().create(UserApiService::class.java)
    }

    // Database
    single {
        Room.databaseBuilder(
            get<Context>(),
            TemplateDatabase::class.java,
            TemplateDatabase.DATABASE_NAME
        ).build()
    }

    // DAO
    single<UserDao> {
        get<TemplateDatabase>().userDao()
    }

    // Repository
    single<UserRepository> {
        DefaultUserRepository(get(), get())
    }

    // Use Cases
    factory {
        GetUsersUseCase(get())
    }

    factory {
        SyncUsersUseCase(get())
    }

    factory {
        UserUseCases(get(), get())
    }

    // ViewModels
    viewModel {
        HomeViewModel(get())
    }
}
