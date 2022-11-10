package com.greensock.pokeflow.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.greensock.pokeflow.data.local.model.PokemonDataModel
import com.greensock.pokeflow.data.repo.PokeRepository
import com.greensock.pokeflow.ui.model.PokeUiModel
import com.greensock.pokeflow.ui.model.PokeUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repo: PokeRepository) : ViewModel() {
    private val repoFlow: Flow<Result<PokemonDataModel>> = repo.getPokemonInfoFlow()
    val uiState: LiveData<PokeUiState> =
        repoFlow
            .mapNotNull { result ->
                if (result.isSuccess) {
                    result.getOrNull()
                        ?.let { dataModel ->
                            PokeUiState.Succes(
                                PokeUiModel(
                                    dataModel.name,
                                    "${dataModel.type} type",
                                    dataModel.imageUrl,
                                    dataModel.spriteUrl,
                                )
                            )
                        }
                } else {
                    null
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = PokeUiState.Loading
            ).asLiveData()

    fun updatePokemon() {
        viewModelScope.launch {
            repo.randomizePokemonInfo()
        }
    }
}



