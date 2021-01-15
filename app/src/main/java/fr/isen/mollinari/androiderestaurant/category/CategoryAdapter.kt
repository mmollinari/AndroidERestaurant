package fr.isen.mollinari.androiderestaurant.category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        holder.entryName.text = entries[position].title
        holder.layout.setOnClickListener {
            entryClickListener.invoke(entries[position])
        }
    }

    override fun getItemCount(): Int = entries.size

    class CategoryViewHolder(categoryBinding: CategoryCellBinding) :
        RecyclerView.ViewHolder(categoryBinding.root) {
        val entryName: TextView = categoryBinding.entryName
        val layout = categoryBinding.root
    }
}
