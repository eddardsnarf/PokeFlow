package com.greensock.pokeflow.data.remote

import com.greensock.pokeflow.data.remote.model.PokemonInfoApiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeService {
    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name")pokemon:String): PokemonInfoApiModel
}



