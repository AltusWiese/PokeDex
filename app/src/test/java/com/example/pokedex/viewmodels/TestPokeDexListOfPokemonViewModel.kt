package com.example.pokedex.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokedex.TestCoroutineRule
import com.example.pokedex.model.PokeDexRepository
import com.example.pokedex.model.PokeDexRepositoryI.PokemonCallback
import com.example.pokedex.model.models.FormattedPokemonModel
import com.example.pokedex.model.models.NamedApiResource
import com.example.pokedex.model.models.NamedApiResourceList
import com.example.pokedex.views.interfaces.ListOfPokemonFragmentViewModelInt
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class TestPokeDexListOfPokemonViewModel {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    var formattedPokemonListWithData: ArrayList<FormattedPokemonModel> = arrayListOf()

    lateinit var listOfPokemonViewModel: PokeDexListOfPokemonViewModel
    lateinit var result: NamedApiResource
    lateinit var results: ArrayList<NamedApiResource>
    lateinit var apiList: NamedApiResourceList

    var listOfPokemonFragmentViewModelInt: ListOfPokemonFragmentViewModelInt = mock(ListOfPokemonFragmentViewModelInt::class.java)

    @Spy
    var spyViewModel: PokeDexListOfPokemonViewModel = PokeDexListOfPokemonViewModel()

    @Mock
    var pokeDexRepositoryI = PokeDexRepository()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        listOfPokemonViewModel = PokeDexListOfPokemonViewModel()
        listOfPokemonViewModel.fragmentInterface = listOfPokemonFragmentViewModelInt
        listOfPokemonViewModel.pokeDexRepository = pokeDexRepositoryI
        result = NamedApiResource("bulbasaur", "https://pokeapi.co/api/v2/pokemon/249/")
        results = arrayListOf(result)
        apiList = NamedApiResourceList(248, "null", "null", results)
        formattedPokemonListWithData.add(FormattedPokemonModel("bulbasaur", 249))
    }

    @Test
    fun isRepositoryAllocatedSuccessfully() {
        listOfPokemonViewModel.allocateRepo(pokeDexRepositoryI)
        assertEquals(listOfPokemonViewModel.pokeDexRepository, pokeDexRepositoryI)
    }

    @Test
    fun whenViewIsReadyAndDataIsMissingIsServiceCallMadeToRetrievePokemonData() {
        listOfPokemonViewModel.listOfPokemon = formattedPokemonListWithData
        `when`(listOfPokemonViewModel.viewIsReady(listOfPokemonFragmentViewModelInt)).thenAnswer {
            verify(spyViewModel.listOfPokemonServiceCall())
        }
    }

    @Test
    fun whenViewIsReadyAndDataIsAvailableIsDataSentToFragmentAndServiceCallNotMade() {
        listOfPokemonViewModel.listOfPokemon = formattedPokemonListWithData
        `when`(listOfPokemonViewModel.viewIsReady(listOfPokemonFragmentViewModelInt)).thenAnswer {
            verify(spyViewModel.fragmentInterface.listOfPokemonIsAvailable(listOfPokemonViewModel.listOfPokemon))
        }
    }

    @Test
    fun isPokemonListDataRetrieved() = testCoroutineRule.runBlockingTest {
        `when`(listOfPokemonViewModel.listOfPokemonServiceCall())
                .thenAnswer {
                    (it.arguments[0] as PokemonCallback<NamedApiResourceList>)
                            .onSuccess(apiList)
                }
        listOfPokemonViewModel.formatList(apiList)
        assertEquals(listOfPokemonViewModel.listOfPokemon, formattedPokemonListWithData)
    }

    @Test
    fun didPokemonRetrieveDataServiceCallFail() = testCoroutineRule.runBlockingTest {
        `when`(listOfPokemonViewModel.listOfPokemonServiceCall())
                .thenAnswer {
                    (it.arguments[0] as PokemonCallback<NamedApiResourceList>)
                            .onFailure()
                    verify(spyViewModel.fragmentInterface.listOfPokemonIsNotAvailable())
                }
    }

    @Test
    fun isIdSuccessfullyExtractedFromUrl() {
       assertEquals(listOfPokemonViewModel.formatList(apiList), formattedPokemonListWithData)
    }
}