package com.example.pokedex.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList
import me.sargunvohra.lib.pokekotlin.model.Pokemon

class PokeDexRepository: PokeDexRepositoryI {

    private val pokeApi = PokeApiClient()

    override suspend fun retrieveListOfPokemon(results: MutableLiveData<NamedApiResourceList>) {
        return withContext(Dispatchers.IO) {
            results.postValue(pokeApi.getPokemonList(248, 248))
        }
    }

    override suspend fun retrievePokemonSpecifics(results: MutableLiveData<Pokemon>, id: Int) {
       return withContext(Dispatchers.IO) {
           results.postValue(pokeApi.getPokemon(id))
       }
    }
}