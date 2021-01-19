package com.example.pokedex.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.*
import com.example.pokedex.model.models.FormattedPokemonModel
import com.example.pokedex.model.models.NamedApiResourceList
import com.example.pokedex.model.models.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern

class PokeDexViewModel : ViewModel() {

    private val pokeDexRepository: PokeDexRepositoryI = PokeDexRepository()
    var listOfPokemon: ArrayList<FormattedPokemonModel> = arrayListOf()
    var listOfPokemonSpecifics: ArrayList<Pokemon> = arrayListOf()

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

    fun formatList(listOfPokemon: NamedApiResourceList): List<FormattedPokemonModel> {
        val formattedListOfPokemon = arrayListOf<FormattedPokemonModel>()
        var isFirst = true
        for (result in listOfPokemon.results) {
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
        this.listOfPokemon = formattedListOfPokemon
        return formattedListOfPokemon
    }
}