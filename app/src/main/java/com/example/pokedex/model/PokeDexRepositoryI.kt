package com.example.pokedex.model

import com.example.pokedex.model.models.NamedApiResourceList
import com.example.pokedex.model.models.Pokemon


interface PokeDexRepositoryI {

    suspend fun retrieveListOfPokemon(results: PokemonCallback<NamedApiResourceList>)

    suspend fun retrievePokemonSpecifics( id: Int, results: PokemonCallback<Pokemon>)

    interface PokemonCallback<T> {

        fun onSuccess(result: T)

        fun onFailure()
    }
}