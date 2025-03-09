package com.example.chefbot

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val instance: MealApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApiService::class.java)
    }

    fun convertToMeal(mealData: Map<String, Any?>): Meal {
        val ingredients = mutableListOf<Ingredient>()

        for (i in 1..20) {
            val ingredientName = mealData["strIngredient$i"] as? String
            val measure = mealData["strMeasure$i"] as? String

            if (!ingredientName.isNullOrBlank() && !measure.isNullOrBlank()) {
                ingredients.add(Ingredient(ingredientName, measure))
            }
        }

        return Meal(
            idMeal = mealData["idMeal"] as? String ?: "",  // Se null, assegna stringa vuota
            strMeal = mealData["strMeal"] as? String ?: "Nome non disponibile",
            strCategory = mealData["strCategory"] as? String ?: "Categoria sconosciuta",
            strInstructions = mealData["strInstructions"] as? String ?: "Istruzioni non disponibili",
            strMealThumb = mealData["strMealThumb"] as? String ?: "",  // Se null, assegna stringa vuota
            ingredients = ingredients
        )
    }
}
