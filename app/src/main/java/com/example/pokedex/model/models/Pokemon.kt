package com.example.pokedex.model.models

import com.google.gson.annotations.SerializedName
import me.sargunvohra.lib.pokekotlin.model.PokemonAbility
import me.sargunvohra.lib.pokekotlin.model.PokemonMove

data class Pokemon(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("base_experience")
    val baseExperience: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("abilities")
    val abilities: List<PokemonAbility>,
    @SerializedName("moves")
    val moves: List<PokemonMove>
)
