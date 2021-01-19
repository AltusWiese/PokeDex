package com.example.pokedex.model.models

import com.google.gson.annotations.SerializedName

data class FormattedPokemonModel(
    @SerializedName("name")
    var name : String,
    @SerializedName("id")
    var id : Int
)
