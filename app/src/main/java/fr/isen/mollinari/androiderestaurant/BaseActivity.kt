package fr.isen.mollinari.androiderestaurant

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import fr.isen.mollinari.androiderestaurant.basket.BasketActivity
import fr.isen.mollinari.androiderestaurant.detail.DetailActivity

open class BaseActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuView = menu.findItem(R.id.basket).actionView
        val count = getSharedPreferences(
            DetailActivity.APP_PREFS, Context.MODE_PRIVATE).getInt(DetailActivity.BASKET_COUNT, 0)
        val countText = (menuView.findViewById(R.id.cartCount) as? TextView)
        countText?.text = count.toString()
        countText?.isVisible = count > 0
        menuView.setOnClickListener {
            startActivity(Intent(this, BasketActivity::class.java))
        }
        return true
    }
}