package com.example.chefbot

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DettagliRicettaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dettagli_ricetta)

        val mealTitle: TextView = findViewById(R.id.dettagliMealTitle)
        val mealCategory: TextView = findViewById(R.id.dettagliMealCategory)
        val mealInstructions: TextView = findViewById(R.id.dettagliMealInstructions)
        val mealImage: ImageView = findViewById(R.id.dettagliMealImage)

        // Recuperiamo i dati passati dall'Intent
        val title = intent.getStringExtra("MEAL_TITLE")
        val category = intent.getStringExtra("MEAL_CATEGORY")
        val instructions = intent.getStringExtra("MEAL_INSTRUCTIONS")
        val imageUrl = intent.getStringExtra("MEAL_IMAGE")

        // Impostiamo i dati nella UI
        mealTitle.text = title
        mealCategory.text = "Categoria: $category"
        mealInstructions.text = instructions

        // Carichiamo l'immagine con Glide
        Glide.with(this).load(imageUrl).into(mealImage)
    }
}
