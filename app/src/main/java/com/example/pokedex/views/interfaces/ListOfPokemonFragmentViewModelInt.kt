package com.example.pokedex.views.interfaces

import com.example.pokedex.model.models.FormattedPokemonModel
import com.example.pokedex.model.models.NamedApiResourceList

interface ListOfPokemonFragmentViewModelInt {

    fun startProgressLoader()

    fun stopProgressLoader()

    fun listOfPokemonIsAvailable(listOfPokemon: ArrayList<FormattedPokemonModel>)

    fun listOfPokemonIsNotAvailable()

}