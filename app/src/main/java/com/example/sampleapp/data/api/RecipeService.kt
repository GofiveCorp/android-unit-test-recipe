package com.example.sampleapp.data.api

import com.example.sampleapp.data.model.CategoriesResponse
import retrofit2.Response
import retrofit2.http.GET


interface RecipeService{
    @GET("categories.php")
    suspend fun getCategories(): Response<CategoriesResponse>
}