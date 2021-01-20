package com.example.pokedex.views

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokedexListOfPokemonBinding
import com.example.pokedex.model.models.FormattedPokemonModel
import com.example.pokedex.viewmodels.PokeDexListOfPokemonViewModel
import com.example.pokedex.views.adapters.ListOfPokemonAdapter
import com.example.pokedex.views.interfaces.ListOfPokemonFragmentViewModelInt
import com.example.pokedex.views.interfaces.ListOfPokemonToPokemonSpecificsInt
import com.example.pokedex.views.utils.ChangeActivityHeader

class ListOfPokemonFragment : Fragment(), ListOfPokemonToPokemonSpecificsInt, ListOfPokemonFragmentViewModelInt {

    private val mListOfPokemonViewModel: PokeDexListOfPokemonViewModel by activityViewModels()
    private lateinit var callback: ChangeActivityHeader
    private lateinit var listOfPokemonAdapter: ListOfPokemonAdapter

    private var _binding: FragmentPokedexListOfPokemonBinding? = null
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
        _binding = FragmentPokedexListOfPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callback.setToolbar("List of Pokemon")
        callback.disableBack()
        notifyViewModelViewIsReady()
    }

    private fun notifyViewModelViewIsReady() {
        mListOfPokemonViewModel.viewIsReady(this)
    }

    private fun setupAdapter(listOfPokemon: ArrayList<FormattedPokemonModel>) {
        val manager = LinearLayoutManager(context)
        listOfPokemonAdapter = ListOfPokemonAdapter(this, listOfPokemon)
        val recycler: RecyclerView = binding.pokedexListOfPokemonRecyclerview
        recycler.layoutManager = manager
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = listOfPokemonAdapter
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

    override fun fetchSpecificPokemon(name: String, id: Int) {
        val action = ListOfPokemonFragmentDirections.actionFragmentPokedexListOfPokemonToFragmentPokedexPokemonSpecifics(id, name)
        findNavController().navigate(action)
    }

    override fun startProgressLoader() {
        callback.startAnimation()
    }

    override fun stopProgressLoader() {
        callback.stopAnimation()
    }

    override fun listOfPokemonIsAvailable(listOfPokemon: ArrayList<FormattedPokemonModel>) {
        callback.stopAnimation()
        setupAdapter(listOfPokemon)
    }

    override fun listOfPokemonIsNotAvailable() {
        callback.stopAnimation()
        displayErrorDialog()
    }
}