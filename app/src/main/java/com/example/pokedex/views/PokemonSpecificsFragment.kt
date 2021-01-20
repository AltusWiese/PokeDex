package com.example.pokedex.views

import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokedexPokemonSpecificsBinding
import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.viewmodels.PokeDexPokemonSpecificsViewModel
import com.example.pokedex.views.adapters.PokemonAbilitiesAdapter
import com.example.pokedex.views.adapters.PokemonMovesAdapter
import com.example.pokedex.views.interfaces.SpecificPokemonFragmentViewModelInt
import com.example.pokedex.views.utils.ChangeActivityHeader
import me.sargunvohra.lib.pokekotlin.model.PokemonAbility
import me.sargunvohra.lib.pokekotlin.model.PokemonMove


class PokemonSpecificsFragment : Fragment() , SpecificPokemonFragmentViewModelInt {

    private val mSpecificPokemonViewModel: PokeDexPokemonSpecificsViewModel by activityViewModels()
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
    ): View {
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
        notifyViewModelViewIsReady()
    }

    private fun notifyViewModelViewIsReady() {
        mSpecificPokemonViewModel.viewIsReady(this, args.pokemonId)
    }

    private fun displayErrorDialog() {
        val builder = AlertDialog.Builder(requireContext(), R.style.ThemeOverlay_AppCompat_Dialog_Alert)
        builder.setCancelable(false)
        builder.setTitle(Html.fromHtml("<font color='#000000'>Oops!</font>"))
        builder.setMessage("Something went wrong.\nPlease try again.")
        builder.setPositiveButton("OK") { _: DialogInterface?, _: Int -> notifyViewModelViewIsReady() }
        val alert = builder.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
        val pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
        pbutton.setTextColor(Color.parseColor("#0097c9"))
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

    override fun startProgressLoader() {
        callback.startAnimation()
    }

    override fun stopProgressLoader() {
        callback.stopAnimation()
    }

    override fun specificPokemonIsAvailable(pokemon: Pokemon) {
        callback.stopAnimation()
        displayPokemonData(pokemon)
        setupAbilitiesAdapter(pokemon.abilities)
        setupMovesAdapter(pokemon.moves)
    }

    override fun specificPokemonIsNotAvailable() {
        callback.stopAnimation()
        displayErrorDialog()
    }
}