package de.astride.gungame.services

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import de.astride.gungame.functions.AllowTeams
import de.astride.gungame.stats.Action
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import kotlinx.serialization.stringify
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonConfig
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonService
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonService.loadAs
import net.darkdevelopers.darkbedrock.darkness.general.functions.asString
import net.darkdevelopers.darkbedrock.darkness.general.message.GsonMessages
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
 * Current Version: 1.0 (29.03.2019 - 05.04.2019)
 */
class ConfigService(private val directory: File) {

    val config by lazy { Config() }
    val shops by lazy { Shops() }
    val maps by lazy { Maps() }
    val actions by lazy { Actions() }
    val messages by lazy { Messages() }

    inner class Config internal constructor() {

        /* Main */
        private val configData = ConfigData(directory, "config.json")
        private val jsonObject = loadAs(configData) ?: JsonObject()

        /* Values */
        val allowTeams by lazy { AllowTeams.byName(jsonObject["allow-teams"]?.asString()) }

        /* SubClass */
        val files by lazy { Files(jsonObject[Files::class.java.simpleName]?.asJsonObject) }
        val commands by lazy { Commands(jsonObject[Commands::class.java.simpleName]?.asJsonObject) }
        val shopItems by lazy { ShopItems(jsonObject[ShopItems::class.java.simpleName]?.asJsonObject) }

        init {
            //Very bad code but it works!
            if (jsonObject["allow-teams"] == null ||
                files.jsonObject == null ||
                commands.jsonObject == null ||
                commands.stats.jsonObject == null ||
                commands.statsReset.jsonObject == null ||
                commands.team.jsonObject == null ||
                commands.teams.jsonObject == null ||
                commands.top.jsonObject == null ||
                commands.gungame.jsonObject == null ||
                shopItems.jsonObject == null ||
                shopItems.instantKiller.jsonObject == null ||
                shopItems.keepInventory.jsonObject == null ||
                shopItems.levelUp.jsonObject == null ||
                shopItems.magicHeal.jsonObject == null
            ) {

                GsonService.save(configData, JsonObject().apply {
                    addProperty("allow-teams", allowTeams.type)
                    add(Files::class.simpleName, JsonObject().apply {
                        addProperty("maps", files.maps)
                        addProperty("shops", files.shops)
                        addProperty("actions", files.actions)
                    })
                    add(Commands::class.simpleName, JsonObject().apply {
                        add(Commands.Stats::class.simpleName, JsonObject().apply {
                            val command = commands.stats
                            addProperty("name", command.name)
                            addProperty("permission", command.permission)
                            add("aliases", command.aliases.toJsonArray())
                        })
                        add(Commands.StatsReset::class.simpleName, JsonObject().apply {
                            val command = commands.statsReset

                            addProperty("name", command.name)
                            addProperty("permission", command.permission)
                            add("aliases", command.aliases.toJsonArray())
                        })
                        add(Commands.Team::class.simpleName, JsonObject().apply {
                            val command = commands.team
                            addProperty("name", command.name)
                            addProperty("permission", command.permission)
                            add("aliases", command.aliases.toJsonArray())
                        })
                        add(Commands.Teams::class.simpleName, JsonObject().apply {
                            val command = commands.teams
                            addProperty("name", command.name)
                            addProperty("permission", command.permission)
                            add("aliases", command.aliases.toJsonArray())
                        })
                        add(Commands.Top::class.simpleName, JsonObject().apply {
                            val command = commands.top
                            addProperty("name", command.name)
                            addProperty("permission", command.permission)
                            add("aliases", command.aliases.toJsonArray())
                        })
                        add(Commands.GunGame::class.simpleName, JsonObject().apply {
                            val command = commands.gungame
                            addProperty("name", command.name)
                            addProperty("permission", command.permission)
                            add("aliases", command.aliases.toJsonArray())
                        })
                    })
                    add(ShopItems::class.simpleName, JsonObject().apply {
                        add(ShopItems.InstantKiller::class.simpleName, JsonObject().apply {
                            val item = shopItems.instantKiller
                            addProperty("material", item.material.name)
                            addProperty("damage", item.damage)
                            addProperty("name", item.name)
                            add("lore", item.lore.toJsonArray())
                            addProperty("delay", item.delay)
                            addProperty("price", item.price)
                        })
                        add(ShopItems.KeepInventory::class.simpleName, JsonObject().apply {
                            val item = shopItems.keepInventory
                            addProperty("material", item.material.name)
                            addProperty("damage", item.damage)
                            addProperty("name", item.name)
                            add("lore", item.lore.toJsonArray())
                            addProperty("delay", item.delay)
                            addProperty("price", item.price)
                        })
                        add(ShopItems.LevelUp::class.simpleName, JsonObject().apply {
                            val item = shopItems.levelUp
                            addProperty("material", item.material.name)
                            addProperty("damage", item.damage)
                            addProperty("name", item.name)
                            add("lore", item.lore.toJsonArray())
                            addProperty("delay", item.delay)
                            addProperty("price", item.price)
                        })
                        add(ShopItems.MagicHeal::class.simpleName, JsonObject().apply {
                            val item = shopItems.magicHeal
                            addProperty("material", item.material.name)
                            addProperty("damage", item.damage)
                            addProperty("name", item.name)
                            add("lore", item.lore.toJsonArray())
                            addProperty("delay", item.delay)
                            addProperty("price", item.price)
                        })
                    })
                })

            }
        }

        inner class Files internal constructor(val jsonObject: JsonObject?) {

            /* Values */
            val maps by lazy { file("maps") }
            val shops by lazy { file("shops") }
            val actions by lazy { file("actions") }
            val messages by lazy { file("messages") }

            /**
             * @author Lars Artmann | LartyHD
             * Created by Lars Artmann | LartyHD on 01.04.2019 14:29.
             * Current Version: 1.0 (01.04.2019 - 01.04.2019)
             */
            private fun file(name: String, suffix: String = ".json") =
                jsonObject?.get(name)?.asString() ?: "$name$suffix"

        }

        inner class Commands internal constructor(val jsonObject: JsonObject?) {

            /* SubClass */
            val stats by lazy { Stats(jsonObject?.get(Stats::class.java.simpleName)?.asJsonObject) }
            val statsReset by lazy { StatsReset(jsonObject?.get(StatsReset::class.java.simpleName)?.asJsonObject) }
            val team by lazy { Team(jsonObject?.get(Team::class.java.simpleName)?.asJsonObject) }
            val teams by lazy { Teams(jsonObject?.get(Teams::class.java.simpleName)?.asJsonObject) }
            val top by lazy { Top(jsonObject?.get(Top::class.java.simpleName)?.asJsonObject) }
            val gungame by lazy { GunGame(jsonObject?.get(GunGame::class.java.simpleName)?.asJsonObject) }

            inner class Stats internal constructor(val jsonObject: JsonObject?) {

                /* Values */
                val name by lazy { jsonObject?.get("name")?.asString() ?: "Stats" }
                val permission by lazy { jsonObject?.get("permission")?.asString() ?: "gungame.commands.stats" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

            inner class StatsReset internal constructor(val jsonObject: JsonObject?) {

                /* Values */
                val name by lazy { jsonObject?.get("name")?.asString() ?: "StatsReset" }
                val permission by lazy { jsonObject?.get("permission")?.asString() ?: "gungame.commands.statsReset" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

            inner class Team internal constructor(val jsonObject: JsonObject?) {

                /* Values */
                val name by lazy { jsonObject?.get("name")?.asString() ?: "Team" }
                val permission by lazy { jsonObject?.get("permission")?.asString() ?: "gungame.commands.team" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

            inner class Teams internal constructor(val jsonObject: JsonObject?) {

                /* Values */
                val name by lazy { jsonObject?.get("name")?.asString() ?: "Teams" }
                val permission by lazy { jsonObject?.get("permission")?.asString() ?: "gungame.commands.teams" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

            inner class Top internal constructor(val jsonObject: JsonObject?) {

                /* Values */
                val name by lazy { jsonObject?.get("name")?.asString() ?: "Top" }
                val permission by lazy { jsonObject?.get("permission")?.asString() ?: "gungame.commands.top" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

            /**
             * @author Lars Artmann | LartyHD
             * Created by Lars Artmann | LartyHD on 04.04.2019 18:36.
             * Current Version: 1.0 (04.04.2019 - 04.04.2019)
             */
            inner class GunGame internal constructor(val jsonObject: JsonObject?) {

                /* Values */
                val name by lazy { jsonObject?.get("name")?.asString() ?: "GunGame" }
                val permission by lazy { jsonObject?.get("permission")?.asString() ?: "gungame.commands.gungame" }
                val aliases by lazy {
                    val jsonArray = jsonObject?.get("aliases") as? JsonArray ?: JsonArray()
                    jsonArray.mapNotNull { it.asString() }.toTypedArray()
                }

            }

        }

        inner class ShopItems internal constructor(val jsonObject: JsonObject?) {

            /* SubClass */
            val instantKiller by lazy { InstantKiller(jsonObject?.get(Commands.Stats::class.java.simpleName)?.asJsonObject) }
            val keepInventory by lazy { KeepInventory(jsonObject?.get(Commands.StatsReset::class.java.simpleName)?.asJsonObject) }
            val levelUp by lazy { LevelUp(jsonObject?.get(Commands.Team::class.java.simpleName)?.asJsonObject) }
            val magicHeal by lazy { MagicHeal(jsonObject?.get(Commands.Teams::class.java.simpleName)?.asJsonObject) }

            inner class InstantKiller(val jsonObject: JsonObject?) {

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

            inner class KeepInventory(val jsonObject: JsonObject?) {

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

            inner class LevelUp(val jsonObject: JsonObject?) {

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

            inner class MagicHeal(val jsonObject: JsonObject?) {

                /* Values */
                val material by lazy { jsonObject?.get("material")?.asString()?.toMaterial() ?: Material.INK_SACK }
                val damage by lazy { jsonObject?.get("damage")?.asShort ?: 1 }
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
        val configData: ConfigData = ConfigData(directory, config.files.actions)

        /* Values */
        fun load(configData: ConfigData = this.configData): MutableMap<UUID, MutableList<Action>> =
            Json.parse(configData.file.readText())

        fun save(input: MutableMap<UUID, MutableList<Action>>, configData: ConfigData = this.configData) =
            GsonService.save(configData, Json.stringify(input))

    }

    inner class Messages internal constructor() {

        /* Main */
        private val configData = ConfigData(directory, config.files.messages)
        private val gsonConfig = @Suppress("DEPRECATION") GsonConfig(configData).load()

        /* Values */
        val availableMessages = GsonMessages(gsonConfig).availableMessages

    }

    companion object {

        val instance get() = Bukkit.getServicesManager()?.getRegistration(ConfigService::class.java)?.provider!!

    }

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 01.04.2019 16:20.
 * Current Version: 1.0 (01.04.2019 - 01.04.2019)
 */
private fun Array<String>.toJsonArray() = JsonArray().apply { this@toJsonArray.forEach { add(JsonPrimitive(it)) } }

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 01.04.2019 16:14.
 * Current Version: 1.0 (01.04.2019 - 01.04.2019)
 */
private fun Collection<String>.toJsonArray() = toTypedArray().toJsonArray()