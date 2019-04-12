/*
 * © Copyright - Lars Artmann | LartyHD 2018.
 */
package de.astride.gungame

import com.google.gson.JsonObject
import de.astride.gungame.commands.*
import de.astride.gungame.commands.GunGame
import de.astride.gungame.functions.*
import de.astride.gungame.kits.kits
import de.astride.gungame.listener.InGameListener
import de.astride.gungame.listener.MoneyListener
import de.astride.gungame.listener.RegionsListener
import de.astride.gungame.services.ConfigService
import de.astride.gungame.shop.ShopListener
import de.astride.gungame.stats.Actions
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
 * Current Version: 1.0 (17.02.2018 - 12.04.2019)
 */
class GunGame : DarkPlugin() {

    override fun onLoad(): Unit = onLoad {
        Bukkit.getServicesManager().register(
            ConfigService::class.java,
            ConfigService(dataFolder),
            this,
            ServicePriority.Normal
        ) //Important for ConfigService.instance
    }

    override fun onEnable(): Unit = onEnable {

        EventsListener.autoRespawn = true

        logLoad("map") {
            val config = configService.maps
            if (config.maps.size() < 1) throw IllegalStateException("No Maps are configured")
            @Suppress("LABEL_NAME_CLASH")
            val jsonObject = config.maps[Random.nextInt(config.maps.size())] as? JsonObject ?: return@onEnable
            gameMap = MapsUtils.getMapAndLoad(config.bukkitGsonConfig, jsonObject) { player, holograms, map ->
                val uuid = player.uniqueId
                holograms[uuid] =
                    Holograms(messages.hologram.withReplacements(uuid).mapNotNull { it }.toTypedArray(), map.hologram)
                holograms[uuid]?.show(player)
            }
        }
        logLoad("kits") { kits = configService.kits.load() }
        logLoad("allow-teams") { allowTeams = configService.config.allowTeams }
        logLoad("stats") {
            allActions = configService.actions.load().map { it.key to Actions(it.key, it.value) }.toMap().toMutableMap()
        }

        initListener()
        initCommands()

        ShopListener(this)

        Bukkit.getScheduler().runTaskLater(this, { spawnShops() }, 5)
//        ranksUpdater()
    }

    override fun onDisable(): Unit = onDisable {
        logSave("kits") { configService.kits.save() }
        logSave("stats") { configService.actions.save() }
    }

    private fun initListener() {
        InGameListener(this)
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

            customName = messages.shopName
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
    private inline fun log(prefix: String, suffix: String, block: () -> Unit) {
        logger.info("$prefix $suffix...")
        block()
        logger.info("${prefix}ed $suffix")
    }

//    private fun ranksUpdater() =
//        Bukkit.getScheduler().runTaskTimerAsynchronously(this, { ranks = ranks() }, 0, TimeUnit.SECONDS.toMillis(20))


}
