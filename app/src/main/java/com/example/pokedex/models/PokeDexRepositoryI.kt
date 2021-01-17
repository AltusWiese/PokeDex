package com.example.pokedex.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList
import me.sargunvohra.lib.pokekotlin.model.Pokemon

interface PokeDexRepositoryI {

    suspend fun retrieveListOfPokemon(results: MutableLiveData<NamedApiResourceList>)

    suspend fun retrievePokemonSpecifics(results: MutableLiveData<Pokemon>, id: Int)
}