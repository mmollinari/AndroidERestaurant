package fr.isen.mollinari.androiderestaurant.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DishPhotoAdapter(activity: AppCompatActivity, private val items: List<String>) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment =
        DishPhotoFragment.newInstance(items[position])
}