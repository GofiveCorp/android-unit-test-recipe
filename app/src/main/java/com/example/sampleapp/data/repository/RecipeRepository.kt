package com.example.sampleapp.data.repository

import com.example.sampleapp.data.api.RecipeService
import com.example.sampleapp.data.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface RecipeRepository {
    fun getCategories(): Flow<Result<List<Category>>>
}

class RecipeRepositoryImpl(private val recipeService: RecipeService) : RecipeRepository {
    override fun getCategories(): Flow<Result<List<Category>>> = flow {
        try {
            val response = recipeService.getCategories()

            if (response.isSuccessful) {
                response.body()?.let { body ->
                    emit(Result.success(body.categories))
                } ?: run {
                    emit(Result.failure(IllegalStateException("Response body or contents is null")))
                }
            } else {
                emit(Result.failure(IllegalStateException(response.code().toString())))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

}
