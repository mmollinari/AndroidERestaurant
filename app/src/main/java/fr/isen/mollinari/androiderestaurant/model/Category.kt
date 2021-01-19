package fr.isen.mollinari.androiderestaurant.model

import com.google.gson.annotations.SerializedName

data class Category(@SerializedName("name_fr") val name: String, @SerializedName("items") val dishes: List<Dish>)