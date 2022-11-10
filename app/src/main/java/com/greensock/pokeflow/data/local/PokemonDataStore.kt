package com.greensock.pokeflow.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.greensock.pokeflow.data.local.model.PokemonDataModel
import com.greensock.pokeflow.moshi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PokemonDataStore(private val context: Context) {
    private val POKEMON_INFO_KEY = stringPreferencesKey("pokemon_info_key")
    private val Context.dataStore by preferencesDataStore(
        name = POKEMON_DATA_STORE
    )

    suspend fun updatePokemonInfo(pokemonInfoJson: String?) = withContext(IO) {
        context.dataStore
            .edit { settings ->
                settings[POKEMON_INFO_KEY] = pokemonInfoJson ?: ""
            }
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
            }catch(ex: Exception){
                Result.failure(ex)
            }
        }
    }
}

private const val POKEMON_DATA_STORE = "POKEMON_DATA_STORE"

