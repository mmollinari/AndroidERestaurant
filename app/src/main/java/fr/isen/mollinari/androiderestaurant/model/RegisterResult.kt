package fr.isen.mollinari.androiderestaurant.model

import com.google.gson.annotations.SerializedName

data class RegisterResult(@SerializedName("data") val data: User, @SerializedName("code") val code: Int)