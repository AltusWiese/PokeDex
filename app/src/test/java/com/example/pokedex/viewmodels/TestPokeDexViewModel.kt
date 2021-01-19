package com.example.pokedex.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.pokedex.TestCoroutineRule
import com.example.pokedex.model.PokeDexRepository
import com.example.pokedex.model.models.FormattedPokemonModel
import com.example.pokedex.model.models.NamedApiResource
import com.example.pokedex.model.models.NamedApiResourceList
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class TestPokeDexViewModel {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: PokeDexViewModel
    val receivedData = MutableLiveData<NamedApiResourceList>()
    lateinit var result: NamedApiResource
    var formattedPokemonList: ArrayList<FormattedPokemonModel> = arrayListOf()
    lateinit var results: ArrayList<NamedApiResource>
    lateinit var apiList: NamedApiResourceList

    @Mock
    var pokeDexRepositoryI = PokeDexRepository()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = PokeDexViewModel()
        result = NamedApiResource("bulbasaur", "https://pokeapi.co/api/v2/pokemon/249/")
        results = arrayListOf(result)
        apiList = NamedApiResourceList(248, "null", "null", results)
        formattedPokemonList.add(FormattedPokemonModel("bulbasaur", 249))
    }

    @Test
    fun isPokemonListDataRetrieved() = testCoroutineRule.runBlockingTest {
        `when`(pokeDexRepositoryI.retrieveListOfPokemon(receivedData))
            .thenReturn(isIdSuccessfullyExtractedFromUrl())
        assertNotNull(viewModel.listOfPokemon)
        assertEquals(viewModel.listOfPokemon, formattedPokemonList)
    }

    @Test
    fun isIdSuccessfullyExtractedFromUrl() {
       assertEquals(viewModel.formatList(apiList), formattedPokemonList)
    }

}