package com.example.pokedex.model.models

import com.google.gson.annotations.SerializedName

data class PokemonAbility(
    @SerializedName("ability")
    val ability: NamedApiResource
)
