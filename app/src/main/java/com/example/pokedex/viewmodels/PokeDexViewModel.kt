package com.example.pokedex.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.models.PokeDexRepository
import com.example.pokedex.models.PokeDexRepositoryI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList
import me.sargunvohra.lib.pokekotlin.model.Pokemon

class PokeDexViewModel: ViewModel() {

    private val pokeDexRepository: PokeDexRepositoryI = PokeDexRepository()

     fun getListOfPokemon(): MutableLiveData<NamedApiResourceList> {
         val receivedData = MutableLiveData<NamedApiResourceList>()
         viewModelScope.launch(Dispatchers.IO) {
                pokeDexRepository.retrieveListOfPokemon(receivedData)
         }
         return receivedData
    }

     fun getPokemonSpecifics(id: Int): MutableLiveData<Pokemon> {
         val receivedData = MutableLiveData<Pokemon>()
         viewModelScope.launch(Dispatchers.IO) {
             pokeDexRepository.retrievePokemonSpecifics(receivedData, id)
         }
         return receivedData
    }
}