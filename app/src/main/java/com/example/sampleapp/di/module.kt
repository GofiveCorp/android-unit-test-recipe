package com.example.sampleapp.di

import com.example.sampleapp.data.api.RecipeService
import com.example.sampleapp.data.repository.RecipeRepository
import com.example.sampleapp.data.repository.RecipeRepositoryImpl
import com.example.sampleapp.domain.GetCategoriesUseCase
import com.example.sampleapp.domain.GetCategoriesUseCaseImpl
import com.example.sampleapp.presentation.viewModel.MainViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<Retrofit>(named("recipeRetrofit")) {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<RecipeService> {
        get<Retrofit>(named("recipeRetrofit")).create(RecipeService::class.java)
    }

    single<RecipeRepository> { RecipeRepositoryImpl(get()) }
    factory<GetCategoriesUseCase> { GetCategoriesUseCaseImpl(get()) }
    viewModel { MainViewModel(get()) }
}
