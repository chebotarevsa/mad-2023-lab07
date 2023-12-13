package com.example.lab5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterRecyclerView(private val action: ActionInterface) :
    RecyclerView.Adapter<AdapterRecyclerView.MyViewHolder>() { //Наследуется от <постонно>
    class MyViewHolder(itemView: View) : //<постонно>
        RecyclerView.ViewHolder(itemView) { //Тут лежат элементы интерфейса карточки, которая лежит в списке
        val thumbnailImage: ImageView = itemView.findViewById(R.id.pictureSmall)
        val largeTextView: TextView = itemView.findViewById(R.id.textAbove)
        val smallTextView: TextView = itemView.findViewById(R.id.textBelow)
        val deleteImage: ImageView = itemView.findViewById(R.id.delete)
    } //ВРОДЕ поиск по данной карточке в списке тех элементов, что лежат в ней (дальше передавать сам card должны?)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = cards.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val card = cards[position]
        if (card.image != null) {
            holder.thumbnailImage.setImageBitmap(cards[position].image)
        } else {
            holder.thumbnailImage.setImageResource(R.drawable.empty)
        }
        holder.largeTextView.text = card.answer
        holder.smallTextView.text = card.translation
        holder.itemView.setOnClickListener {
            action.onItemClick(card.id)
        }
        holder.deleteImage.setOnClickListener {
            action.onDeleteCard(card.id)
        }
    }

    var cards: List<Card> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
        get() = field
}

interface ActionInterface {
    fun onItemClick(cardId: Int)
    fun onDeleteCard(cardId: Int)
}