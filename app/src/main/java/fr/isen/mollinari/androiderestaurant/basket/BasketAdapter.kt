package fr.isen.mollinari.androiderestaurant.basket

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.mollinari.androiderestaurant.R
import fr.isen.mollinari.androiderestaurant.databinding.BasketCellBinding
import fr.isen.mollinari.androiderestaurant.model.ItemBasket

class BasketAdapter(private val entries: MutableList<ItemBasket>, private val deleteClickListener: (ItemBasket) -> Unit) :
    RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasketViewHolder {
        context = parent.context
        return BasketViewHolder(
            BasketCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.dishName.text = entries[position].dish.title
        holder.dishPrice.text = entries[position].dish.getFormattedPrice()
        holder.dishQuantity.text = context.resources.getString(
            R.string.basket_quantity,
            entries[position].quantity.toString()
        )
        Picasso.get()
            .load("https://www.demotivateur.fr/images-buzz/188912/pizza-napolitaine-600x400.jpg")
            .into(holder.dishPicture)
        holder.deleteDish.setOnClickListener {
            deleteClickListener.invoke(entries[position])
            entries.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = entries.size

    class BasketViewHolder(categoryBinding: BasketCellBinding) :
        RecyclerView.ViewHolder(categoryBinding.root) {
        val dishName: TextView = categoryBinding.dishName
        val dishPrice: TextView = categoryBinding.dishPrice
        val dishPicture: ImageView = categoryBinding.dishPicture
        val dishQuantity: TextView = categoryBinding.quantity
        val deleteDish: ImageView = categoryBinding.delete
    }
}
