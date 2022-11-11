package com.greensock.pokeflow.ui.model


sealed interface PokeUiState {
    object Loading : PokeUiState
    data class Succes(val pokeInfoUiModel: PokeInfoUiModel, val list: List<PokeInfoUiModel>) :
        PokeUiState
}
