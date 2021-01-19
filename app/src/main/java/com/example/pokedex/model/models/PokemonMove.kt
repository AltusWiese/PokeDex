package com.example.pokedex.model.models

import com.google.gson.annotations.SerializedName

data class PokemonMove(
    @SerializedName("move")
    val move: NamedApiResource
)
