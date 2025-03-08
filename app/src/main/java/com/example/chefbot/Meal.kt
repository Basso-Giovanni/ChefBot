package com.example.chefbot

data class Meal (
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strInstructions: String,
    val strMealThumb: String
)

data class MealResponse (
    val meals: List<Meal>
)
