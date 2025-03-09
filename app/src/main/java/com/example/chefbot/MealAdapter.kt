package com.example.chefbot.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chefbot.DettagliRicettaActivity
import com.example.chefbot.Meal
import com.example.chefbot.R

class MealAdapter(private val context: Context, private val mealList: List<Meal>) :
    RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    class MealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mealImage: ImageView = view.findViewById(R.id.mealImage)
        val mealTitle: TextView = view.findViewById(R.id.mealTitle)
        val mealCategory: TextView = view.findViewById(R.id.mealCategory)
        val detailsButton: Button = view.findViewById(R.id.detailsButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = mealList[position]
        holder.mealTitle.text = meal.strMeal
        holder.mealCategory.text = "Categoria: ${meal.strCategory}"

        Glide.with(context).load(meal.strMealThumb).into(holder.mealImage)

        holder.detailsButton.setOnClickListener {
            val intent = Intent(context, DettagliRicettaActivity::class.java).apply {
                putExtra("MEAL_TITLE", meal.strMeal)
                putExtra("MEAL_CATEGORY", meal.strCategory)
                putExtra("MEAL_INSTRUCTIONS", meal.strInstructions)
                putExtra("MEAL_IMAGE", meal.strMealThumb)
                putParcelableArrayListExtra("MEAL_INGREDIENTS", ArrayList(meal.ingredients))
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}
