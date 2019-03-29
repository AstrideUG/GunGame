/*
 * © Copyright - Lars Artmann | LartyHD 2018.
 */
package de.astride.gungame

import com.google.gson.JsonObject
import de.astride.gungame.commands.Stats
import de.astride.gungame.commands.Team
import de.astride.gungame.commands.Teams
import de.astride.gungame.commands.Top
import de.astride.gungame.functions.changeColor
import de.astride.gungame.functions.configService
import de.astride.gungame.functions.gameMap
import de.astride.gungame.listener.InGameListener
import de.astride.gungame.listener.MoneyListener
import de.astride.gungame.listener.RegionsListener
import de.astride.gungame.services.ConfigService
import de.astride.gungame.shop.ShopListener
import net.darkdevelopers.darkbedrock.darkness.spigot.events.listener.EventsListener
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.SECONDARY
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
 * Current Version: 1.0 (17.02.2018 - 29.03.2019)
 */
class GunGame : DarkPlugin() {

    override fun onLoad() = onLoad {
        Bukkit.getServicesManager().register(
            ConfigService::class.java,
            ConfigService(dataFolder),
            this,
            ServicePriority.Normal
        ) //Important for ConfigService.instance

        Messages.NAME.message = "GunGame"

    }

    override fun onEnable() {

        EventsListener.autoRespawn = true

        val config = configService.maps
        val jsonObject = config.maps[Random.nextInt(config.maps.size() - 1)] as? JsonObject ?: return
        gameMap = MapsUtils.getMapAndLoad(config.config, jsonObject) { _, _ -> }

        initListener()
//        initStats()
        initCommands()
        spawnShop()

        ShopListener(this)
    }

    private fun initListener() {
        InGameListener(this)
        RegionsListener(this)
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            MoneyListener(this)
            logger.info("Hooking to Vault")
        } else logger.warning("Vault not found")
    }

    private fun initCommands() {
        Teams(this)
        Team(this)
        Stats(this)
        Top(this)
    }

    private fun spawnShop() = configService.shops.locations.forEach {

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
//    private fun initStats() {
//        val stats = setOf(
//            "Punkte",
//            "Kills",
//            "MaxKillStreak",
//            "Tode",
//            "Tode.Water",
//            "Used.LevelUps",
//            "Used.Healer",
//            "Used.Killer",
//            "Used.KeepInventory"
//        )
//        Saves.setStatsAPI(StatsAPI(Messages.getName(), stats, Utils.connectMySQL("Stats", "mysql.properties")))
//    }

}
