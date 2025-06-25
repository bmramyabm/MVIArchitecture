package com.example.mvianimals.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvianimals.R
import com.example.mvianimals.api.AnimalService
import com.example.mvianimals.model.Animal

class AnimalAdapter(private val animals: ArrayList<Animal>): RecyclerView.Adapter<AnimalAdapter.DataViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun newAnimals(newAnimals: List<Animal>){
        animals.clear()
        animals.addAll(newAnimals)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_animal,parent,false)
    )

    override fun onBindViewHolder(
        holder: DataViewHolder,
        position: Int
    ) {
        holder.bind(animals[position])
    }

    override fun getItemCount(): Int = animals.size

    class DataViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val animalName: TextView = itemView.findViewById(R.id.animalName)
        private val animalLocation: TextView = itemView.findViewById(R.id.animalLocation)
        private val animalImage : ImageView = itemView.findViewById(R.id.animalImage)
        fun bind(animal:Animal){
             animalName.text = animal.name
           animalLocation.text = animal.location
            val url = AnimalService.BASE_URL+ animal.image
            Glide.with(animalImage.context)
                .load(url)
                .into(animalImage)
        }
    }


}