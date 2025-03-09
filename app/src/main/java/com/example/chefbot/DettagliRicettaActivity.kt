package com.example.chefbot

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DettagliRicettaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettagli_ricetta)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mealTitle: TextView = findViewById(R.id.dettagliMealTitle)
        val mealCategory: TextView = findViewById(R.id.dettagliMealCategory)
        val mealInstructions: TextView = findViewById(R.id.dettagliMealInstructions)
        val mealImage: ImageView = findViewById(R.id.dettagliMealImage)
        val mealIngredients: TextView = findViewById(R.id.dettagliMealIngredients)

        val title = intent.getStringExtra("MEAL_TITLE")
        val category = intent.getStringExtra("MEAL_CATEGORY")
        val instructions = intent.getStringExtra("MEAL_INSTRUCTIONS")
        val imageUrl = intent.getStringExtra("MEAL_IMAGE")
        val ingredients = intent.getParcelableArrayListExtra<Ingredient>("MEAL_INGREDIENTS") ?: emptyList()

        mealTitle.text = title
        mealCategory.text = "Categoria: $category"
        mealInstructions.text = instructions
        Glide.with(this).load(imageUrl).into(mealImage)

        // Mostriamo gli ingredienti
        mealIngredients.text = ingredients.joinToString("\n") { "${it.name}: ${it.measure}" }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
