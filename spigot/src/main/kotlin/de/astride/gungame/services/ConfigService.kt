package de.astride.gungame.services

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonService
import net.darkdevelopers.darkbedrock.darkness.general.functions.asString
import net.darkdevelopers.darkbedrock.darkness.spigot.configs.gson.BukkitGsonConfig
import org.bukkit.Bukkit
import java.io.File

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 29.03.2019 13:42.
 * Current Version: 1.0 (29.03.2019 - 01.04.2019)
 */
class ConfigService(private val directory: File) {

    val config by lazy { Config() }
    val shops by lazy { Shops() }
    val maps by lazy { Maps() }

    inner class Config internal constructor() {

        /* Main */
        private val configData = ConfigData(directory, "config.json")
        private val jsonObject = GsonService.loadAsJsonObject(configData)

        /* SubClass */
        val files by lazy { Files(jsonObject[Files::class.java.simpleName]?.asJsonObject) }
        val commands by lazy { Commands(jsonObject[Commands::class.java.simpleName]?.asJsonObject) }

        inner class Files internal constructor(jsonObject: JsonObject?) {

            /* Values */
            val maps by lazy { jsonObject?.get("maps")?.asString ?: "maps.json" }
            val shops by lazy { jsonObject?.get("shops")?.asString ?: "shops.json" }

        }

        inner class Commands internal constructor(jsonObject: JsonObject?) {

            /* SubClass */
            val stats by lazy { Stats(jsonObject?.get(Stats::class.java.simpleName)?.asJsonObject) }
            val statsReset by lazy { StatsReset(jsonObject?.get(StatsReset::class.java.simpleName)?.asJsonObject) }
            val team by lazy { Team(jsonObject?.get(Team::class.java.simpleName)?.asJsonObject) }
            val teams by lazy { Teams(jsonObject?.get(Teams::class.java.simpleName)?.asJsonObject) }
            val top by lazy { Top(jsonObject?.get(Top::class.java.simpleName)?.asJsonObject) }

            inner class Stats internal constructor(jsonObject: JsonObject?) {

                /* Values */
                val name by lazy { jsonObject?.get("name")?.asString ?: "Stats" }
                val permission by lazy { jsonObject?.get("permission")?.asString ?: "gungame.commands.stats" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

            inner class StatsReset internal constructor(jsonObject: JsonObject?) {

                /* Values */
                val name by lazy { jsonObject?.get("name")?.asString ?: "StatsReset" }
                val permission by lazy { jsonObject?.get("permission")?.asString ?: "gungame.commands.statsReset" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

            inner class Team internal constructor(jsonObject: JsonObject?) {

                /* Values */
                val name by lazy { jsonObject?.get("name")?.asString ?: "Team" }
                val permission by lazy { jsonObject?.get("permission")?.asString ?: "gungame.commands.team" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

            inner class Teams internal constructor(jsonObject: JsonObject?) {

                /* Values */
                val name by lazy { jsonObject?.get("name")?.asString ?: "Teams" }
                val permission by lazy { jsonObject?.get("permission")?.asString ?: "gungame.commands.teams" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

            inner class Top internal constructor(jsonObject: JsonObject?) {

                /* Values */
                val name by lazy { jsonObject?.get("name")?.asString ?: "Top" }
                val permission by lazy { jsonObject?.get("permission")?.asString ?: "gungame.commands.top" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

        }

    }

    inner class Maps internal constructor() {

        /* Main */
        private val configData = ConfigData(directory, config.files.maps)
        val maps = GsonService.load(configData) as? JsonArray ?: JsonArray()

        /* Values */
        val bukkitGsonConfig = BukkitGsonConfig(configData)

    }

    inner class Shops internal constructor() {

        /* Main */
        private val configData = ConfigData(directory, config.files.shops)
        private val jsonArray = GsonService.load(configData) as? JsonArray ?: JsonArray()
        private val bukkitGsonConfig = BukkitGsonConfig(configData)

        /* Values */
        val locations = jsonArray.map { bukkitGsonConfig.getLocation(it.asJsonObject) }

    }

    companion object {

        val instance get() = Bukkit.getServicesManager()?.getRegistration(ConfigService::class.java)?.provider!!

    }

}