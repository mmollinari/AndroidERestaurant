package fr.isen.mollinari.androiderestaurant.order

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.mollinari.androiderestaurant.R
import fr.isen.mollinari.androiderestaurant.detail.DetailActivity
import fr.isen.mollinari.androiderestaurant.model.MenuResult
import org.json.JSONObject
import java.io.File

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

       /* val jsonFile = File(cacheDir.absolutePath + DetailActivity.BASKET_FILE)

        val url = getString(R.string.server_domain) + "user/sendmsg"

        val jsonData = JSONObject()
        jsonData.put("msg", jsonFile.readText())
        jsonData.put("id_sender", "7")
        jsonData.put("typemsg", "1")
        jsonData.put("id_receiver", "1")

        val stringRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonData, { response ->
                val menu = GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)

            },
            {
                Log.d("CategoryActivity", "That didn't work! ${it}")
            })

        Volley.newRequestQueue(this).add(stringRequest)*/
    }
}