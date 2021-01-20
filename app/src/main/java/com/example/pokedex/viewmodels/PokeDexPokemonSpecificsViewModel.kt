package com.example.pokedex.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.PokeDexRepositoryI
import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.views.interfaces.SpecificPokemonFragmentViewModelInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokeDexPokemonSpecificsViewModel : ViewModel() {

    private lateinit var pokeDexRepository: PokeDexRepositoryI
    lateinit var fragmentInterface: SpecificPokemonFragmentViewModelInt
    var listOfPokemonSpecifics: ArrayList<Pokemon> = arrayListOf()

    fun allocateRepo(repository: PokeDexRepositoryI) {
        pokeDexRepository = repository
    }

    fun viewIsReady(fragmentInt: SpecificPokemonFragmentViewModelInt, id: Int) {
        fragmentInterface = fragmentInt
        var isPokemonCached = false
        for (pokemon in listOfPokemonSpecifics) {
            if (pokemon.id == id) {
                fragmentInterface.specificPokemonIsAvailable(pokemon)
                isPokemonCached = true
                break
            }
        }
        if (!isPokemonCached) {
            pokemonSpecificsServiceCall(id)
        }
    }

    private fun pokemonSpecificsServiceCall(id: Int): MutableLiveData<Pokemon> {
        fragmentInterface.startProgressLoader()
        val receivedData = MutableLiveData<Pokemon>()
        viewModelScope.launch(Dispatchers.IO) {
            pokeDexRepository.retrievePokemonSpecifics(id, object : PokeDexRepositoryI.PokemonCallback<Pokemon> {
                override fun onSuccess(result: Pokemon) {
                    fragmentInterface.stopProgressLoader()
                    listOfPokemonSpecifics.add(result)
                    fragmentInterface.specificPokemonIsAvailable(result)
                }

                override fun onFailure() {
                    fragmentInterface.stopProgressLoader()
                    fragmentInterface.specificPokemonIsNotAvailable()
                }
            })
        }
        return receivedData
    }
}