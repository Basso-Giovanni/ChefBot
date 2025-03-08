package com.example.chefbot.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.chefbot.MealResponse
import com.example.chefbot.R
import com.example.chefbot.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var mealCard: CardView
    private lateinit var mealImage: ImageView
    private lateinit var mealTitle: TextView
    private lateinit var mealCategory: TextView
    private lateinit var detailsButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        searchEditText = view.findViewById(R.id.searchEditText)
        searchButton = view.findViewById(R.id.searchButton)
        mealCard = view.findViewById(R.id.mealCard)
        mealImage = view.findViewById(R.id.mealImage)
        mealTitle = view.findViewById(R.id.mealTitle)
        mealCategory = view.findViewById(R.id.mealCategory)
        detailsButton = view.findViewById(R.id.detailsButton)

        // Nascondiamo la card all'avvio
        mealCard.visibility = View.GONE

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            if (query.isNotEmpty()) {
                searchMeal(query)
            }
        }

        return view
    }

    private fun searchMeal(query: String) {
        RetrofitClient.instance.searchMeal(query).enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    if (!meals.isNullOrEmpty()) {
                        val meal = meals[0] // Prendiamo il primo risultato
                        mealTitle.text = meal.strMeal
                        mealCategory.text = "Categoria: ${meal.strCategory}"

                        // Carichiamo l'immagine con Glide
                        Glide.with(requireContext()).load(meal.strMealThumb).into(mealImage)

                        // Mostriamo la card con l'animazione
                        mealCard.visibility = View.VISIBLE

                        // Gestione del click sul bottone "Vedi Dettagli"
                        detailsButton.setOnClickListener {
                            Toast.makeText(requireContext(), "Mostra dettagli di ${meal.strMeal}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Nessuna ricetta trovata!", Toast.LENGTH_SHORT).show()
                        mealCard.visibility = View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Errore nella ricerca!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
