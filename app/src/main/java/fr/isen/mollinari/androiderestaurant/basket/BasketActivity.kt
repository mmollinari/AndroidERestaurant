package fr.isen.mollinari.androiderestaurant.basket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import fr.isen.mollinari.androiderestaurant.R
import fr.isen.mollinari.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.mollinari.androiderestaurant.detail.DetailActivity
import fr.isen.mollinari.androiderestaurant.model.Basket
import fr.isen.mollinari.androiderestaurant.order.OrderActivity
import fr.isen.mollinari.androiderestaurant.sign.RegisterActivity
import fr.isen.mollinari.androiderestaurant.sign.RegisterActivity.Companion.USER_ID
import java.io.File

private lateinit var binding: ActivityBasketBinding

class BasketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonFile = File(cacheDir.absolutePath + DetailActivity.BASKET_FILE)

        var hasItemsInBasket = false
        if (jsonFile.exists()) {
            val dataJson = jsonFile.readText()
            if (dataJson.isNotEmpty()) {
                val basket = GsonBuilder().create().fromJson(dataJson, Basket::class.java)
                if (basket.items.isNotEmpty()) {
                    hasItemsInBasket = true
                    displayBasket(basket)
                }
            }
        }
        if (!hasItemsInBasket){
            displayEmptyBasket()
        }

        val hasUserIdSaved = getSharedPreferences(DetailActivity.APP_PREFS, Context.MODE_PRIVATE).contains(USER_ID)

        binding.order.setOnClickListener {
            if (hasUserIdSaved) {
                startActivity(Intent(this, OrderActivity::class.java))
            } else {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    private fun displayEmptyBasket() {
        binding.order.isVisible = false
        binding.basketErrorMessage.isVisible = true
        binding.basketErrorMessage.text = getString(R.string.basket_error_empty)
    }

    private fun displayBasket(basket: Basket) {
        binding.basketList.layoutManager = LinearLayoutManager(this)
        binding.basketList.adapter = BasketAdapter(basket.items) {
            basket.items.remove(it)
            updateBasket(basket)
            invalidateOptionsMenu()
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