package com.example.pokedex.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.airbnb.lottie.LottieAnimationView
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityPokedexBinding
import com.example.pokedex.model.PokeDexRepository
import com.example.pokedex.model.PokeDexRepositoryI
import com.example.pokedex.viewmodels.PokeDexListOfPokemonViewModel
import com.example.pokedex.viewmodels.PokeDexPokemonSpecificsViewModel
import com.example.pokedex.views.utils.AnimationView
import com.example.pokedex.views.utils.BackgroundLottieAnimation
import com.example.pokedex.views.utils.ChangeActivityHeader

class MainActivity : AppCompatActivity(), ChangeActivityHeader, BackgroundLottieAnimation {

    private lateinit var mListOfPokemonViewModel: PokeDexListOfPokemonViewModel
    private lateinit var mSpecificPokemonViewModel: PokeDexPokemonSpecificsViewModel
    private lateinit var binding: ActivityPokedexBinding
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var animationView: AnimationView
    private var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokedexBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        lottieAnimationView = binding.lottieLoader
        setupToolbar()
//        setupViewModel()
        setupNavigation()
    }

    private fun setupToolbar() {
        binding.myToolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp)
        setSupportActionBar(binding.myToolbar)

        actionBar = supportActionBar

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }

//    private fun setupViewModel() {
//        val pokeDexRepository: PokeDexRepositoryI = PokeDexRepository()
//        mListOfPokemonViewModel = ViewModelProvider(this).get(PokeDexListOfPokemonViewModel::class.java)
//        mSpecificPokemonViewModel = ViewModelProvider(this).get(PokeDexPokemonSpecificsViewModel::class.java)
//        mListOfPokemonViewModel.allocateRepo(pokeDexRepository)
//        mSpecificPokemonViewModel.allocateRepo(pokeDexRepository)
//    }

    private fun setupNavigation() {
        val host = NavHostFragment.create(R.navigation.nav_graph)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, host)
            .setPrimaryNavigationFragment(host).commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun setToolbar(string: String?) {
        if (actionBar != null) {
            binding.toolbarTextView.text = string
        }
    }

    override fun enableBack() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun disableBack() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    override fun startAnimation() {
        animationView = startBackgroundAnimation(lottieAnimationView)
        binding.fadeBackground.visibility = View.VISIBLE
        binding.fadeBackground.animate().alpha(0.2f)
    }

    override fun stopAnimation() {
        stopBackgroundAnimation(animationView, lottieAnimationView)
        binding.fadeBackground.visibility = View.GONE
    }

    override fun makeServiceToast() {
        Toast.makeText(this, "SERVICE CALL COMPLETED", Toast.LENGTH_LONG).show()
    }

    override fun makeCoroutineToast() {
        Toast.makeText(this, "COROUTINE COMPLETED", Toast.LENGTH_LONG).show()
    }
}