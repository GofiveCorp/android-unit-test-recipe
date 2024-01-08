package com.example.sampleapp.domain

import com.example.sampleapp.data.model.Category
import com.example.sampleapp.data.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

fun interface GetCategoriesUseCase {
    fun execute(): Flow<Result<List<Category>>>
}
class GetCategoriesUseCaseImpl(private val recipeRepository: RecipeRepository):
    GetCategoriesUseCase {

    override fun execute(): Flow<Result<List<Category>>> {
        return recipeRepository.getCategories()
    }
}