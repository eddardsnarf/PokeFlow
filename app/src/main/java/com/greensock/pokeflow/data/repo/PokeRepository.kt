package com.greensock.pokeflow.data.repo

import com.greensock.pokeflow.NAME_LIST
import com.greensock.pokeflow.data.local.PokemonDataStore
import com.greensock.pokeflow.data.local.model.PokemonDataModel
import com.greensock.pokeflow.data.remote.PokeService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class PokeRepository(
    private val dataStore: PokemonDataStore,
    private val api: PokeService
) {
    private val coroutineScope = CoroutineScope(IO)

    fun getPokemonInfoFlow(): Flow<Result<PokemonDataModel>> {
       return dataStore.getPokemonInfoFlow()
    }

    fun randomizePokemonInfo() {
        coroutineScope.launch {
            val apiModel = api.getPokemonInfo(NAME_LIST.random().lowercase())
            dataStore.updatePokemonInfo(apiModel.toJson())
        }
    }
}
