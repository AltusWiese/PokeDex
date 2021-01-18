package com.example.pokedex.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.pokedex.TestCoroutineRule
import com.example.pokedex.models.PokeDexRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doReturn
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
    lateinit var results: List<NamedApiResource>
    lateinit var apiList: NamedApiResourceList

    @Mock
    var pokeDexRepositoryI = PokeDexRepository()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = PokeDexViewModel()
        result = NamedApiResource("Bulbasaur", "Plant", 1)
        results = listOf(result)
        apiList = NamedApiResourceList(248, "null", "null", results)
    }

    @Test
    fun isPokemonListDataRetrieved() = testCoroutineRule.runBlockingTest {
        `when`(pokeDexRepositoryI.retrieveListOfPokemon(receivedData)).thenReturn(setList())
        assertNotNull(viewModel.listOfPokemon)
        assertEquals(viewModel.listOfPokemon, apiList.results)
    }

    fun setList() {
        receivedData.value = apiList
        viewModel.listOfPokemon = receivedData.value!!.results
    }

}