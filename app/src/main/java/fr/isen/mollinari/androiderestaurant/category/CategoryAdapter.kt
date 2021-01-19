package fr.isen.mollinari.androiderestaurant.category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.mollinari.androiderestaurant.databinding.CategoryCellBinding
import fr.isen.mollinari.androiderestaurant.model.Dish

class CategoryAdapter(private val entries: List<Dish>, private val entryClickListener: (Dish) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder =
        CategoryViewHolder(
            CategoryCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.dishName.text = entries[position].title
        holder.dishPrice.text = entries[position].getFormattedPrice()
        Picasso.get().load("https://www.demotivateur.fr/images-buzz/188912/pizza-napolitaine-600x400.jpg").into(holder.dishPicture)
        holder.layout.setOnClickListener {
            entryClickListener.invoke(entries[position])
        }
    }

    override fun getItemCount(): Int = entries.size

    class CategoryViewHolder(categoryBinding: CategoryCellBinding) :
        RecyclerView.ViewHolder(categoryBinding.root) {
        val dishName: TextView = categoryBinding.dishName
        val dishPrice: TextView = categoryBinding.dishPrice
        val dishPicture: ImageView = categoryBinding.dishPicture
        val layout = categoryBinding.root
    }
}
