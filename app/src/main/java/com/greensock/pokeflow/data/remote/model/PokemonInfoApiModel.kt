package com.greensock.pokeflow.data.remote.model

import com.greensock.pokeflow.data.local.model.PokemonDataModel
import com.greensock.pokeflow.moshi
import com.squareup.moshi.Json

data class PokemonInfoApiModel(
    val name: String,
    val types: List<PokemonType>,
    val sprites: PokemonSpritesApiModel
) {
    fun toJson(): String =
        moshi.adapter(PokemonDataModel::class.java)
            .toJson(toDataModel())


    fun toDataModel(): PokemonDataModel =
        PokemonDataModel(
            name,
            types[0].type.name,
            sprites.other.officialArtwork.defaultFront,
            sprites.frontDefault
        )
}

data class PokemonSpritesApiModel(
    @field:Json(name = "front_default") val frontDefault: String,
    val other: OtherSpritesApiModel
)

data class OtherSpritesApiModel(@field:Json(name = "official-artwork") val officialArtwork: OfficialArtwork)
data class OfficialArtwork(@field:Json(name = "front_default") val defaultFront: String)
data class PokemonType(val type: SubType)
data class SubType(val name: String)
