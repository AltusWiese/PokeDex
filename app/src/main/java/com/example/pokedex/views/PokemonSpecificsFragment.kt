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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.FragmentPokedexPokemonSpecificsBinding
import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.viewmodels.PokeDexViewModel
import com.example.pokedex.views.adapters.PokemonAbilitiesAdapter
import com.example.pokedex.views.adapters.PokemonMovesAdapter
import com.example.pokedex.views.utils.ChangeActivityHeader
import me.sargunvohra.lib.pokekotlin.model.PokemonAbility
import me.sargunvohra.lib.pokekotlin.model.PokemonMove


class PokemonSpecificsFragment : Fragment() {

    private val mViewModel: PokeDexViewModel by activityViewModels()
    val args: PokemonSpecificsFragmentArgs by navArgs()
    private lateinit var callback: ChangeActivityHeader
    private lateinit var pokemonAbilitiesAdapter: PokemonAbilitiesAdapter
    private lateinit var pokemonMovesAdapter: PokemonMovesAdapter

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
                setupAbilitiesAdapter(pokemon.abilities)
                setupMovesAdapter(pokemon.moves)
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
                        setupAbilitiesAdapter(pokemonData.abilities)
                        setupMovesAdapter(pokemonData.moves)
                    }
                })
        }
    }

    private fun setupAbilitiesAdapter(listOfAbilities: List<PokemonAbility>) {
        val manager = LinearLayoutManager(context)
        pokemonAbilitiesAdapter = PokemonAbilitiesAdapter(listOfAbilities)
        val recycler: RecyclerView = binding.pokemonAbilitiesRecyclerview
        recycler.layoutManager = manager
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = pokemonAbilitiesAdapter
    }

    private fun setupMovesAdapter(listOfMoves: List<PokemonMove>) {
        val manager = LinearLayoutManager(context)
        pokemonMovesAdapter = PokemonMovesAdapter(listOfMoves)
        val recycler: RecyclerView = binding.pokemonMovesRecyclerview
        recycler.layoutManager = manager
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = pokemonMovesAdapter
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