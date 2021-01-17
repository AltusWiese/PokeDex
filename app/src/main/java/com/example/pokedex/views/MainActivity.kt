package com.example.pokedex.views

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.airbnb.lottie.LottieAnimationView
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityPokedexBinding
import com.example.pokedex.viewmodels.PokeDexViewModel
import com.example.pokedex.views.utils.AnimationView
import com.example.pokedex.views.utils.BackgroundLottieAnimation
import com.example.pokedex.views.utils.ChangeActivityHeader
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import me.sargunvohra.lib.pokekotlin.model.Pokemon

class MainActivity : AppCompatActivity(), ChangeActivityHeader, BackgroundLottieAnimation {

    private lateinit var mViewModel: PokeDexViewModel
    private lateinit var binding: ActivityPokedexBinding
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var animationView: AnimationView
    private var actionBar: ActionBar? = null
    private var backDisabled: Boolean = false

    var listOfPokemon: List<NamedApiResource> = listOf()
    var pokemonSpecifics: List<Pokemon> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokedexBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        lottieAnimationView = binding.lottieLoader
        setupToolbar()
        setupViewModel()
        setupNavigation()
    }

    private fun setupToolbar() {
        binding.myToolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp)
        setSupportActionBar(binding.myToolbar)

        actionBar = supportActionBar

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProvider(this).get(PokeDexViewModel::class.java)
    }

    private fun setupNavigation() {
        val host = NavHostFragment.create(R.navigation.nav_graph)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, host)
            .setPrimaryNavigationFragment(host).commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (!backDisabled) {
            super.onBackPressed()
        } else {

        }
    }

    override fun setToolbar(string: String?) {
        if (actionBar != null) {
            binding.toolbarTextView.text = string
        }
    }

    override fun enableBack() {
        backDisabled = false
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun disableBack() {
        backDisabled = true
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

    override fun blockUserInteraction() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        );
    }

    override fun allowUserInteraction() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}