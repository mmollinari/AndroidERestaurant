package fr.isen.mollinari.androiderestaurant.sign

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import fr.isen.mollinari.androiderestaurant.R
import fr.isen.mollinari.androiderestaurant.basket.BasketActivity
import fr.isen.mollinari.androiderestaurant.databinding.ActivityRegisterBinding
import fr.isen.mollinari.androiderestaurant.detail.DetailActivity
import fr.isen.mollinari.androiderestaurant.model.RegisterResult
import org.json.JSONObject

private lateinit var binding: ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginAction.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.register.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val url = getString(R.string.server_domain) + "user/register"

        val field = listOf(
            binding.firstName.text,
            binding.lastname.text,
            binding.address.text,
            binding.login.text,
            binding.password.text
        )

        val hasError = field.any { it?.trim().isNullOrEmpty() }

        if(!hasError) {
            val jsonData = JSONObject()
            jsonData.put("id_shop", "1")
            jsonData.put("firstname", binding.firstName.text)
            jsonData.put("lastname", binding.lastname.text)
            jsonData.put("address", binding.address.text)
            jsonData.put("email", binding.login.text)
            jsonData.put("password", binding.password.text)

            val stringRequest = JsonObjectRequest(
                Request.Method.POST, url, jsonData, { response ->
                    val register =
                        GsonBuilder().create()
                            .fromJson(response.toString(), RegisterResult::class.java)
                    val editor =
                        getSharedPreferences(DetailActivity.APP_PREFS, Context.MODE_PRIVATE).edit()
                    editor.putString(USER_ID, register.data.userId)
                    editor.apply()
                    startActivity(Intent(this, BasketActivity::class.java))
                    finish()
                },
                {
                    Log.d("RegisterActivity", "That didn't work! ${it}")
                })

            Volley.newRequestQueue(this).add(stringRequest)
        } else {
            Snackbar.make(binding.root, "Un des champs est mal renseigné. Veuillez vérifier la saisie", Snackbar.LENGTH_LONG).show()
        }
    }

    companion object {
        const val USER_ID = "USER_ID"
    }
}