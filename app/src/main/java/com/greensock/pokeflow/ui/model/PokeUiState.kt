package com.greensock.pokeflow.ui.model

sealed interface PokeUiState {
    object Loading : PokeUiState
    data class Succes(val pokeUiModel: PokeUiModel) : PokeUiState
}
