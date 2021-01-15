package fr.isen.mollinari.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import fr.isen.mollinari.androiderestaurant.category.CategoryActivity
import fr.isen.mollinari.androiderestaurant.databinding.ActivityHomeBinding

private lateinit var binding: ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.starters.setOnClickListener {
            redirectToCategory(R.string.home_starters)
        }

        binding.mainDishes.setOnClickListener {
            redirectToCategory(R.string.home_main_dishes)
        }

        binding.deserts.setOnClickListener {
            redirectToCategory(R.string.home_desert)
        }
    }

    private fun redirectToCategory(@StringRes categoryRes: Int) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CATEGORY_NAME, getString(categoryRes))
        startActivity(intent)
    }

    override fun onStop() {
        super.onStop()
        Log.d("HomeActivity", "Vous stoppez la page home")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeActivity", "Vous quittez la page home")
    }

    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }
}