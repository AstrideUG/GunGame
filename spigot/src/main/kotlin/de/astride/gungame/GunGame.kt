/*
 * © Copyright - Lars Artmann | LartyHD 2018.
 */
package de.astride.gungame

import com.google.gson.JsonObject
import de.astride.gungame.commands.*
import de.astride.gungame.commands.GunGame
import de.astride.gungame.functions.*
import de.astride.gungame.kits.kits
import de.astride.gungame.listener.InGameEventsTemplate
import de.astride.gungame.listener.MoneyListener
import de.astride.gungame.listener.RegionsListener
import de.astride.gungame.services.ConfigService
import de.astride.gungame.setup.Events
import de.astride.gungame.shop.ShopListener
import de.astride.gungame.stats.Actions
import net.darkdevelopers.darkbedrock.darkness.general.functions.performCraftPluginUpdater
import net.darkdevelopers.darkbedrock.darkness.spigot.events.listener.EventsListener
import net.darkdevelopers.darkbedrock.darkness.spigot.plugin.DarkPlugin
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Holograms
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Items
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.MapsUtils
import org.bukkit.Bukkit
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.plugin.ServicePriority
import kotlin.random.Random

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 17.02.2018 15:27.
 * Current Version: 1.0 (17.02.2018 - 07.05.2019)
 */
class GunGame : DarkPlugin() {

    override fun onLoad(): Unit = onLoad {
        val map = mapOf(
            "type" to "GunGame-Spigot",
            "javaplugin" to this
        )
        performCraftPluginUpdater(map)
    }

    override fun onEnable(): Unit = onEnable {

        logLoad("Config") {
            Bukkit.getServicesManager().register(
                ConfigService::class.java,
                ConfigService(dataFolder),
                this,
                ServicePriority.Normal
            ) //Important for ConfigService.instance
        }

        EventsListener.autoRespawn = true

        logLoad("map") {
            val config = configService.maps
            if (config.maps.size() < 1) {
                isSetup = true
                logger.warning("No Maps are configured!")
                logger.warning("The plugin needs at least one map to make it work!")
                return@logLoad
            }
            @Suppress("LABEL_NAME_CLASH")
            val jsonObject = config.maps[Random.nextInt(config.maps.size())] as? JsonObject ?: return@onEnable
            gameMap = MapsUtils.getMapAndLoad(config.bukkitGsonConfig, jsonObject) { player, holograms, map ->
                val uuid = player.uniqueId
                holograms[uuid] =
                    Holograms(messages.hologram.withReplacements(uuid).mapNotNull { it }.toTypedArray(), map.hologram)
                holograms[uuid]?.show(player)
            }
        }

        logLoad("setup listener") { Events.setup(this) }

        if (isSetup) {

            logLoad("GunGame command") { GunGame(this) }

            logger.info("Since the plugin is in setup mode, only the GunGame command & setup listener has been initialized!")
            @Suppress("LABEL_NAME_CLASH")
            return@onEnable
        }

        logLoad("kits") { kits = configService.kits.load() }
        logLoad("allow-teams") {
            AllowTeams.Random.update()
            allowTeams = configService.config.allowTeams
        }
        logLoad("actions") {
            allActions = configService.actions.load().map { it.key to Actions(it.key, it.value) }.toMap().toMutableMap()
        }

        logLoad("events") { initEvents() }
        logLoad("commands") { initCommands() }

        ShopListener(this)

        Bukkit.getScheduler().runTaskLater(this, { spawnShops() }, 5)
//        ranksUpdater()
    }

    override fun onDisable(): Unit = onDisable {
        logUnregister("setup listener") { Events.reset() }
        @Suppress("LABEL_NAME_CLASH")
        if (isSetup) {
            isSetup = false
            return@onDisable
        }
        logSave("kits") { configService.kits.save() }
        logSave("stats") { configService.actions.save() }
        logLoad("ingame events") { InGameEventsTemplate.reset() }

        logUnregister("Config") {
            //must be after all "configService" calls
            server.servicesManager.unregister(ConfigService::class.java, configService)
        }
    }

    private fun initEvents() {
        InGameEventsTemplate.setup(this)
        RegionsListener(this)
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            logger.info("Hooking to Vault...")
            MoneyListener(this)
            logger.info("Hooked to Vault")
        } else logger.warning("Vault not found")
    }

    private fun initCommands() {
        Teams(this)
        Team(this)
        Stats(this)
        StatsReset(this)
        Top(this)
        GunGame(this)
    }

    private fun spawnShops() = configService.shops.locations.forEach {

        val armorStand = it.world.spawnEntity(it, EntityType.ARMOR_STAND) as ArmorStand
        armorStand.apply {

            customName = messages.shop.entityName
            isCustomNameVisible = true
            setGravity(false)
            isVisible = false
            isSmall = true
            helmet = Items.CHEST.itemStack
            changeColor()

        }

    }

    private inline fun logLoad(suffix: String, block: () -> Unit) = log("Load", suffix, block)
    private inline fun logSave(suffix: String, block: () -> Unit) = log("Save", suffix, block)
    private inline fun logUnregister(suffix: String, block: () -> Unit) = log("Unregister", suffix, block)
    private inline fun log(prefix: String, suffix: String, block: () -> Unit) {
        logger.info("$prefix $suffix...")
        block()
        logger.info("${if (prefix.endsWith('e')) prefix.dropLast(1) else prefix}ed $suffix")
    }

//    private fun ranksUpdater() =
//        Bukkit.getScheduler().runTaskTimerAsynchronously(this, { ranks = ranks() }, 0, TimeUnit.SECONDS.toMillis(20))


}
