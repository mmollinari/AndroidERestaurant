package fr.isen.mollinari.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Dish(
    @SerializedName("name_fr") val title: String,
    @SerializedName("ingredients") val ingredients: List<Ingredient>,
    @SerializedName("prices") private val prices: List<Price>
): Serializable {

    fun getPrice() = prices[0].price.toDouble()
    fun getFormattedPrice() = prices[0].price + "€"
}