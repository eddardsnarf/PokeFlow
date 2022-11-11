package com.greensock.pokeflow.data.repo

import android.util.Log
import com.greensock.pokeflow.NAME_LIST
import com.greensock.pokeflow.data.local.PokemonDataStore
import com.greensock.pokeflow.data.local.model.PokemonDataModel
import com.greensock.pokeflow.data.local.model.toJson
import com.greensock.pokeflow.data.remote.PokeService
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class PokeRepository(
    private val dataStore: PokemonDataStore,
    private val api: PokeService
) {

    var dispatcher = IO

    fun getPokemonInfoFlow(): Flow<Result<PokemonDataModel>> = dataStore.getPokemonInfoFlow()

    fun getPokemonListFlow(): Flow<Result<List<PokemonDataModel>>> = dataStore.getPokemonListFlow()

    suspend fun randomizePokemonInfo() = withContext(dispatcher) {
        val apiModel = api.getPokemonInfo(NAME_LIST.random().lowercase())
        Log.d("DERP", "$apiModel")
        val result = dataStore.getPokemonListFlow().first()
        Log.d("DERP2", "$apiModel")
        val list = if (result.isSuccess) {
            result.getOrNull() ?: listOf()
        } else {
            listOf()
        }.toMutableList()
        list.add(apiModel.toDataModel())
        Log.d("DERP3", "$list")

        dataStore.updatePokemonInfo(apiModel.toJson())
        Log.d("DERP3", "${toJson(list)}")
        dataStore.updatePokemonList(toJson(list))


    }
}
