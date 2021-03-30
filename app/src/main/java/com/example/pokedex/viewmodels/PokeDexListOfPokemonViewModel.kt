package com.example.pokedex.viewmodels

import androidx.lifecycle.*
import com.example.pokedex.model.*
import com.example.pokedex.model.PokeDexRepositoryI.PokemonCallback
import com.example.pokedex.model.models.FormattedPokemonModel
import com.example.pokedex.model.models.NamedApiResourceList
import com.example.pokedex.views.interfaces.ListOfPokemonFragmentViewModelInt
import kotlinx.coroutines.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class PokeDexListOfPokemonViewModel : ViewModel() {

    val pokeDexRepository: PokeDexRepositoryI = PokeDexRepository()
    var listOfPokemon: ArrayList<FormattedPokemonModel>? = arrayListOf()
    lateinit var fragmentInterface: ListOfPokemonFragmentViewModelInt
    var pokemonLiveData: MutableLiveData<ArrayList<FormattedPokemonModel>> = MutableLiveData()

    fun viewIsReady(fragmentInt: ListOfPokemonFragmentViewModelInt) {

        fragmentInterface = fragmentInt
//        if (listOfPokemon.isEmpty()) {
//            listOfPokemonServiceCall()
//        } else {
//            fragmentInterface.listOfPokemonIsAvailable(listOfPokemon)
//        }
    }

//    fun listOfPokemonServiceCall() {
//        viewModelScope.launch {
//            pokeDexRepository.retrieveListOfPokemon(pokemonLiveData)
//        }
//        listOfPokemon = pokemonLiveData.value
//    }

    fun listOfPokemonServiceCall() {
        fragmentInterface.startProgressLoader()
        pokeDexRepository.retrieveListOfPokemon(object : PokemonCallback<NamedApiResourceList> {
            override fun onSuccess(result: NamedApiResourceList) {
                fragmentInterface.stopProgressLoader()
                fragmentInterface.listOfPokemonIsAvailable(formatList(result))
            }
            override fun onFailure() {
                fragmentInterface.stopProgressLoader()
                fragmentInterface.listOfPokemonIsNotAvailable()
            }
        })
    }

    fun formatList(listOfPokemon: NamedApiResourceList?): ArrayList<FormattedPokemonModel> {
        val formattedListOfPokemon = arrayListOf<FormattedPokemonModel>()
        var isFirst = true
        for (result in listOfPokemon!!.results) {
            val p: Pattern = Pattern.compile("\\d+")
            val m: Matcher = p.matcher(result.url)
            while (m.find()) {
                val id = m.group().toString()
                if (isFirst) {
                    isFirst = false
                } else {
                    isFirst = true
                    formattedListOfPokemon.add(FormattedPokemonModel(result.name, id.toInt()))
                }
            }
        }
        return formattedListOfPokemon
    }
}