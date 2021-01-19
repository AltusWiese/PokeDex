package com.example.pokedex.model.models

import com.google.gson.annotations.SerializedName

data class NamedApiResource(
    @SerializedName("name")
    var name : String,
    @SerializedName("url")
    var url : String
    )
