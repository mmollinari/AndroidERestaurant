package fr.isen.mollinari.androiderestaurant.category

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.mollinari.androiderestaurant.detail.DetailActivity
import fr.isen.mollinari.androiderestaurant.HomeActivity.Companion.CATEGORY_NAME
import fr.isen.mollinari.androiderestaurant.R
import fr.isen.mollinari.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.mollinari.androiderestaurant.model.Dish

private lateinit var binding: ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(CATEGORY_NAME)
        binding.categoryTitle.text = title

        val dishes = resources.getStringArray(R.array.planets_array).map { Dish(it, 12.50, defaultPizzasUrl()) }

        binding.categoryList.layoutManager = LinearLayoutManager(this)
        binding.categoryList.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        binding.categoryList.adapter = CategoryAdapter(dishes) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("Dish", it)
            startActivity(intent)
        }

    }

    private fun defaultPizzasUrl() =
        listOf(
            "https://www.demotivateur.fr/images-buzz/188912/pizza-napolitaine-600x400.jpg",
            "https://www.demotivateur.fr/images-buzz/188912/pizza-merguez-600x400.jpg",
            "https://www.demotivateur.fr/images-buzz/188912/pizza-blanche-pommedeterre-thym-600x400.jpg",
            "https://www.demotivateur.fr/images-buzz/188912/pizza-regina-600x400.jpg",
            "https://www.demotivateur.fr/images-buzz/188912/pizza-marinara-600x400.jpg",
            "https://www.demotivateur.fr/images-buzz/188912/pizza-calzone-600x400.jpg"
        )
}