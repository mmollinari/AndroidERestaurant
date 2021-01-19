package fr.isen.mollinari.androiderestaurant.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.mollinari.androiderestaurant.R
import fr.isen.mollinari.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.mollinari.androiderestaurant.model.Dish

private lateinit var binding: ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dish = intent.getSerializableExtra("Dish") as Dish
        binding.titleDetail.text = dish.title
        binding.button.text = getString(R.string.detail_global_amount, dish.getFormattedPrice())

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
        binding.button.text =
            getString(R.string.detail_global_amount,"${(amount * quantity)}€")
    }
}