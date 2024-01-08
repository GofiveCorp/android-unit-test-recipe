package com.example.sampleapp

import com.example.sampleapp.data.api.RecipeService
import com.example.sampleapp.data.model.CategoriesResponse
import com.example.sampleapp.data.model.Category
import com.example.sampleapp.data.repository.RecipeRepository
import com.example.sampleapp.data.repository.RecipeRepositoryImpl
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import io.mockk.coEvery
import io.mockk.mockk
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RecipeRepositoryUnitTest {

    private val api = mockk<RecipeService>()
    private lateinit var repository: RecipeRepository

    @Before
    fun setup() {
        repository = RecipeRepositoryImpl(api)
    }

    @Test
    fun `Given response success When getCategories Then result success`() = runBlocking {
        coEvery {
            api.getCategories()
        } returns Response.success(
            CategoriesResponse(categories)
        )

        repository.getCategories()
            .collect {
                TestCase.assertTrue(it.isSuccess)
            }
    }

    @Test
    fun `Given response success When getCategories Then result null`() = runBlocking {
        coEvery {
            api.getCategories()
        } returns Response.success(null)

        repository.getCategories()
            .collect {
                it.fold(
                    onFailure = { throwable ->
                        TestCase.assertTrue(throwable.message == "Response body or contents is null")
                    },
                    onSuccess = {
                    }
                )
            }
    }

    @Test
    fun `Given response error When getCategories Then result error`() = runBlocking {
        coEvery {
            api.getCategories()
        } returns Response.error(404, "".toResponseBody())

        repository.getCategories()
            .collect {
                it.fold(
                    onFailure = { throwable ->
                        TestCase.assertTrue(throwable.message == "404")
                    },
                    onSuccess = {
                    }
                )
            }
    }

    private val categories = listOf(
        Category("1", "Category1", "thumb1.jpg", "Description1"),
        Category("2", "Category2", "thumb2.jpg", "Description2")
    )
}