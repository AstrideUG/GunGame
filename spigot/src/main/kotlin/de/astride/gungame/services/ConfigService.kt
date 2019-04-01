package de.astride.gungame.services

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import de.astride.gungame.stats.Action
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonService
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonService.loadAs
import net.darkdevelopers.darkbedrock.darkness.general.functions.asString
import net.darkdevelopers.darkbedrock.darkness.spigot.configs.gson.BukkitGsonConfig
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toMaterial
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.SECONDARY
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.TEXT
import org.bukkit.Bukkit
import org.bukkit.Material
import java.io.File
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 29.03.2019 13:42.
 * Current Version: 1.0 (29.03.2019 - 01.04.2019)
 */
class ConfigService(private val directory: File) {

    val config by lazy { Config() }
    val shops by lazy { Shops() }
    val maps by lazy { Maps() }
    val actions by lazy { Actions() }

    inner class Config internal constructor() {

        /* Main */
        private val configData = ConfigData(directory, "config.json")
        private val jsonObject = loadAs(configData) ?: JsonObject()

        /* SubClass */
        val files by lazy { Files(jsonObject[Files::class.java.simpleName]?.asJsonObject) }
        val commands by lazy { Commands(jsonObject[Commands::class.java.simpleName]?.asJsonObject) }
        val shopItems by lazy { ShopItems(jsonObject[ShopItems::class.java.simpleName]?.asJsonObject) }

        inner class Files internal constructor(jsonObject: JsonObject?) {

            /* Values */
            val maps by lazy { jsonObject?.get("maps")?.asString() ?: "maps.json" }
            val shops by lazy { jsonObject?.get("shops")?.asString() ?: "shops.json" }

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
                val name by lazy { jsonObject?.get("name")?.asString() ?: "Stats" }
                val permission by lazy { jsonObject?.get("permission")?.asString() ?: "gungame.commands.stats" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

            inner class StatsReset internal constructor(jsonObject: JsonObject?) {

                /* Values */
                val name by lazy { jsonObject?.get("name")?.asString() ?: "StatsReset" }
                val permission by lazy { jsonObject?.get("permission")?.asString() ?: "gungame.commands.statsReset" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

            inner class Team internal constructor(jsonObject: JsonObject?) {

                /* Values */
                val name by lazy { jsonObject?.get("name")?.asString() ?: "Team" }
                val permission by lazy { jsonObject?.get("permission")?.asString() ?: "gungame.commands.team" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

            inner class Teams internal constructor(jsonObject: JsonObject?) {

                /* Values */
                val name by lazy { jsonObject?.get("name")?.asString() ?: "Teams" }
                val permission by lazy { jsonObject?.get("permission")?.asString() ?: "gungame.commands.teams" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

            inner class Top internal constructor(jsonObject: JsonObject?) {

                /* Values */
                val name by lazy { jsonObject?.get("name")?.asString() ?: "Top" }
                val permission by lazy { jsonObject?.get("permission")?.asString() ?: "gungame.commands.top" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

        }

        inner class ShopItems internal constructor(jsonObject: JsonObject?) {

            /* SubClass */
            val instantKiller by lazy { InstantKiller(jsonObject?.get(Commands.Stats::class.java.simpleName)?.asJsonObject) }
            val keepInventory by lazy { KeepInventory(jsonObject?.get(Commands.StatsReset::class.java.simpleName)?.asJsonObject) }
            val levelUp by lazy { LevelUp(jsonObject?.get(Commands.Team::class.java.simpleName)?.asJsonObject) }
            val magicHeal by lazy { MagicHeal(jsonObject?.get(Commands.Teams::class.java.simpleName)?.asJsonObject) }

            inner class InstantKiller(jsonObject: JsonObject?) {

                /* Values */
                val material by lazy { jsonObject?.get("material")?.asString()?.toMaterial() ?: Material.FIREBALL }
                val damage by lazy { jsonObject?.get("damage")?.asShort ?: 0 }
                val name by lazy { jsonObject?.get("name")?.asString() ?: "${SECONDARY}Instant Killer" }
                val lore by lazy {
                    val element = jsonObject?.get("lore")
                    val jsonArray = element as? JsonArray
                    val single = element?.asString() ?: "${TEXT}Töte einen Spieler sofort"
                    jsonArray?.mapNotNull { it.asString() } ?: listOf(single)
                }
                val delay by lazy { jsonObject?.get("delay")?.asLong ?: 300 }
                val price by lazy { jsonObject?.get("price")?.asInt ?: 500 }

            }

            inner class KeepInventory(jsonObject: JsonObject?) {

                /* Values */
                val material by lazy { jsonObject?.get("material")?.asString()?.toMaterial() ?: Material.PAPER }
                val damage by lazy { jsonObject?.get("damage")?.asShort ?: 0 }
                val name by lazy { jsonObject?.get("name")?.asString() ?: "${SECONDARY}KeepInventory" }
                val lore by lazy {
                    val element = jsonObject?.get("lore")
                    val jsonArray = element as? JsonArray
                    val single = element?.asString() ?: "${TEXT}Behalte nach deinem Tot deine Items"
                    jsonArray?.mapNotNull { it.asString() } ?: listOf(single)
                }
                val delay by lazy { jsonObject?.get("delay")?.asLong ?: 300 }
                val price by lazy { jsonObject?.get("price")?.asInt ?: 500 }

            }

            inner class LevelUp(jsonObject: JsonObject?) {

                /* Values */
                val material by lazy { jsonObject?.get("material")?.asString()?.toMaterial() ?: Material.DIAMOND }
                val damage by lazy { jsonObject?.get("damage")?.asShort ?: 0 }
                val name by lazy { jsonObject?.get("name")?.asString() ?: "${SECONDARY}LevelUp" }
                val lore by lazy {
                    val element = jsonObject?.get("lore")
                    val jsonArray = element as? JsonArray
                    val single = element?.asString() ?: "${TEXT}Erhöht dein Level um 5"
                    jsonArray?.mapNotNull { it.asString() } ?: listOf(single)
                }
                val delay by lazy { jsonObject?.get("delay")?.asLong ?: 60 }
                val price by lazy { jsonObject?.get("price")?.asInt ?: 100 }

            }

            inner class MagicHeal(jsonObject: JsonObject?) {

                /* Values */
                val material by lazy { jsonObject?.get("material")?.asString()?.toMaterial() ?: Material.INK_SACK }
                val damage by lazy { jsonObject?.get("damage")?.asShort ?: 0 }
                val name by lazy { jsonObject?.get("name")?.asString() ?: "${SECONDARY}Magic Heal" }
                val lore by lazy {
                    val element = jsonObject?.get("lore")
                    val jsonArray = element as? JsonArray
                    val single = element?.asString() ?: "${TEXT}Er regeneriert dich sofort"
                    jsonArray?.mapNotNull { it.asString() } ?: listOf(single)
                }
                val delay by lazy { jsonObject?.get("delay")?.asLong ?: 30 }
                val price by lazy { jsonObject?.get("price")?.asInt ?: 50 }

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

    inner class Actions internal constructor() {

        /* Main */
        private val configData = ConfigData(directory, config.files.shops)
        private val jsonObject = loadAs(configData) ?: JsonObject()

        /* Values */
        fun load(): MutableMap<UUID, MutableList<Action>> = jsonObject.entrySet().map { (key, value) ->
            UUID.fromString(key) to value.asJsonArray.map { element ->
                element.asJsonObject.run {
                    Action(
                        this["id"].asString,
                        entrySet().map { it.key to it.value }.toMap() - "id" - "timestamp",
                        this["timestamp"].asLong
                    )
                }
            }.toMutableList()
        }.toMap().toMutableMap()

        fun save(input: MutableMap<UUID, MutableList<Action>>) {
            val b = JsonObject()

            input.forEach { (key, actions) ->
                val jsonObject1 = JsonObject()
                jsonObject1.add(key.toString(), JsonArray().apply {
                    actions.forEach {
                        add(JsonObject().apply {
                            it.meta.forEach {
                                addProperty(it.key, GsonBuilder().setPrettyPrinting().create().toJson(it.value))
                            }
                            addProperty("id", it.id)
                            addProperty("timestamp", it.timestamp)
                        })
                    }
                })
            }

            GsonService.save(configData, b)
        }


    }

    companion object {

        val instance get() = Bukkit.getServicesManager()?.getRegistration(ConfigService::class.java)?.provider!!

    }

}