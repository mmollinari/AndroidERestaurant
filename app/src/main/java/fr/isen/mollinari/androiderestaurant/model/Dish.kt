package fr.isen.mollinari.androiderestaurant.model

import java.io.Serializable

data class Dish(val title: String, val price: Double, val photos: List<String>): Serializable