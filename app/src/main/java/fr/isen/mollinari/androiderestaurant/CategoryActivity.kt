package fr.isen.mollinari.androiderestaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.mollinari.androiderestaurant.HomeActivity.Companion.CATEGORY_NAME
import fr.isen.mollinari.androiderestaurant.databinding.ActivityCategoryBinding

private lateinit var binding: ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(CATEGORY_NAME)
        binding.categoryTitle.text = title
    }
}