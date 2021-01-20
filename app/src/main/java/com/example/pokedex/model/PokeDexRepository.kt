package com.example.pokedex.model

import androidx.lifecycle.MutableLiveData
import com.example.pokedex.model.models.NamedApiResourceList
import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.network.PokeDexServicesI
import com.example.pokedex.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokeDexRepository: PokeDexRepositoryI {

//    private val pokeApi = PokeApiClient()
    private var api : PokeDexServicesI = createRetrofitInstance()

    private fun createRetrofitInstance(): PokeDexServicesI {
        return RetrofitClient().retrofitInstance()!!.create(PokeDexServicesI::class.java)
    }

    override suspend fun retrieveListOfPokemon(results: MutableLiveData<NamedApiResourceList>) {
        return withContext(Dispatchers.IO) {
           api.getPokemonList(248,248).enqueue(object : Callback<NamedApiResourceList> {
                override fun onResponse(call: Call<NamedApiResourceList>, response: Response<NamedApiResourceList>) {
                   results.postValue(response.body())
                }

                override fun onFailure(call: Call<NamedApiResourceList>, t: Throwable) {
                    results.postValue(NamedApiResourceList(0,"","", emptyList()))
                }

            })
//            results.postValue(pokeApi.getPokemonList(248, 248))
        }
    }

    override suspend fun retrievePokemonSpecifics(results: MutableLiveData<Pokemon>, id: Int) {
       return withContext(Dispatchers.IO) {
           api.getPokemon(id).enqueue(object : Callback<Pokemon> {
               override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                   results.postValue(response.body())
               }

               override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                   results.postValue(Pokemon(-1,"",-1,-1,-1, emptyList(), emptyList()))
               }

           })
//           results.postValue(pokeApi.getPokemon(id))
       }
    }
}