/*
 * © Copyright - Lars Artmann | LartyHD 2018.
 */
package de.astride.ffa.listener

import de.astride.ffa.functions.*
import de.astride.ffa.stats.Action
import net.darkdevelopers.darkbedrock.darkness.spigot.events.PlayerDisconnectEvent
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
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
 * Current Version: 1.0 (17.02.2018 - 19.04.2019)
 */
class InGameListener(javaPlugin: JavaPlugin) : InGameListener(javaPlugin) {

    @EventHandler
    override fun onPlayerJoinEvent(event: PlayerJoinEvent) {

        event.player.apply {

            inventory.apply {
                clear()
                armorContents = null
                setLeave()
            }

            foodLevel = 20
            saturation = 20f
            exp = 0f
            level = 0
            gameMode = GameMode.ADVENTURE
            health = maxHealth
            teleport(gameMap.spawn/*.randomLook()*/)

            event.player.inventory.apply {
                val kit = configService.kit.kit
                armorContents = kit.take(4).asReversed().toTypedArray()
                addItem(*kit.drop(4).toTypedArray())
            }

            sendScoreBoard()
            showAll()
            gameMap.sendHologram(event.player)

        }

    }

    @EventHandler
    override fun onPlayerDisconnectEvent(event: PlayerDisconnectEvent) {
        gameMap.removeHologram(event.player)
    }

    @EventHandler
    override fun onPlayerDeathEvent(event: PlayerDeathEvent) {
        super.onPlayerDeathEvent(event)
        event.keepInventory = true
        event.droppedExp = 0

        event.entity.uniqueId.actions += Action(
            event.javaClass.simpleName,
            mapOf(/*"player" to event.entity.toDataPlayer()*/)
        )
    }

    @EventHandler
    override fun onPlayerRespawnEvent(event: PlayerRespawnEvent) {

        event.respawnLocation = gameMap.spawn/*.randomLook()*/
        event.player.apply player@{

            this@InGameListener.killer[uniqueId]?.apply {

                if (this@player.uniqueId == uniqueId) return

                uniqueId.actions += Action(
                    event.javaClass.simpleName,
                    mapOf(/*"player" to this.toDataPlayer(), "killed" to this@player.toDataPlayer()*/)
                )

                playSound(location, Sound.ENDERMAN_HIT, 2f, 1f)
//                broadcastKillStreak(killStreak++, this) TODO: Add broadcastKillStreak
                sendScoreBoard()
                heal()
                gameMap.sendHologram(this)

            }

            playSound(location, Sound.GHAST_DEATH, 2f, 1f)
            sendScoreBoard()
            gameMap.sendHologram(this)

        }

        super.onPlayerRespawnEvent(event)

    }

    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {
        if (event.hasItem()) {
            val item = event.item
            if (item == Items.LEAVE.itemStack) event.player.kickPlayer("LEAVE")
            else if (item.type == Material.BOW || item.type == Material.FISHING_ROD) return
        }
        event.cancel()
    }

    @EventHandler
    fun onInventoryClickEvent(event: InventoryClickEvent) {
        if (event.clickedInventory == event.whoClicked.openInventory.bottomInventory)
            if (event.slot == 8) event.cancel()
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
