@file:OptIn(ExperimentalCoroutinesApi::class)

package com.greensock.pokeflow.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.greensock.pokeflow.data.local.model.PokemonDataModel
import com.greensock.pokeflow.data.local.model.fromJson
import com.greensock.pokeflow.moshi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext

class PokemonDataStore(private val context: Context) {
    private val POKEMON_INFO_KEY = stringPreferencesKey("pokemon_info_key")
    private val POKEMON_LIST_KEY = stringPreferencesKey("pokemon_list_key")
    private val Context.dataStore by preferencesDataStore(
        name = POKEMON_DATA_STORE
    )

    suspend fun updatePokemonInfo(pokemonInfoJson: String?) = withContext(IO) {
        context.dataStore
            .edit { settings ->
                settings[POKEMON_INFO_KEY] = pokemonInfoJson ?: ""
            }
    }

    suspend fun updatePokemonList(pokemonListJson: String?) = withContext(IO) {
        context.dataStore
            .edit { settings ->
                settings[POKEMON_LIST_KEY] = pokemonListJson ?: ""
            }
    }

    private fun getPokemonListJsonFlow(): Flow<String> =
        context.dataStore.data.mapLatest { pref -> pref[POKEMON_LIST_KEY] ?: "" }

    fun getPokemonListFlow(): Flow<Result<List<PokemonDataModel>>> =
        getPokemonListJsonFlow().mapLatest {
            try {
                val model = fromJson(it)
                if (model != null) {
                    Log.d("DERP4", "$model")

                    Result.success(model)
                } else {
                    Result.failure(IllegalStateException("model was null"))
                }
            } catch (ex: Exception) {
                Log.d("DERP5", "$ex")

                Result.failure(ex)
            }
        }.catch { ex ->
            Log.d("DERP6", "$ex")

            emit(Result.failure(ex))
        }

    fun getPokemonInfoFlow(): Flow<Result<PokemonDataModel>> {
        return context.dataStore.data.map { pref ->
            try {
                val model =
                    moshi.adapter(PokemonDataModel::class.java)
                        .fromJson(pref[POKEMON_INFO_KEY] ?: "")
                if (model != null) {
                    Result.success(model)
                } else {
                    Result.failure(IllegalStateException("model was null"))
                }
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        }
    }
}

private const val POKEMON_DATA_STORE = "POKEMON_DATA_STORE"

