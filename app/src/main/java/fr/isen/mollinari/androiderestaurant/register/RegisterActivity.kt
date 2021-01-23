package fr.isen.mollinari.androiderestaurant.register

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.mollinari.androiderestaurant.R
import fr.isen.mollinari.androiderestaurant.databinding.ActivityRegisterBinding
import fr.isen.mollinari.androiderestaurant.detail.DetailActivity
import fr.isen.mollinari.androiderestaurant.model.MenuResult
import fr.isen.mollinari.androiderestaurant.model.RegisterResult
import org.json.JSONObject

private lateinit var binding: ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginAction.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val url = getString(R.string.server_domain) + "user/register"

        val jsonData = JSONObject()
        jsonData.put("id_shop", "1")
        jsonData.put("firstname", binding.firstName.text)
        jsonData.put("lastname", binding.lastname.text)
        jsonData.put("address", binding.address.text)
        jsonData.put("email", binding.login.text)
        jsonData.put("password", binding.password.text)

        val stringRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonData, { response ->
                val register = GsonBuilder().create().fromJson(response.toString(), RegisterResult::class.java)
                val editor = getSharedPreferences(DetailActivity.APP_PREFS, Context.MODE_PRIVATE).edit()
                editor.putString(USER_ID, register.data.userId)
                editor.apply()
            },
            {
                Log.d("CategoryActivity", "That didn't work! ${it}")
            })

        Volley.newRequestQueue(this).add(stringRequest)
    }

    companion object {
        const val USER_ID = "USER_ID"
    }
}