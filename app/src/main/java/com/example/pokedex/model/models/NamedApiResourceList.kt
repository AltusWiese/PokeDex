package com.example.pokedex.model.models

import com.google.gson.annotations.SerializedName

data class NamedApiResourceList(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<NamedApiResource>
)