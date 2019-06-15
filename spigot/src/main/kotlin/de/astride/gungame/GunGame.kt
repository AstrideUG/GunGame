/*
 * © Copyright - Lars Artmann | LartyHD 2018.
 */
package de.astride.gungame

import com.rollbar.api.payload.data.Server
import com.rollbar.notifier.Rollbar
import com.rollbar.notifier.config.ConfigBuilder.withAccessToken
import de.astride.gungame.commands.*
import de.astride.gungame.commands.GunGame
import de.astride.gungame.functions.*
import de.astride.gungame.kits.kits
import de.astride.gungame.listener.InGameEventsTemplate
import de.astride.gungame.listener.MoneyListener
import de.astride.gungame.listener.RegionsEventsTemplate
import de.astride.gungame.listener.TeamsEvents
import de.astride.gungame.services.ConfigService
import de.astride.gungame.setup.Events
import de.astride.gungame.shop.ShopListener
import de.astride.gungame.stats.Actions
import net.darkdevelopers.darkbedrock.darkness.general.functions.performCraftPluginUpdater
import net.darkdevelopers.darkbedrock.darkness.spigot.events.listener.EventsListener
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.loadBukkitWorld
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.setup
import net.darkdevelopers.darkbedrock.darkness.spigot.location.toBukkitLocation
import net.darkdevelopers.darkbedrock.darkness.spigot.plugin.DarkPlugin
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Items
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.map.setupWorldBorder
import org.bukkit.Bukkit
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.plugin.ServicePriority
import java.net.InetAddress
import kotlin.random.Random


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 17.02.2018 15:27.
 * Current Version: 1.0 (17.02.2018 - 12.05.2019)
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
        reportThrowable {

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
                if (config.rawMaps.size() < 1) {
                    isSetup = true
                    logger.warning("No Maps are configured!")
                    logger.warning("The plugin needs at least one map to make it work!")
                    return@logLoad
                }

                gameMap = config.maps.toList()[Random.nextInt(config.maps.size)]
                gameMap.spawn.world.loadBukkitWorld().setup()
                gameMap.setupWorldBorder()
            }

            logLoad("setup events") { Events.setup(this) }

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
                allActions =
                    configService.actions.load().map { it.key to Actions(it.key, it.value) }.toMap().toMutableMap()
            }

            logLoad("events") { initEvents() }
            logLoad("commands") { initCommands() }

            Bukkit.getScheduler().runTaskLater(this, { logLoad("shops") { spawnShops() } }, 5)
        }
    }

    override fun onDisable(): Unit = onDisable {
        logUnregister("setup events") { Events.reset() }

        logSave("shops") { configService.shops.save() }
        logSave("maps") { configService.maps.save() }

        @Suppress("LABEL_NAME_CLASH")
        if (isSetup) {
            isSetup = false
            return@onDisable
        }
        logSave("kits") { configService.kits.save() }
        logSave("stats") { configService.actions.save() }
        logUnregister("ingame events") { InGameEventsTemplate.reset() }
        logUnregister("regions events") { RegionsEventsTemplate.reset() }

        logUnregister("Config") {
            //must be after all "configService" calls
            server.servicesManager.unregister(ConfigService::class.java, configService)
        }
    }

    private fun initEvents() {
        TeamsEvents.setup(this)
        InGameEventsTemplate.setup(this)
        RegionsEventsTemplate.setup(this)
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            logger.info("Hooking to Vault...")
            MoneyListener(this)
            logger.info("Hooked to Vault")
        } else logger.warning("Vault not found")
        ShopListener(this)
    }

    private fun initCommands() {
        Teams(this)
        Team(this)
        Stats(this)
        StatsReset(this)
        Top(this)
        GunGame(this)
    }

    private fun spawnShops(): Unit = configService.shops.locations.forEach {

        val location = it.toBukkitLocation()

        val armorStand = location.world?.spawnEntity(location, EntityType.ARMOR_STAND) as? ArmorStand ?: return@forEach
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

    private inline fun reportThrowable(code: () -> Unit): Unit = try {
        code()
    } catch (throwable: Throwable) {
        throwable.printStackTrace()

        val rollbar: Rollbar = Rollbar.init(withAccessToken("364c0eca3f6f49e98201dc8dabec501d")
            .codeVersion("1.1.0")
            .custom {
                mapOf("DarkFrame-Version" to server.pluginManager.getPlugin("DarkFrame")?.description?.version)
            }
            .server {
                Server.Builder().host(InetAddress.getLocalHost().toString()).build()
            }
            .build())
        rollbar.critical(throwable)
        rollbar.close(true)
    }

}
