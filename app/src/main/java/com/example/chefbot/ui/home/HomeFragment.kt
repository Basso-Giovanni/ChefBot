package com.example.chefbot.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chefbot.CountryAdapter
import com.example.chefbot.Meal
import com.example.chefbot.MealResponse
import com.example.chefbot.R
import com.example.chefbot.RetrofitClient
import com.example.chefbot.ui.home.CategoryAdapter
import com.example.chefbot.ui.home.MealAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var mealRecyclerView: RecyclerView
    private lateinit var mealAdapter: MealAdapter
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var countryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var countryAdapter: CountryAdapter
    private var mealList: MutableList<Meal> = mutableListOf()

    private val categories = listOf("Beef", "Chicken", "Dessert", "Pasta", "Seafood", "Vegan")
    private val countries = listOf("Italian", "Mexican", "French", "Chinese", "Japanese")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        searchEditText = view.findViewById(R.id.searchEditText)
        searchButton = view.findViewById(R.id.searchButton)
        mealRecyclerView = view.findViewById(R.id.mealRecyclerView)
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView)
        countryRecyclerView = view.findViewById(R.id.countryRecyclerView)

        mealRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        mealAdapter = MealAdapter(requireContext(), mealList)
        mealRecyclerView.adapter = mealAdapter

        categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter(requireContext(), categories) { category ->
            filterByCategory(category)
        }
        categoryRecyclerView.adapter = categoryAdapter

        countryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        countryAdapter = CountryAdapter(requireContext(), countries) { country ->
            filterByCountry(country)
        }
        countryRecyclerView.adapter = countryAdapter

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            if (query.isNotEmpty()) {
                searchMeal(query)
            }
        }

        return view
    }

    // Funzione per la ricerca di ricette in base alla query
    private fun searchMeal(query: String) {
        RetrofitClient.instance.searchMeal(query).enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    if (!meals.isNullOrEmpty()) {
                        mealList.clear()
                        val convertedMeals = meals.map { RetrofitClient.convertToMeal(it) }
                        mealList.clear()
                        mealList.addAll(convertedMeals)

                        mealAdapter.notifyDataSetChanged()
                    } else {
                        // Messaggio di errore se non ci sono risultati
                        Toast.makeText(requireContext(), "Nessuna ricetta trovata!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Errore nella ricerca!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Funzione per filtrare per categoria
    private fun filterByCategory(category: String) {
        RetrofitClient.instance.filterByCategory(category).enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    if (!meals.isNullOrEmpty()) {
                        mealList.clear()
                        val convertedMeals = meals.map { RetrofitClient.convertToMeal(it) }
                        mealList.clear()
                        mealList.addAll(convertedMeals)

                        mealAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Errore nel filtraggio per categoria!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Funzione per filtrare per paese
    private fun filterByCountry(country: String) {
        RetrofitClient.instance.filterByCountry(country).enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    if (!meals.isNullOrEmpty()) {
                        mealList.clear()
                        val convertedMeals = meals.map { RetrofitClient.convertToMeal(it) }
                        mealList.clear()
                        mealList.addAll(convertedMeals)

                        mealAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Errore nel filtraggio per nazione!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
