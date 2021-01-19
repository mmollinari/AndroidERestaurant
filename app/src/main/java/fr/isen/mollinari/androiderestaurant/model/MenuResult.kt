package fr.isen.mollinari.androiderestaurant.model

import com.google.gson.annotations.SerializedName

data class MenuResult(@SerializedName("data") val data: List<Category>)