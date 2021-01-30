package fr.isen.mollinari.androiderestaurant.category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.mollinari.androiderestaurant.BaseActivity
import fr.isen.mollinari.androiderestaurant.HomeActivity.Companion.CATEGORY_NAME
import fr.isen.mollinari.androiderestaurant.R
import fr.isen.mollinari.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.mollinari.androiderestaurant.detail.DetailActivity
import fr.isen.mollinari.androiderestaurant.model.Dish
import fr.isen.mollinari.androiderestaurant.model.MenuResult
import org.json.JSONObject

private lateinit var binding: ActivityCategoryBinding

class CategoryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryTitle = intent.getStringExtra(CATEGORY_NAME) ?: ""
        binding.categoryTitle.text = categoryTitle

        loadDishesFromCategory(categoryTitle)
    }

    private fun loadDishesFromCategory(category: String) {
        val url = getString(R.string.server_domain) + "menu"

        val jsonData = JSONObject()
        jsonData.put("id_shop", "1")

        val stringRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonData, { response ->
                val menu = GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
                menu.data.firstOrNull { it.name == category }?.dishes?.let {
                    displayDishes(it)
                } ?: run {
                    binding.categoryLoading.visibility = View.GONE
                    binding.categoryErrorMessage.text = getString(R.string.category_error_empty)
                }
            },
            {
                Log.d("CategoryActivity", "That didn't work! ${it}")
                binding.categoryLoading.visibility = View.GONE
                binding.categoryErrorMessage.text = getString(R.string.category_error_server, category)
            })

        Volley.newRequestQueue(this).add(stringRequest)
    }

    private fun displayDishes(dishes: List<Dish>) {
        binding.categoryLoading.visibility = View.GONE
        binding.categoryList.layoutManager = LinearLayoutManager(this)
        binding.categoryList.adapter = CategoryAdapter(dishes) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("dish", it)
            startActivity(intent)
        }
    }
}