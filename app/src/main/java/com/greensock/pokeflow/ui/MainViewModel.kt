package com.greensock.pokeflow.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.greensock.pokeflow.data.local.model.PokemonDataModel
import com.greensock.pokeflow.data.repo.PokeRepository
import com.greensock.pokeflow.ui.model.PokeInfoUiModel
import com.greensock.pokeflow.ui.model.PokeUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repo: PokeRepository) : ViewModel() {
    private val repoListFlow: Flow<Result<List<PokemonDataModel>>> = repo.getPokemonListFlow()

    private val repoInfoFlow: Flow<Result<PokemonDataModel>> = repo.getPokemonInfoFlow()
    val uiState: LiveData<PokeUiState> =
        repoInfoFlow
            .combineTransform(repoListFlow) { infoResult, listResult ->
                if (!(infoResult.isFailure || listResult.isFailure)) {
                    infoResult.getOrNull()?.let { infoDataModel ->
                        listResult.getOrNull()?.let { list ->
                            emit(
                                PokeUiState.Succes(
                                    PokeInfoUiModel(
                                        infoDataModel.name,
                                        "${infoDataModel.type} type",
                                        infoDataModel.imageUrl,
                                        infoDataModel.spriteUrl,
                                    ), list.map {
                                        PokeInfoUiModel(
                                            it.name,
                                            "${it.type} type",
                                            it.imageUrl,
                                            it.spriteUrl,
                                        )
                                    }
                                )
                            )
                        }
                    }

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



