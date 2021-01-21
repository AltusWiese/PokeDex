package com.example.pokedex.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokedex.TestCoroutineRule
import com.example.pokedex.model.PokeDexRepository
import com.example.pokedex.model.PokeDexRepositoryI
import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.views.interfaces.SpecificPokemonFragmentViewModelInt
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class TestPokeDexPokemonSpecificsViewModel {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    var listOfPokemonSpecifics: ArrayList<Pokemon> = arrayListOf()

    lateinit var pokemonSpecificsViewModel: PokeDexPokemonSpecificsViewModel
    lateinit var result: Pokemon

    var specificPokemonFragmentViewModelInt: SpecificPokemonFragmentViewModelInt = Mockito.mock(SpecificPokemonFragmentViewModelInt::class.java)

    @Spy
    var spyViewModel: PokeDexPokemonSpecificsViewModel = PokeDexPokemonSpecificsViewModel()

    @Mock
    var pokeDexRepositoryI = PokeDexRepository()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        pokemonSpecificsViewModel = PokeDexPokemonSpecificsViewModel()
        pokemonSpecificsViewModel.fragmentInterface = specificPokemonFragmentViewModelInt
        pokemonSpecificsViewModel.pokeDexRepository = pokeDexRepositoryI
        result = Pokemon(1,"bulbasaur", 240, 7, 120, arrayListOf(), arrayListOf())
    }

    @Test
    fun isRepositoryAllocatedSuccessfully() {
        pokemonSpecificsViewModel.allocateRepo(pokeDexRepositoryI)
        assertEquals(pokemonSpecificsViewModel.pokeDexRepository, pokeDexRepositoryI)
    }

    @Test
    fun whenViewIsReadyAndDataIsMissingIsServiceCallMadeToRetrievePokemonData() {
        pokemonSpecificsViewModel.listOfPokemonSpecifics = listOfPokemonSpecifics
        Mockito.`when`(pokemonSpecificsViewModel.viewIsReady(specificPokemonFragmentViewModelInt, 1)).thenAnswer {
            Mockito.verify(spyViewModel.pokemonSpecificsServiceCall(1))
        }
    }

    @Test
    fun whenViewIsReadyAndDataIsAvailableIsDataSentToFragmentAndServiceCallNotMade() {
        listOfPokemonSpecifics.add(result)
        pokemonSpecificsViewModel.listOfPokemonSpecifics = listOfPokemonSpecifics
        Mockito.`when`(pokemonSpecificsViewModel.viewIsReady(specificPokemonFragmentViewModelInt, 1)).thenAnswer {
            Mockito.verify(spyViewModel.fragmentInterface.specificPokemonIsAvailable(result))
        }
    }

    @Test
    fun isPokemonListDataRetrieved() = testCoroutineRule.runBlockingTest {
        Mockito.`when`(pokemonSpecificsViewModel.pokemonSpecificsServiceCall(1))
                .thenAnswer {
                    (it.arguments[0] as PokeDexRepositoryI.PokemonCallback<Pokemon>)
                            .onSuccess(result)
                    pokemonSpecificsViewModel.listOfPokemonSpecifics.add(result)
                    Mockito.verify(spyViewModel.fragmentInterface.specificPokemonIsAvailable(result))
                }
    }

    @Test
    fun didPokemonRetrieveDataServiceCallFail() = testCoroutineRule.runBlockingTest {
        Mockito.`when`(pokemonSpecificsViewModel.pokemonSpecificsServiceCall(1))
                .thenAnswer {
                    (it.arguments[0] as PokeDexRepositoryI.PokemonCallback<Pokemon>)
                            .onFailure()
                    Mockito.verify(spyViewModel.fragmentInterface.specificPokemonIsNotAvailable())
                }
    }
}