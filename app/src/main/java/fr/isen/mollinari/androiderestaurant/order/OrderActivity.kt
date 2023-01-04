package fr.isen.mollinari.androiderestaurant.order

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.mollinari.androiderestaurant.HomeActivity
import fr.isen.mollinari.androiderestaurant.R
import fr.isen.mollinari.androiderestaurant.databinding.ActivityOrderBinding
import fr.isen.mollinari.androiderestaurant.detail.DetailActivity
import fr.isen.mollinari.androiderestaurant.sign.LoginActivity
import org.json.JSONObject
import java.io.File

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderBasket()
        binding.orderBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun orderBasket() {
        val jsonFile = File(cacheDir.absolutePath + DetailActivity.BASKET_FILE)

        val url = getString(R.string.server_domain) + "user/order"

        val jsonData = JSONObject()
        jsonData.put("msg", jsonFile.readText())
        jsonData.put("id_shop", "1")
        jsonData.put(
            "id_user",
            getSharedPreferences(
                DetailActivity.APP_PREFS,
                MODE_PRIVATE
            ).getString(LoginActivity.USER_ID, "")
        )

        val stringRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonData, {
                displayContent(true)
            },
            {
                displayContent(false)
                binding.orderDescription.text = getString(R.string.order_description_error)
            })

        Volley.newRequestQueue(this).add(stringRequest)
    }

    private fun displayContent(success: Boolean) {
        binding.deliveryMan.isVisible = success
        binding.warning.isVisible = !success
        binding.orderDescription.isVisible = true
        binding.orderBack.isVisible = true
        binding.orderLoading.isVisible = false
    }
}