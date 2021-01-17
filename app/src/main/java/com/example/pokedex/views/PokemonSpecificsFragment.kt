package com.example.pokedex.views

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.pokedex.databinding.FragmentPokedexPokemonSpecificsBinding
import com.example.pokedex.viewmodels.PokeDexViewModel
import com.example.pokedex.views.utils.ChangeActivityHeader
import me.sargunvohra.lib.pokekotlin.model.Pokemon


class PokemonSpecificsFragment : Fragment() {

    private val mViewModel: PokeDexViewModel by activityViewModels()
    val args: PokemonSpecificsFragmentArgs by navArgs()
    private lateinit var callback: ChangeActivityHeader

    private var _binding: FragmentPokedexPokemonSpecificsBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as ChangeActivityHeader
    }

    override fun onDetach() {
        super.onDetach()
        callback.stopAnimation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokedexPokemonSpecificsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callback.setToolbar(args.pokemonName)
        callback.enableBack()
        getPokemonSpecifics(args.pokemonId)
    }

    private fun getPokemonSpecifics(pokemonId: Int) {
        var isPokemonCached = false
        for (pokemon in mViewModel.listOfPokemonSpecifics) {
            if (pokemon.id == pokemonId) {
                displayPokemonData(pokemon)
                isPokemonCached = true
                break
            }
        }
        if (!isPokemonCached) {
            callback.startAnimation()
            mViewModel.getPokemonSpecifics(pokemonId).observe(
                viewLifecycleOwner,
                { pokemonData: Pokemon ->
                    callback.stopAnimation()
                    if (pokemonData != null) {
                        mViewModel.listOfPokemonSpecifics.add(pokemonData)
                        displayPokemonData(pokemonData)
                    }
                })
        }
    }

    private fun displayPokemonData(pokemonData: Pokemon) {
        setImageResource(pokemonData.id)
        val pokemonHeight = pokemonData.height.toString() + " \'"
        val pokemonWeight = pokemonData.weight.toString() + " lbs"
        binding.pokemonName.text = pokemonData.name
        binding.pokemonExperience.text = pokemonData.baseExperience.toString()
        binding.pokemonHeight.text = pokemonHeight
        binding.pokemonWeight.text = pokemonWeight
    }

    private fun setImageResource(pokemonId: Int) {
        val res: Resources = resources
        val mDrawableName = "id$pokemonId"
        val resID: Int = res.getIdentifier(mDrawableName, "drawable", requireActivity().packageName)
        val drawable: Drawable = res.getDrawable(resID)
        binding.pokemonImage.setImageDrawable(drawable)
    }
}