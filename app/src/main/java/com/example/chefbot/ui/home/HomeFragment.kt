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
import com.example.chefbot.Meal
import com.example.chefbot.MealResponse
import com.example.chefbot.R
import com.example.chefbot.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var mealRecyclerView: RecyclerView
    private lateinit var mealAdapter: MealAdapter
    private var mealList: MutableList<Meal> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        searchEditText = view.findViewById(R.id.searchEditText)
        searchButton = view.findViewById(R.id.searchButton)
        mealRecyclerView = view.findViewById(R.id.mealRecyclerView)

        mealRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        mealAdapter = MealAdapter(requireContext(), mealList)
        mealRecyclerView.adapter = mealAdapter

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
                        mealList.clear()
                        mealList.addAll(meals)
                        mealAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(requireContext(), "Nessuna ricetta trovata!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Errore nella ricerca!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
