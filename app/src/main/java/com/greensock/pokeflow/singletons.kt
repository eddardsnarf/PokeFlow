package com.greensock.pokeflow

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.greensock.pokeflow.data.local.PokemonDataStore
import com.greensock.pokeflow.data.remote.PokeService
import com.greensock.pokeflow.data.repo.PokeRepository
import com.greensock.pokeflow.ui.MainViewModel
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private var repo: PokeRepository? = null
fun getRepo(context: Context): PokeRepository {
    if (repo == null) {
        repo = PokeRepository(
            PokemonDataStore(context),
            pokeService
        )
    }
    return repo!!
}

private var mainViewModelFactory: ViewModelProvider.Factory? = null
fun getMainViewModeFactory(context: Context): ViewModelProvider.Factory {
    if (mainViewModelFactory == null) {
        mainViewModelFactory = viewModelFactory {
            initializer {
                MainViewModel(getRepo(context))
            }
        }
    }
    return mainViewModelFactory!!
}


val moshi = Moshi.Builder().build()
val pokeService = Retrofit.Builder()
    .baseUrl("https://pokeapi.co/api/v2/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
    .create(PokeService::class.java)

val NAME_LIST = listOf(
    "Abra",
    "Absol",
    "Aerodactyl",
    "Aggron",
    "Aipom",
    "Alakazam",
    "Altaria",
    "Amaldo",
    "Ampharos",
    "Anorith",
    "Arbok",
    "Arcanine",
    "Ariados",
    "Aron",
    "Articuno",
    "Azumarill",
    "Azurill",
    "Bagon",
    "Baltoy",
    "Banette",
    "Barboach",
    "Bayleef",
    "Beautifly",
    "Beedrill",
    "Beldum",
    "Bellossom",
    "Bellsprout",
    "Blastoise",
    "Blaziken",
    "Blissey",
    "Breloom",
    "Bulbasaur",
    "Butterfree",
    "Cleffa",
    "Cloyster",
    "Combusken",
    "Corphish",
    "Corsola",
    "Cradily",
    "Crawdaunt",
    "Crobat",
    "Croconaw",
    "Cubone",
    "Cyndaquil",
    "Delcatty",
    "Delibird",
    "Deoxys",
    "Dewgong",
    "Diglett",
    "Ditto",
    "Dodrio",
    "Doduo",
    "Donaphan",
    "Dragonair",
    "Dragonite",
    "Dratini",
    "Drowzee",
    "Dugtrio",
    "Dunsparce",
    "Dusclops",
    "Duskull",
    "Dustox",
    "Eevee",
    "Ekans",
    "Electabuzz",
    "Electrike",
    "Electrode",
    "Elekid",
    "Entei",
    "Espeon",
    "Exeggcute",
    "Exeggutor",
    "Exploud",
    "Fearow",
    "Feebas",
    "Feraligatr",
    "Flaaffy",
    "Flareon",
    "Flygon",
    "Foretress",
    "Furret",
    "Gardevoir",
    "Gastly",
    "Gengar",
    "Geodude",
    "Girafarig",
    "Glalie",
    "Gligar",
    "Gloom",
    "Golbat",
    "Goldeen",
    "Golduck",
    "Golem",
    "Gorebyss",
    "Granbull",
    "Graveler",
    "Grimer",
    "Groundon",
    "Grovyle",
    "Growlithe",
    "Grumpig",
    "Gulpin",
    "Gyarados",
    "Hariyama",
    "Haunter",
    "Heracross",
    "Hitmonchan",
    "Hitmonlee",
    "Hitmontop",
    "HootHoot",
    "Hoppip",
    "Horsea",
    "Houndoom",
    "Houndour",
    "Huntail",
    "Hypno",
    "Igglybuff",
    "Illumise",
    "Ivysaur",
    "Jigglypuff",
    "Jirachi",
    "Jolteon",
    "Jumpluff",
    "Jynx",
    "Kabuto",
    "Kabutops",
    "Kadabra",
    "Kakuna",
    "Kangaskhan",
    "Kecleon",
    "Kingdra",
    "Kingler",
    "Kirlia",
    "Koffing",
    "Krabby",
    "Kyogre",
    "Lairon",
    "Lanturn",
    "Lapras",
    "Larvitar",
    "Latias",
    "Latios",
    "Cacnea",
    "Cacturne",
    "Camerupt",
    "Carvanha",
    "Cascoon",
    "Castform",
    "Caterpie",
    "Celebi",
    "Chansey",
    "Charizard",
    "Charmander",
    "Charmeleon",
    "Chikorita",
    "Chimecho",
    "Chinchou",
    "Clamperl",
    "Claydol",
    "Clefable",
    "Clefairy",
    "Ledia"
)
