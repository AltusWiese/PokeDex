package com.example.pokedex.model

import androidx.lifecycle.MutableLiveData
import com.example.pokedex.model.models.FormattedPokemonModel
import com.example.pokedex.model.models.NamedApiResourceList
import com.example.pokedex.model.models.Pokemon


interface PokeDexRepositoryI {

     fun retrieveListOfPokemon(results: PokemonCallback<NamedApiResourceList>)
//    suspend fun retrieveListOfPokemon(results: MutableLiveData<ArrayList<FormattedPokemonModel>>)

     fun retrievePokemonSpecifics( id: Int, results: PokemonCallback<Pokemon>)
//    suspend fun retrievePokemonSpecifics( id: Int, results: MutableLiveData<Pokemon>)

    interface PokemonCallback<T> {

        fun onSuccess(result: T)

        fun onFailure()
    }
}