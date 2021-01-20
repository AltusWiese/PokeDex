package com.example.pokedex.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import me.sargunvohra.lib.pokekotlin.model.PokemonAbility

class PokemonAbilitiesAdapter(var listOfAbilities: List<PokemonAbility>) :
        RecyclerView.Adapter<PokemonAbilitiesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_abilities_moves_card, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val abilities = listOfAbilities[position]
        holder.pokemonAbility.text = abilities.ability.name
    }

    override fun getItemCount(): Int {
      return listOfAbilities.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pokemonAbility: TextView = itemView.findViewById(R.id.pokemon_abilities_description)
    }

}