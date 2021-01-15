package fr.isen.mollinari.androiderestaurant.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.isen.mollinari.androiderestaurant.databinding.FragmentDishPhotoBinding

private lateinit var binding: FragmentDishPhotoBinding

class DishPhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDishPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("URL")?.let {
            Picasso.get().load(it).into(binding.photo)
        }
    }

    companion object {
        fun newInstance(url: String) =
            DishPhotoFragment().apply { arguments = Bundle().apply { putString("URL", url) } }
    }
}