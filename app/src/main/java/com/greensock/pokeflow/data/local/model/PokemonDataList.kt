package com.greensock.pokeflow.data.local.model

import com.greensock.pokeflow.moshi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import java.lang.reflect.Type


private val type: Type = Types.newParameterizedType(
    MutableList::class.java,
    PokemonDataModel::class.java
)
private val jsonAdapter: JsonAdapter<List<PokemonDataModel>> = moshi.adapter(type)

fun fromJson(json: String): List<PokemonDataModel>? {
    return jsonAdapter.fromJson(json)
}

fun toJson(list: List<PokemonDataModel>): String {
    return jsonAdapter.toJson(list)
}
