package com.example.pokedex.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.FragmentPokedexListOfPokemonBinding
import com.example.pokedex.viewmodels.PokeDexViewModel
import com.example.pokedex.views.adapters.ListOfPokemonAdapter
import com.example.pokedex.views.utils.ChangeActivityHeader
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList

class ListOfPokemonFragment : Fragment() {

    private val mViewModel: PokeDexViewModel by activityViewModels()
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
    ): View? {
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
        getListOfPokemon()
    }

    private fun getListOfPokemon() {
        callback.startAnimation()
        mViewModel.getListOfPokemon()
            .observe(viewLifecycleOwner, { listOfPokemon: NamedApiResourceList ->
                if (listOfPokemon != null) {
                    val list = listOfPokemon.results
                    (activity as MainActivity).listOfPokemon = list
                    setupAdapter(list)
                    callback.stopAnimation()
                } else {
                    Toast.makeText(requireContext(), "Service call failed. Please try again.", Toast.LENGTH_LONG).show()
                    callback.stopAnimation()
                }
            })
    }

    private fun setupAdapter(list: List<NamedApiResource>) {
        val manager = LinearLayoutManager(context)
        listOfPokemonAdapter = ListOfPokemonAdapter(list)
        val list: RecyclerView = binding.pokedexListOfPokemonRecyclerview
        list.layoutManager = manager
        list.setHasFixedSize(true)
        list.itemAnimator = DefaultItemAnimator()
        list.adapter = listOfPokemonAdapter
    }
}