package de.astride.gungame.services

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import de.astride.data.ItemStackSerializer
import de.astride.data.UUIDSerializer
import de.astride.gungame.functions.AllowTeams
import de.astride.gungame.functions.allActions
import de.astride.gungame.functions.messages
import de.astride.gungame.kits.DefaultKits
import de.astride.gungame.stats.Action
import kotlinx.serialization.KSerializer
import kotlinx.serialization.context.getOrDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.json
import kotlinx.serialization.list
import kotlinx.serialization.map
import kotlinx.serialization.stringify
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonConfig
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonService
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonService.loadAs
import net.darkdevelopers.darkbedrock.darkness.general.functions.asString
import net.darkdevelopers.darkbedrock.darkness.spigot.configs.gson.BukkitGsonConfig
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toMaterial
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.SECONDARY
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.TEXT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.SpigotGsonMessages
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import de.astride.gungame.kits.kits as allKits

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 29.03.2019 13:42.
 * Current Version: 1.0 (29.03.2019 - 12.04.2019)
 */
class ConfigService(private val directory: File) {

    val config by lazy { Config() }
    val shops by lazy { Shops() }
    val maps by lazy { Maps() }
    val actions by lazy { Actions() }
    val kits by lazy { Kits() }

    init {
        Messages()
    }

    inner class Config internal constructor() {

        /* Main */
        private val configData = ConfigData(directory, "config.json")
        private val jsonObject = loadAs(configData) ?: JsonObject()

        /* Values */
        val allowTeams by lazy { AllowTeams.byName(jsonObject["allow-teams"]?.asString()) }
        val rewards: Map<String, Double>  by lazy {
            (jsonObject["rewards"] as? JsonObject)?.entrySet()?.mapNotNull {
                try {
                    it.key to it.value.asDouble
                } catch (ex: Exception) {
                    null
                }
            }?.toMap() ?: mapOf("PlayerRespawnEvent" to 5.0)
        }

        /* SubClass */
        val files by lazy { Files(jsonObject[Files::class.java.simpleName]?.asJsonObject) }
        val commands by lazy { Commands(jsonObject[Commands::class.java.simpleName]?.asJsonObject) }
        val shopItems by lazy { ShopItems(jsonObject[ShopItems::class.java.simpleName]?.asJsonObject) }

        init {
            //Very bad code but it works!
            if (jsonObject["allow-teams"] == null ||
                jsonObject["rewards"] == null ||
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
                    add("rewards", JsonObject().apply { rewards.forEach { key, value -> addProperty(key, value) } })
                    add(Files::class.simpleName, JsonObject().apply {
                        addProperty("maps", files.maps)
                        addProperty("shops", files.shops)
                        addProperty("actions", files.actions)
                        addProperty("messages", files.messages)
                        addProperty("kits", files.kits)
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
                            addProperty("permission-other", command.permissionOther)
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
                            addProperty("delay", command.delay)
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
            val kits by lazy { file("kits") }

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
                val permission by lazy { jsonObject?.get("permission")?.asString() ?: "gungame.commands.statsreset" }
                val permissionOther by lazy {
                    jsonObject?.get("permission-other")?.asString() ?: "gungame.commands.statsreset.other"
                }
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
                val delay: Long by lazy { jsonObject?.get("delay")?.asLong ?: TimeUnit.MINUTES.toMillis(5) }

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
        private val kSerializer: KSerializer<Map<UUID, List<Action>>> =
            (UUIDSerializer to Json.context.getOrDefault(Action::class).list).map

        /* Values */
        fun load(configData: ConfigData = this.configData): MutableMap<UUID, MutableList<Action>> {
            val string = configData.file.readText()
            return if (string.isEmpty()) mutableMapOf() else Json.parse(
                kSerializer,
                string
            ).map { it.key to it.value.toMutableList() }.toMap().toMutableMap()
        }

        fun save(input: MutableMap<UUID, MutableList<Action>> = allActions, configData: ConfigData = this.configData) =
            GsonService.save(configData, Json.indented.stringify(kSerializer, input))

    }

    inner class Messages internal constructor() {

        /* Main */
        private val configData = ConfigData(directory, config.files.messages)
        private val gsonConfig = @Suppress("DEPRECATION") GsonConfig(configData).load()

        /* Values */
        private val spigotGsonMessages by lazy { SpigotGsonMessages(gsonConfig) }
        internal val available by lazy { spigotGsonMessages.availableMessages }
        val name by lazy { available["name"]?.firstOrNull() ?: "GunGame" }
        val prefix by lazy {
            available["prefix"]?.firstOrNull()
                ?: "%Colors.PRIMARY%%Colors.EXTRA%%name% %Colors.IMPORTANT%┃ %Colors.RESET%"
        }
        val shopName by lazy { available["shop-name"]?.firstOrNull() ?: "%Colors.SECONDARY%Shop" }
        val teamsAllow by lazy { available["teams-allow"]?.firstOrNull() ?: "erlaubt" }
        val teamsDisAllow by lazy { available["teams-dis-allow"]?.firstOrNull() ?: "verboten" }
        val scoreboardDisplayName by lazy {
            available["scoreboard-displayname"]?.firstOrNull() ?: "%Colors.PRIMARY%%Colors.EXTRA%GunGame"
        }
        val scoreboardScores by lazy {
            available["scoreboard-scores"] ?: listOf(
                " ",
                "%Colors.TEXT%Map%Colors.IMPORTANT%:",
                "%Colors.IMPORTANT%@map@",
                "  ",
                "%Colors.TEXT%Teams%Colors.IMPORTANT%:",
                "%Colors.IMPORTANT%@allow-teams@",
                "   ",
                "%Colors.TEXT%Rang%Colors.IMPORTANT%:",
                "%Colors.IMPORTANT%@rank@",
                "    ",
                "%Colors.TEXT%Points%Colors.IMPORTANT%:",
                "%Colors.IMPORTANT%@points@ ",
                "     ",
                "%Colors.TEXT%Nutze%Colors.IMPORTANT%: /Stats",
                "%Colors.TEXT%für mehr Stats"
            )
        }
        val hologram by lazy {
            available["hologram"] ?: listOf(
                "%Colors.TEXT%-= %gungame.stats% =-",
                "",
                "%Colors.TEXT%Dein Platz%Separator.Stats%@rank@",
                "%Colors.TEXT%Kills%Separator.Stats%@kills@",
                "%Colors.TEXT%Tode%Separator.Stats%@deaths@",
                "%Colors.TEXT%K/D%Separator.Stats%@kd@"
            )
        }
        val addAction by lazy { available["add-action"] ?: listOf() }

        /* SubClass */
        val commands by lazy { Commands() }
        val shop by lazy { Shop() }
        val regions by lazy { Regions() }

        init {
            messagesInstance = this

            //Very bad code but it works!
            if (available.isEmpty()) {
                GsonService.save(configData, Json.indented.stringify(json {
                    "Messages" to json {
                        "language" to "de_DE"
                        "languages" to json {
                            "de_DE" to json {
                                "Colors.PRIMARY" to "\u0026b"
                                "Colors.SECONDARY" to "\u00269"
                                "Colors.IMPORTANT" to "\u0026f"
                                "Colors.TEXT" to "\u00267"
                                "Colors.EXTRA" to "\u0026l"
                                "Colors.DESIGN" to "\u0026m"
                                "Colors.RESET" to "\u0026r"
                                "Colors.WARNING" to "\u0026c"
                                "name" to name
                                "prefix" to prefix
                                "Prefix.Text" to "%prefix%%Colors.TEXT%"
                                "Prefix.Important" to "%prefix%%Colors.IMPORTANT%"
                                "Prefix.Warning" to "%prefix%%Colors.WARNING%"
                                "Separator.Line" to "%Prefix.Text%%Colors.DESIGN%                                                               "
                                "Separator.Stats" to "%Colors.IMPORTANT%: %Colors.PRIMARY%"
                                "gungame.stats" to "%Colors.IMPORTANT%GunGame Stats%Colors.TEXT%"
                                "gungame.stats.by" to "von %Colors.IMPORTANT%@sender@%Colors.TEXT%"
                                "shop-name" to shopName
                                "teams-allow" to teamsAllow
                                "teams-dis-allow" to teamsDisAllow
                                "scoreboard-displayname" to scoreboardDisplayName
                                "scoreboard-scores" to scoreboardScores.toJsonArray()
                                "hologram" to hologram.toJsonArray()
                                "commands.gungame.successfully" to commands.gungame.successfully.toJsonArray()
                                "commands.stats.failed.use-this-if-you-are-not-a-player" to commands.stats.failedPlayer.toJsonArray()
                                "commands.stats.successfully" to commands.stats.successfully.toJsonArray()
                                "commands.statsreset.info.confirm" to commands.statsReset.infoConfirm.toJsonArray()
                                "commands.statsreset.successfully.self.stats-were-reset" to commands.statsReset.successfullySelf.toJsonArray()
                                "commands.statsreset.successfully.self.by.stats-were-reset" to commands.statsReset.successfullySelfBy.toJsonArray()
                                "commands.statsreset.successfully.target.stats-were-reset" to commands.statsReset.successfullyTarget.toJsonArray()
                                "commands.statsreset.failed.use-this-if-you-are-not-a-player" to commands.statsReset.failedPlayer.toJsonArray()
                                "commands.statsreset.failed.self.nothing-to-reset" to commands.statsReset.failedSelfNothing.toJsonArray()
                                "commands.statsreset.failed.target.nothing-to-reset" to commands.statsReset.failedTargetNothing.toJsonArray()
                                "commands.team.failed.teams-not-allowed" to commands.team.failedTeamsNotAllowed.toJsonArray()
                                "commands.teams.successfully" to commands.teams.successfully
                                "commands.teams.title" to commands.teams.title
                                "commands.teams.sub-title" to commands.teams.subTitle
                                "commands.teams.failed.delay" to commands.teams.failedDelay.toJsonArray()
                                "commands.top.success" to commands.top.success.toJsonArray()
                                "commands.top.successfully" to commands.top.successfully.toJsonArray()
                                "commands.top.entry" to commands.top.entry.toJsonArray()
                                "shop.delayed" to shop.delayed.toJsonArray()
                                "shop.price-lore" to shop.priceLore.toJsonArray()
                                "shop.max-count" to shop.maxCount.toJsonArray()
                                "shop.max-health" to shop.maxHealth.toJsonArray()
                                "shop.max-level" to shop.maxLevel.toJsonArray()
                                "shop.money.successfully" to shop.money.successfully.toJsonArray()
                                "shop.money.failed" to shop.money.failed.toJsonArray()
                                "shop.keepInventory.successfully" to shop.keepInventory.successfully.toJsonArray()
                                "shop.keepInventory.failed" to shop.keepInventory.failed.toJsonArray()
                                "regions.damage-in-player" to regions.damageInPlayer.toJsonArray()
                                "regions.damage-in-target" to regions.damageInTarget.toJsonArray()
                                "regions.launch-arrow" to regions.launchArrow.toJsonArray()
                            }
                        }
                    }
                }))

                Messages()

            }

            net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages.NAME.message = messages.name
            net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages.PREFIX.message = messages.prefix

        }

        /**
         * @author Lars Artmann | LartyHD
         * Created by Lars Artmann | LartyHD on 06.04.2019 02:46.
         * Current Version: 1.0 (06.04.2019 - 06.04.2019)
         */
        private fun List<String?>.toJsonArray() =
            kotlinx.serialization.json.JsonArray(this.map { kotlinx.serialization.json.JsonPrimitive(it) })

        inner class Commands internal constructor() {

            internal val prefix get() = "${javaClass.simpleName!!}.".toLowerCase()
            internal val Any.prefix get() = "${messages.commands.prefix}${javaClass.simpleName!!}.".toLowerCase()

            /* SubClass */
            val gungame by lazy { GunGame() }
            val stats by lazy { Stats() }
            val statsReset by lazy { StatsReset() }
            val team by lazy { Team() }
            val teams by lazy { Teams() }
            val top by lazy { Top() }

            inner class GunGame internal constructor() {

                /* Values */
                val successfully by lazy {
                    messages.available["${prefix}successfully"]
                        ?: listOf("%Prefix.Text%@arg1@ wurden in \"@path@\" abgespeichert.")
                }

            }

            inner class Stats internal constructor() {

                /* Values */
                val failedPlayer by lazy {
                    messages.available["${prefix}failed.use-this-if-you-are-not-a-player"]
                        ?: listOf("%Prefix.Warning%Nutze als nicht Spieler: /@command-name@ <Spieler>.")
                }
                val successfully by lazy {

                    fun entry(p1: String, p2: String) = "%Prefix.Text%$p1%Separator.Stats%@$p2@"

                    val lineSeparator = "%Separator.Line%"
                    messages.available["${prefix}successfully"]
                        ?: listOf(
                            "%Prefix.Important%%Colors.DESIGN%                         %Colors.IMPORTANT%[ %Colors.PRIMARY%%Colors.EXTRA%STATS%Colors.IMPORTANT% ]%Colors.DESIGN%                         ",
                            "%Prefix.Important%%Colors.DESIGN%                   %Colors.IMPORTANT%[ %Colors.PRIMARY%%Colors.EXTRA%@name@%Colors.IMPORTANT% ]%Colors.DESIGN%                   ",
                            entry("Rank", "rank"),
                            entry("Points", "points"),
                            lineSeparator,
                            entry("Deaths", "deaths"),
                            entry("Kills", "kills"),
                            entry("K/D", "kd"),
                            lineSeparator,
                            entry("DeathStreak", "death-streak"),
                            entry("KillStreak", "kill-streak"),
                            lineSeparator,
                            entry("MaxDeathStreak", "max-death-streak"),
                            entry("MaxKillStreak", "max-kill-streak"),
                            lineSeparator,
                            entry("Bought LevelUps", "bought-levelup"),
                            entry("Bought MagicHeal", "bought-magicheal"),
                            entry("Bought InstantKiller", "bought-instantkiller"),
                            entry("Bought KeepInventory", "bought-keepinventory"),
                            lineSeparator,
                            entry("Used MagicHeal", "used-magicheal"),
                            entry("Used InstantKiller", "used-instantkiller"),
                            entry("Used KeepInventory", "used-keepinventory"),
                            lineSeparator,
                            entry("Changed Shop Color", "shop-color-changes"),
                            entry("Shop openings", "shop-openings"),
                            "%Prefix.Important%%Colors.DESIGN%                         %Colors.IMPORTANT%[ %Colors.PRIMARY%%Colors.EXTRA%STATS%Colors.IMPORTANT% ]%Colors.DESIGN%                         "
                        )

                }

            }

            inner class StatsReset internal constructor() {

                /* Values */
                val infoConfirm by lazy {
                    messages.available["${prefix}info.confirm"] ?: listOf(
                        "",
                        "%Prefix.Text%Nutze %Colors.IMPORTANT%\"/@command-name@ [Spieler] @confirmKey@\"%Colors.TEXT% um die @gungame.stats@ zurückzusetzen",
                        ""
                    )
                }

                val failedPlayer by lazy {
                    messages.available["${prefix}failed.use-this-if-you-are-not-a-player"]
                        ?: listOf("%Prefix.Warning%Nutze als nicht Spieler: /@command-name@ <Spieler>.")
                }

                val failedSelfNothing by lazy {
                    messages.available["${prefix}failed.self.nothing-to-reset"]
                        ?: listOf("%Prefix.Warning%Du hast keine Stats die man resetten könnte!")
                }

                val failedTargetNothing by lazy {
                    messages.available["${prefix}failed.target.nothing-to-reset"]
                        ?: listOf("%Prefix.Warning%@target@ hat keine Stats die man resetten könnte!")
                }

                val successfullySelf by lazy {
                    messages.available["${prefix}successfully.self.stats-were-reset"] ?: listOf(
                        "",
                        "%Prefix.Text%Deine @gungame.stats@ wurden zurückgesetzt",
                        ""
                    )
                }

                val successfullySelfBy by lazy {
                    messages.available["${prefix}successfully.self.by.stats-were-reset"] ?: listOf(
                        "",
                        "%Prefix.Text%Deine @gungame.stats@ wurden @gungame.stats.by@ zurückgesetzt",
                        ""
                    )
                }

                val successfullyTarget by lazy {
                    messages.available["${prefix}successfully.target.stats-were-reset"] ?: listOf(
                        "",
                        "%Prefix.Text%Du hast die @gungame.stats@ @gungame.stats.by@ zurückgesetzt",
                        ""
                    )
                }


            }

            inner class Team internal constructor() {

                private val prefix get() = "${messages.commands.prefix}${javaClass.simpleName!!}.".toLowerCase()

                /* Values */
                val failedTeamsNotAllowed by lazy {
                    messages.available["${prefix}failed.teams-not-allowed"]
                        ?: listOf("%Prefix.Warning%Teams sind grade verboten!")
                }

            }

            inner class Teams internal constructor() {

                /* Values */
                val failedDelay by lazy {
                    messages.available["${prefix}failed.delay"]
                        ?: listOf("%Prefix.Warning%Teams kann nur alle %Colors.IMPORTANT%@delay@ %Colors.TEXT%genutzt werden (%Colors.IMPORTANT%@remaining@%Colors.TEXT%)")
                }

                val title: String  by lazy {
                    messages.available["${prefix}title"]?.firstOrNull() ?: "%Colors.IMPORTANT%Teams"
                }

                val subTitle: String by lazy {
                    messages.available["${prefix}sub-title"]?.firstOrNull()
                        ?: "%Colors.TEXT%sind jetzt @allowed@"
                }

                val successfully by lazy {
                    messages.available["${prefix}successfully"]?.firstOrNull()
                        ?: "%Prefix.TEXT%Teams sind jetzt @allowed@"
                }


            }

            inner class Top internal constructor() {

                /* Values */
                val entry by lazy {
                    messages.available["${prefix}entry"]
                        ?: listOf("%Prefix.Text%#@rank@%Separator.Stats%@name@%Colors.TEXT% (%Colors.IMPORTANT%@points@%Colors.TEXT%)")
                }

                val success by lazy {
                    messages.available["${prefix}success"]
                        ?: listOf("%Prefix.Important%%Colors.DESIGN%                         %Colors.IMPORTANT%[ %Colors.PRIMARY%%Colors.EXTRA%TOP 10%Colors.IMPORTANT% ]%Colors.DESIGN%                         ")
                }

                val successfully by lazy {
                    messages.available["${prefix}successfully"]
                        ?: listOf("%Prefix.Important%%Colors.DESIGN%                         %Colors.IMPORTANT%[ %Colors.PRIMARY%%Colors.EXTRA%TOP 10%Colors.IMPORTANT% ]%Colors.DESIGN%                         ")
                }

            }

        }

        inner class Shop internal constructor() {

            private val prefix get() = "${javaClass.simpleName!!}.".toLowerCase()
            internal val Any.prefix get() = "${messages.shop.prefix}${javaClass.simpleName!!}.".toLowerCase()

            /* Values */
            val delayed by lazy {
                messages.available["${prefix}delayed"]
                    ?: listOf("%Prefix.Warning%Du kannst %Colors.SECONDARY%@item@%Colors.TEXT% in @time@ wieder kaufen!")
            }
            val priceLore by lazy {
                messages.available["${prefix}price-lore"]
                    ?: listOf("%Prefix.Text%Kaufe ihn dir für %Colors.PRIMARY%@price@ %Colors.IMPORTANT%Coins")
            }
            val maxCount by lazy {
                messages.available["${prefix}max-count"]
                    ?: listOf("%Prefix.Warning%Du darfst nur @count@ @item@ %Colors.TEXT%im %Colors.IMPORTANT%Inventar %Colors.TEXT%haben!")
            }
            val maxHealth by lazy {
                messages.available["${prefix}max-health"]
                    ?: listOf("%Prefix.Text%Du hast eine Behandlung echt nicht nötig ;).")
            }
            val maxLevel by lazy {
                messages.available["${prefix}max-health"]
                    ?: listOf("%Prefix.Text%Du hast schon das maximahle Level (%Colors.IMPORTANT%@kits@%Colors.TEXT%) erreicht.")
            }

            /* SubClass */
            val money by lazy { Money() }
            val keepInventory by lazy { KeepInventory() }

            inner class KeepInventory internal constructor() {

                /* Values */
                val successfully by lazy {
                    messages.available["${prefix}successfully"]
                        ?: listOf("%Prefix.Text%Du hast %Colors.IMPORTANT%KeepInventory %Colors.TEXT%aktiviert.")
                }

                val failed by lazy {
                    messages.available["${prefix}failed"]
                        ?: listOf("%Prefix.Warning%Du hast %Colors.IMPORTANT%KeepInventory %Colors.Warning%schon aktiviert!")
                }

            }

            inner class Money internal constructor() {

                /* Values */
                val successfully by lazy {
                    messages.available["${prefix}successfully"]
                        ?: listOf("%Prefix.Text%Der Kauf wurde erfolgreich abgeschlossen.")
                }

                val failed by lazy {
                    messages.available["${prefix}failed"] ?: listOf("%Prefix.Warning%Dir fehlen @difference@!")
                }

            }

        }

        inner class Regions internal constructor() {

            /* Values */
            val damageInTarget by lazy {
                messages.available["${prefix}damage-in-target"]
                    ?: listOf("%Prefix.Important%@target@%Colors.TEXT% befindet sich im %Colors.IMPORTANT%Spawn-Bereich!")
            }

            val damageInPlayer by lazy {
                messages.available["${prefix}damage-in-player"]
                    ?: listOf("%Prefix.Text%Du befindet sich im %Colors.IMPORTANT%Spawn-Bereich!")
            }

            val launchArrow by lazy {
                messages.available["${prefix}launch-arrow"]
                    ?: listOf("%Prefix.Text%Du befindet sich im %Colors.IMPORTANT%Spawn-Bereich!")
            }

        }

    }

    inner class Kits internal constructor() {

        /* Main */
        private val configData = ConfigData(directory, config.files.kits)
        private val kSerializer: KSerializer<List<List<ItemStack>>> = ItemStackSerializer.list.list

        /* Values */
        fun load(configData: ConfigData = this.configData): List<List<ItemStack?>> {
            val string = configData.file.readText()
            return if (string.isEmpty()) DefaultKits.values().map {
                listOf(it.helmet, it.chestplate, it.leggins, it.boots, it.item)
            } else Json.parse(kSerializer, string).map {
                it.map { itemStack -> if (itemStack.type == Material.AIR) null else itemStack }
            }
        }

        fun save(input: List<List<ItemStack?>> = allKits, configData: ConfigData = this.configData) = GsonService.save(
            configData,
            Json.indented.stringify(
                kSerializer,
                input.map { it.mapNotNull { itemStack -> itemStack ?: ItemStack(Material.AIR) } })
        )

    }

    companion object {

        val instance get() = Bukkit.getServicesManager()?.getRegistration(ConfigService::class.java)?.provider!!
        lateinit var messagesInstance: Messages

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