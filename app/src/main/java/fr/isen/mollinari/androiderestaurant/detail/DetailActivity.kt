package fr.isen.mollinari.androiderestaurant.detail

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import fr.isen.mollinari.androiderestaurant.BaseActivity
import fr.isen.mollinari.androiderestaurant.R
import fr.isen.mollinari.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.mollinari.androiderestaurant.model.Basket
import fr.isen.mollinari.androiderestaurant.model.Dish
import fr.isen.mollinari.androiderestaurant.model.ItemBasket
import java.io.File

private lateinit var binding: ActivityDetailBinding

class DetailActivity : BaseActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)

        initDetail()
    }

    private fun initDetail() {
        val dish = intent.getSerializableExtra("Dish") as Dish
        binding.titleDetail.text = dish.title
        binding.addToBasket.text = getString(R.string.detail_global_amount, dish.getFormattedPrice())

        binding.ingredients.text = dish.ingredients.joinToString(", ") { it.name }

        var quantity = 1
        binding.quantity.text = "1"
        binding.more.setOnClickListener {
            if (quantity <= 1000) {
                quantity++
                updateAmountFromQuantity(quantity, dish.getPrice())
            }
        }
        binding.less.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateAmountFromQuantity(quantity, dish.getPrice())
            }
        }

        binding.sliderPhotos.adapter = DishPhotoAdapter(this, defaultPizzasUrl())

        binding.addToBasket.setOnClickListener {
            Snackbar.make(binding.root, "Votre plat a bien été ajouté à votre panier.", Snackbar.LENGTH_LONG).show()
            saveInBasket(quantity, dish)
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

    private fun updateAmountFromQuantity(quantity: Int, amount: Double) {
        binding.quantity.text = quantity.toString()
        binding.addToBasket.text =
            getString(R.string.detail_global_amount, "${(amount * quantity)}€")
    }

    private fun saveInBasket(quantity: Int, dish: Dish) {
        val jsonFile = File(cacheDir.absolutePath + BASKET_FILE)

        if (jsonFile.exists()) {
            val dataJson = jsonFile.readText()
            if (dataJson.isNotEmpty()) {
                val basket = GsonBuilder().create().fromJson(dataJson, Basket::class.java)
                updateOrCreateBasket(basket, quantity, dish)
            } else {
                updateOrCreateBasket(null, quantity, dish)
            }
        } else {
            updateOrCreateBasket(null, quantity, dish)
        }
    }

    private fun updateOrCreateBasket(basket: Basket?, quantity: Int, dish: Dish) {
        val newBasket = basket ?: Basket(mutableListOf())
        newBasket.items.add(ItemBasket(quantity, dish))

        saveDishCountInPref(newBasket)

        File(cacheDir.absolutePath + BASKET_FILE).writeText(
            GsonBuilder().create().toJson(newBasket)
        )
    }

    private fun saveDishCountInPref(basket: Basket) {
        val count = basket.items.sumOf { it.quantity }
        val editor = sharedPreferences.edit()
        editor.putInt(BASKET_COUNT, count)
        editor.apply()
    }

    companion object {
        const val APP_PREFS = "app_prefs"
        const val BASKET_FILE = "basket.json"
        const val BASKET_COUNT = "basket_count"
    }
}