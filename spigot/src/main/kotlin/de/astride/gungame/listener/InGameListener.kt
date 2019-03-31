/*
 * © Copyright - Lars Artmann | LartyHD 2018.
 */
package de.astride.gungame.listener

import de.astride.gungame.functions.actions
import de.astride.gungame.functions.gameMap
import de.astride.gungame.functions.sendScoreBoard
import de.astride.gungame.kits.downgrade
import de.astride.gungame.kits.heal
import de.astride.gungame.kits.setKit
import de.astride.gungame.kits.upgrade
import de.astride.gungame.stats.Action
import net.darkdevelopers.darkbedrock.darkness.spigot.events.PlayerDisconnectEvent
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.randomLook
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.game.InGameListener
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Items
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.*
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 17.02.2018 15:32.
 * Current Version: 1.0 (17.02.2018 - 31.03.2019)
 */
class InGameListener(javaPlugin: JavaPlugin) : InGameListener(javaPlugin) {

    @EventHandler
    override fun onPlayerMoveEvent(event: PlayerMoveEvent) {
        super.onPlayerMoveEvent(event)
        val type = event.to.block.type
        if (event.player.health <= 0.0) return
        if (type == Material.WATER ||
            type == Material.STATIONARY_WATER ||
            type == Material.LAVA ||
            type == Material.STATIONARY_LAVA
        ) event.player.health = 0.0
    }

    @EventHandler
    override fun onPlayerJoinEvent(event: PlayerJoinEvent) {
//        statsAPI.createAccount(uniqueId)

        event.player.apply {

            event.joinMessage = "${Messages.PREFIX}$IMPORTANT$displayName$TEXT hat die Runde betreten"

            inventory.apply {
                clear()
                armorContents = null
                setItem(8, Items.LEAVE.itemStack)
            }

            gameMode = GameMode.ADVENTURE
            health = maxHealth
            teleport(gameMap.spawn.randomLook())

            setKit()
            sendScoreBoard()
            showAll()

        }

    }

    @EventHandler
    override fun onPlayerDisconnectEvent(event: PlayerDisconnectEvent) {
        event.leaveMessage = "${Messages.PREFIX}$IMPORTANT${event.player.displayName}$TEXT hat die Runde verlassen"
    }

    @EventHandler
    override fun onPlayerDeathEvent(event: PlayerDeathEvent) {
        super.onPlayerDeathEvent(event)
        event.keepInventory = true
        event.droppedExp = 0

        event.entity.uniqueId.actions += Action(event.javaClass.simpleName, mapOf("player" to event.entity))
    }

    @EventHandler
    fun onPlayerRespawnEvent(event: PlayerRespawnEvent) {

        event.respawnLocation = gameMap.spawn.randomLook()
        event.player.apply player@{

            killer?.apply {

                if (uniqueId == player.uniqueId) return

                uniqueId.actions += Action(event.javaClass.simpleName, mapOf("player" to this, "killed" to this@player))

                playSound(location, Sound.ENDERMAN_HIT, 2f, 1f)
//                broadcastKillStreak(killStreak++, this) TODO: Add broadcastKillStreak
                sendScoreBoard()
                upgrade()
                heal()

            }

            playSound(location, Sound.GHAST_DEATH, 2f, 1f)
            downgrade()
            sendScoreBoard()

        }

//        statsAPI.add(uniqueId, 1, "tode", {
//            statsAPI.remove(uniqueId, 5, "Punkte", {
//                statsAPI.get(uniqueId, "Punkte", { punkte ->
//        if (punkte < 0) statsAPI.set(uniqueId, 0, "Punkte")

//        statsAPI.get(killerUniqueId, "MaxKillStreak", { result1 ->
//        if (killStreak > result1) statsAPI.set(killerUniqueId, killStreak, "MaxKillStreak")
//        statsAPI.add(killerUniqueId, 1, "Kills")
//        statsAPI.add(killerUniqueId, 10, "Punkte")
//        killer.sendMessage(coinsAPI.addCoins(killerUniqueId, "5", { result2 ->

//        }))
//        })
//                })
//            })
//        })
    }

    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {
        event.cancel()
        if (event.item == Items.LEAVE.itemStack) event.player.kickPlayer("LEAVE")
    }

    @EventHandler
    fun onInventoryClickEvent(event: InventoryClickEvent) {
        if (event.clickedInventory == event.inventory) event.cancel()
    }

    @EventHandler
    fun onEntityDamageEvent(event: EntityDamageEvent) {
        if (event.cause == EntityDamageEvent.DamageCause.FALL) event.cancel()
    }

    @EventHandler
    fun onPlayerDropItemEvent(event: PlayerDropItemEvent) = event.cancel()

    @EventHandler
    fun onPlayerPickupItemEvent(event: PlayerPickupItemEvent) = event.cancel()

    @EventHandler
    fun onBlockBreakEvent(event: BlockBreakEvent) = event.cancel()

    @EventHandler
    fun onBlockPlaceEvent(event: BlockPlaceEvent) = event.cancel()

    @EventHandler
    fun onFoodLevelChangeEvent(event: FoodLevelChangeEvent) = event.cancel()


    private fun Player.showAll() = Utils.goThroughAllPlayers {
        if (name.equals(it.name, ignoreCase = true)) return@goThroughAllPlayers
        it.hidePlayer(this)
        it.showPlayer(this)
    }

    private fun broadcastKillStreak(killStreak: Int, killer: Player) {
        val streaks = arrayOf(5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 60, 70, 80, 90, 100, 125, 150, 200, 250, 500, 1000)
        if (streaks.any { it == killStreak })
            Bukkit.broadcastMessage("${Messages.PREFIX}$IMPORTANT${killer.displayName}$TEXT hat eine $IMPORTANT$EXTRA${killStreak}er KillStreak")
    }

}
