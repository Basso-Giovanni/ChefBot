package com.example.chefbot

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("search.php")
    fun searchMeal(@Query("s") mealName: String): Call<MealResponse>

    @GET("random.php")
    fun getRandomMeal(): Call<MealResponse>
}
