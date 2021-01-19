package com.example.pokedex.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.pokedex.TestCoroutineRule
import com.example.pokedex.model.PokeDexRepository
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
    lateinit var results: ArrayList<NamedApiResource>
    lateinit var apiList: NamedApiResourceList

    @Mock
    var pokeDexRepositoryI = PokeDexRepository()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = PokeDexViewModel()
        result = NamedApiResource("Bulbasaur", "Plant")
        results = arrayListOf(result)
        apiList = NamedApiResourceList(248, "null", "null", results)
    }

//    @Test
//    fun isPokemonListDataRetrieved() = testCoroutineRule.runBlockingTest {
//        `when`(pokeDexRepositoryI.retrieveListOfPokemon(receivedData)).thenReturn(setList())
//        assertNotNull(viewModel.listOfPokemon)
//        assertEquals(viewModel.listOfPokemon, apiList.results)
//    }
//
//    fun setList() {
//        receivedData.value = apiList
//        viewModel.listOfPokemon = receivedData.value!!.results
//    }

}