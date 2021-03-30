package com.example.pokedex.model

import androidx.lifecycle.MutableLiveData
import com.example.pokedex.model.models.FormattedPokemonModel
import com.example.pokedex.model.models.NamedApiResourceList
import com.example.pokedex.model.models.Pokemon
import com.example.pokedex.network.PokeDexServicesI
import com.example.pokedex.network.RetrofitClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern

class PokeDexRepository: PokeDexRepositoryI {

    private var api : PokeDexServicesI = createRetrofitInstance()
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO

    private fun createRetrofitInstance(): PokeDexServicesI {
        return RetrofitClient().retrofitInstance()!!.create(PokeDexServicesI::class.java)
    }

//    override suspend fun retrieveListOfPokemon(results: MutableLiveData<ArrayList<FormattedPokemonModel>>) {
//        val emptyResponse = NamedApiResourceList(1, "", "", listOf())
//        return withContext(defaultDispatcher) {
//            api.getPokemonList(248,248).enqueue(object : Callback<NamedApiResourceList> {
//                override fun onResponse(call: Call<NamedApiResourceList>, response: Response<NamedApiResourceList>) {
//                  results.value = formatList(response.body())
//                }
//
//                override fun onFailure(call: Call<NamedApiResourceList>, t: Throwable) {
//                    results.value = formatList(emptyResponse)
//                }
//            })
//        }
//    }
//
//    override suspend fun retrievePokemonSpecifics(id: Int, results: MutableLiveData<Pokemon>) {
//        val emptyResponse = Pokemon(0, "", 0, 0, 0, listOf(), listOf())
//        return withContext(Dispatchers.IO) {
//            api.getPokemon(id).enqueue(object : Callback<Pokemon> {
//                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
//                    results.value = response.body()
//                }
//
//                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
//                    results.value = emptyResponse
//                }
//            })
//        }
//    }

    fun formatList(listOfPokemon: NamedApiResourceList?): ArrayList<FormattedPokemonModel> {
        val formattedListOfPokemon = arrayListOf<FormattedPokemonModel>()
        var isFirst = true
        for (result in listOfPokemon!!.results) {
            val p: Pattern = Pattern.compile("\\d+")
            val m: Matcher = p.matcher(result.url)
            while (m.find()) {
                val id = m.group().toString()
                if (isFirst) {
                    isFirst = false
                } else {
                    isFirst = true
                    formattedListOfPokemon.add(FormattedPokemonModel(result.name, id.toInt()))
                }
            }
        }
        return formattedListOfPokemon
    }
    override  fun retrieveListOfPokemon(results: PokeDexRepositoryI.PokemonCallback<NamedApiResourceList>) {
           api.getPokemonList(248,248).enqueue(object : Callback<NamedApiResourceList> {
                override fun onResponse(call: Call<NamedApiResourceList>, response: Response<NamedApiResourceList>) {
                    response.body()?.let { results.onSuccess(it) }
                }

                override fun onFailure(call: Call<NamedApiResourceList>, t: Throwable) {
                    results.onFailure()
                }
            })
    }

    override  fun retrievePokemonSpecifics(id: Int, results: PokeDexRepositoryI.PokemonCallback<Pokemon>) {
           api.getPokemon(id).enqueue(object : Callback<Pokemon> {
               override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                   response.body()?.let { results.onSuccess(it) }
               }

               override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                   results.onFailure()
               }
           })
    }
}