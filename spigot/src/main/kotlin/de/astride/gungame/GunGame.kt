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
import de.astride.gungame.functions.gameMap
import de.astride.gungame.listener.InGameListener
import de.astride.gungame.listener.MoneyListener
import de.astride.gungame.listener.RegionsListener
import de.astride.gungame.shop.ShopManager
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonService
import net.darkdevelopers.darkbedrock.darkness.spigot.configs.gson.BukkitGsonConfig
import net.darkdevelopers.darkbedrock.darkness.spigot.events.listener.EventsListener
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.SECONDARY
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.plugin.DarkPlugin
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Items
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.MapsUtils
import org.bukkit.Bukkit
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 17.02.2018 15:27.
 * Current Version: 1.0 (17.02.2018 - 27.03.2019)
 */
class GunGame : DarkPlugin() {

    override fun onLoad() = onLoad {
        Messages.NAME.message = "GunGame"
        EventsListener.autoRespawn = true
    }

    override fun onEnable() {

//        val loadMapNames = MapsUtils.loadMapNames(dataFolder)
//        val mapName = MapsUtils.getRandomMap(loadMapNames)
//        val map = MapsUtils.loadMap(mapName)

        gameMap = MapsUtils.getMapAndLoad(
            BukkitGsonConfig(ConfigData(dataFolder, "data.json")),
            JsonObject()
        ) { _, _ -> }

        //TODO: Add Map Vote
//        Saves.setVoteAPI(object : VoteAPI(Saves.getMapNames(), Messages.getPrefix()) {
//            fun finishVotes(winner: String) {
//                getResult()
//            }
//        })

//        if (Saves.getVoteAPI().getVoteList().size() > 3) {
//            Saves.setMapVoteCountdown(MapVoteCountdown(Messages.getPrefix(), this))
//            MapVoteManager(this, Messages.getPrefix())
//        } else {
//            Saves.setVoteAPI(null)
//        }


        initListener()
//        initStats()
        initCommands()
        spawnShop()

        ShopManager(this)
    }

    private fun initListener() {
        InGameListener(this)
        RegionsListener(this)
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            MoneyListener(this)
            logger.info("Hooking to Vault")
        } else logger.warning("Vault not found")
//        RegionsListener(this, name)
    }

    private fun initCommands() {
        Teams(this)
        Team(this)
        Stats(this)
        Top(this)
    }

    private fun spawnShop() {

        val configData = ConfigData(dataFolder, "shops.json")
        val shops = GsonService.loadAsJsonArray(configData)
        val bukkitGsonConfig = BukkitGsonConfig(configData)
        val locations = shops.map { bukkitGsonConfig.getLocation(it.asJsonObject) }
        locations.forEach {

            (it.world.spawnEntity(it, EntityType.ARMOR_STAND) as ArmorStand).apply {

                customName = "${SECONDARY}Shop"
                isCustomNameVisible = true
                setGravity(false)
                isVisible = false
                isSmall = true
                helmet = Items.CHEST.itemStack
                changeColor()

            }

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
