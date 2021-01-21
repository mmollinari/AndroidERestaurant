package fr.isen.mollinari.androiderestaurant.model

import java.io.Serializable

data class Basket(val items: MutableList<ItemBasket>): Serializable