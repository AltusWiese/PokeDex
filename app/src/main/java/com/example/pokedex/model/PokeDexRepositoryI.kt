package com.example.pokedex.model

import androidx.lifecycle.MutableLiveData
import com.example.pokedex.model.models.NamedApiResourceList
import com.example.pokedex.model.models.Pokemon


interface PokeDexRepositoryI {

    suspend fun retrieveListOfPokemon(results: MutableLiveData<NamedApiResourceList>)

    suspend fun retrievePokemonSpecifics(results: MutableLiveData<Pokemon>, id: Int)
}