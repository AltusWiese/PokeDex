package com.example.pokedex.views.interfaces

import com.example.pokedex.model.models.FormattedPokemonModel
import com.example.pokedex.model.models.Pokemon

interface SpecificPokemonFragmentViewModelInt {

    fun startProgressLoader()

    fun stopProgressLoader()

    fun specificPokemonIsAvailable(pokemon : Pokemon)

    fun specificPokemonIsNotAvailable()
}