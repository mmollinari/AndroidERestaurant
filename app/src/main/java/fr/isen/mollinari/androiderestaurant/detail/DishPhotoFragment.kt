package fr.isen.mollinari.androiderestaurant.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.isen.mollinari.androiderestaurant.R
import fr.isen.mollinari.androiderestaurant.databinding.FragmentDishPhotoBinding


class DishPhotoFragment : Fragment() {

    private lateinit var binding: FragmentDishPhotoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDishPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("URL")?.let {
            Picasso.get().load(it).placeholder(R.drawable.android_logo_restaurant).into(binding.photo)
        }
    }

    companion object {
        fun newInstance(url: String?) =
            DishPhotoFragment().apply { arguments = Bundle().apply { putString("URL", url) } }
    }
}