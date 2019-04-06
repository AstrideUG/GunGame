/*
 * © Copyright - Lars Artmann | LartyHD 2018.
 */
package de.astride.gungame

import com.google.gson.JsonObject
import de.astride.gungame.commands.*
import de.astride.gungame.commands.GunGame
import de.astride.gungame.functions.*
import de.astride.gungame.listener.InGameListener
import de.astride.gungame.listener.MoneyListener
import de.astride.gungame.listener.RegionsListener
import de.astride.gungame.services.ConfigService
import de.astride.gungame.shop.ShopListener
import net.darkdevelopers.darkbedrock.darkness.spigot.events.listener.EventsListener
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.plugin.DarkPlugin
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
 * Current Version: 1.0 (17.02.2018 - 06.04.2019)
 */
class GunGame : DarkPlugin() {

    override fun onLoad(): Unit = onLoad {
        Bukkit.getServicesManager().register(
            ConfigService::class.java,
            ConfigService(dataFolder),
            this,
            ServicePriority.Normal
        ) //Important for ConfigService.instance

        Messages.NAME.message = "GunGame"
        Messages.PREFIX.message = "$PRIMARY$EXTRA${Messages.NAME}$IMPORTANT | $RESET"


    }

    override fun onEnable(): Unit = onEnable {

        EventsListener.autoRespawn = true

        val config = configService.maps
        if (config.maps.size() < 1) throw IllegalStateException("No Maps are configured")
        @Suppress("LABEL_NAME_CLASH")
        val jsonObject = config.maps[Random.nextInt(config.maps.size())] as? JsonObject ?: return@onEnable
        gameMap = MapsUtils.getMapAndLoad(config.bukkitGsonConfig, jsonObject) { _, _ -> }

        logger.info("Load stats...")
        allActions = configService.actions.load()
        logger.info("Loaded stats")

        logger.info("Load allowTeams...")
        allowTeams = configService.config.allowTeams
        logger.info("Loaded allowTeams")

        initListener()
        initCommands()

        ShopListener(this)

        Bukkit.getScheduler().runTaskLater(this, { spawnShops() }, 5)
//        ranksUpdater()
    }

    override fun onDisable(): Unit = onDisable {
        logger.info("Save stats...")
        configService.actions.save(allActions)
        logger.info("Saved stats")
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

            customName = "${SECONDARY}Shop"
            isCustomNameVisible = true
            setGravity(false)
            isVisible = false
            isSmall = true
            helmet = Items.CHEST.itemStack
            changeColor()

        }

    }

//    private fun ranksUpdater() =
//        Bukkit.getScheduler().runTaskTimerAsynchronously(this, { ranks = ranks() }, 0, TimeUnit.SECONDS.toMillis(20))


}
