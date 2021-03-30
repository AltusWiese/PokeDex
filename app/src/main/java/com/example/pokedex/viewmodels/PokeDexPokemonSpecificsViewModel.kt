package com.example.pokedex.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.PokeDexRepository
import com.example.pokedex.model.PokeDexRepositoryI
import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.views.interfaces.SpecificPokemonFragmentViewModelInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PokeDexPokemonSpecificsViewModel : ViewModel() {

    val pokeDexRepository: PokeDexRepositoryI = PokeDexRepository()
    lateinit var fragmentInterface: SpecificPokemonFragmentViewModelInt
    var listOfPokemonSpecifics: ArrayList<Pokemon?> = arrayListOf()
    var specificPokemonLiveData: MutableLiveData<Pokemon> = MutableLiveData()

    fun viewIsReady(fragmentInt: SpecificPokemonFragmentViewModelInt, id: Int) {
        fragmentInterface = fragmentInt
//        var isPokemonCached = false
//        for (pokemon in listOfPokemonSpecifics) {
//            if (pokemon.id == id) {
//                fragmentInterface.specificPokemonIsAvailable(pokemon)
//                isPokemonCached = true
//                break
//            }
//        }
//        if (!isPokemonCached) {
//            pokemonSpecificsServiceCall(id)
//        }
    }

//    fun pokemonSpecificsServiceCall(id: Int) {
//        viewModelScope.launch {
//            pokeDexRepository.retrievePokemonSpecifics(id, specificPokemonLiveData)
//        }
//    }

    fun pokemonSpecificsServiceCall(id: Int) {
        fragmentInterface.startProgressLoader()
        pokeDexRepository.retrievePokemonSpecifics(id, object : PokeDexRepositoryI.PokemonCallback<Pokemon> {
            override fun onSuccess(result: Pokemon) {
                fragmentInterface.stopProgressLoader()
                listOfPokemonSpecifics.add(result)
                fragmentInterface.specificPokemonIsAvailable(result)
                fragmentInterface.makeServiceToast()
            }
            override fun onFailure() {
                fragmentInterface.stopProgressLoader()
                fragmentInterface.specificPokemonIsNotAvailable()
            }
        })
    }
}