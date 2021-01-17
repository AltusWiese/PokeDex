package com.example.pokedex.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

class ListOfPokemonAdapter(var listOfPokemon: List<NamedApiResource>) : RecyclerView.Adapter<ListOfPokemonAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.list_of_pokemon_card, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pokemon = listOfPokemon[position]
        holder.pokemonName.text = pokemon.name
    }

    override fun getItemCount(): Int {
       return listOfPokemon.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pokemonName: TextView = itemView.findViewById(R.id.list_of_pokemon_card_pokemon_name)
    }
}