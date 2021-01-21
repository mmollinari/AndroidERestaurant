package fr.isen.mollinari.androiderestaurant.basket

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import fr.isen.mollinari.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.mollinari.androiderestaurant.detail.DetailActivity
import fr.isen.mollinari.androiderestaurant.model.Basket
import java.io.File

private lateinit var binding: ActivityBasketBinding

class BasketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonFile = File(cacheDir.absolutePath + DetailActivity.BASKET_FILE)

        if (jsonFile.exists()) {
            val dataJson = jsonFile.readText()
            if (dataJson.isNotEmpty()) {
                val basket = GsonBuilder().create().fromJson(dataJson, Basket::class.java)
                displayBasket(basket)
            }
        }
    }

    private fun displayBasket(basket: Basket) {
        binding.basketList.layoutManager = LinearLayoutManager(this)
        binding.basketList.adapter = BasketAdapter(basket.items) {
            basket.items.remove(it)
            updateBasket(basket)
        }
    }

    private fun updateBasket(basket: Basket) {
        saveDishCountInPref(basket)
        File(cacheDir.absolutePath + DetailActivity.BASKET_FILE).writeText(
            GsonBuilder().create().toJson(basket)
        )
    }

    private fun saveDishCountInPref(basket: Basket) {
        val count = basket.items.sumOf { it.quantity }
        val editor = getSharedPreferences(DetailActivity.APP_PREFS, Context.MODE_PRIVATE).edit()
        editor.putInt(DetailActivity.BASKET_COUNT, count)
        editor.apply()
    }
}