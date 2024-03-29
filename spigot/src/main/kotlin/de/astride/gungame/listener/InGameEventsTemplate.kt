/*
 * © Copyright - Lars Artmann | LartyHD 2018.
 */
package de.astride.gungame.listener

import de.astride.gungame.functions.*
import de.astride.gungame.kits.downgrade
import de.astride.gungame.kits.setKit
import de.astride.gungame.kits.upgrade
import de.astride.gungame.stats.Action
import net.darkdevelopers.darkbedrock.darkness.spigot.events.PlayerDisconnectEvent
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.events.*
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.randomLook
import net.darkdevelopers.darkbedrock.darkness.spigot.location.toBukkitLocation
import net.darkdevelopers.darkbedrock.darkness.spigot.manager.game.EventsTemplate
import net.darkdevelopers.darkbedrock.darkness.spigot.manager.game.InGameEventsTemplate
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Holograms
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Items
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Utils.players
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.Plugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 17.02.2018 15:32.
 * Current Version: 1.0 (17.02.2018 - 11.05.2019)
 */
object InGameEventsTemplate : EventsTemplate() {

    fun setup(plugin: Plugin) {

        InGameEventsTemplate.setup(plugin)

        cancelPlayerInteract = true
        cancelPlayerDropItem = true
        cancelPlayerPickupItem = true
        cancelFoodLevelChange = true
        cancelBlockBreak = true
        cancelBlockPlace = true

        setKeepInventory { true }
        setRespawn { gameMap.spawn.toBukkitLocation().randomLook() }

        listen<PlayerMoveEvent>(plugin) { event ->
            if (event.player.health <= 0.0) return@listen
            val types = arrayOf(Material.WATER, Material.STATIONARY_WATER, Material.LAVA, Material.STATIONARY_LAVA)
            if (types.any { it == event.to.block.type }) event.player.health = 0.0
        }.add()
        listen<PlayerJoinEvent>(plugin) { event -> event.player.setup() }.add()
        listen<PlayerDisconnectEvent>(plugin) { event -> event.player.removeHologram() }.add()
        listen<PlayerDeathEvent>(plugin) { event ->
            event.droppedExp = 0

            event.entity.uniqueId.actions += Action(
                event.javaClass.simpleName,
                mapOf(/*"player" to event.entity.toDataPlayer()*/)
            )
        }.add()
        listen<PlayerRespawnEvent>(plugin, priority = EventPriority.LOW) { event ->
            event.player.apply player@{

                InGameEventsTemplate.killer[uniqueId]?.apply {

                    if (this@player.uniqueId == uniqueId) return@apply

                    uniqueId.actions += Action(
                        event.javaClass.simpleName,
                        mapOf(/*"player" to this.toDataPlayer(), "killed" to this@player.toDataPlayer()*/)
                    )

                    playSound(location, Sound.ENDERMAN_HIT, 2f, 1f)
//                broadcastKillStreak(killStreak++, this) TODO: Add broadcastKillStreak
                    sendScoreBoard()
                    upgrade()
                    heal()
                    sendHologram()

                }

                playSound(location, Sound.GHAST_DEATH, 2f, 1f)
                downgrade()
                sendScoreBoard()
                sendHologram()

            }
        }.add()
        listen<PlayerInteractEvent>(plugin) { event ->
            if (event.hasItem()) {
                val item = event.item
                if (item == Items.LEAVE.itemStack) event.player.kickPlayer("LEAVE")
                else if (item.type == Material.BOW || item.type == Material.FISHING_ROD) return@listen
            }
        }.add()
        listen<InventoryClickEvent>(plugin) { event ->
            if (event.clickedInventory == event.whoClicked.openInventory.bottomInventory)
                if (event.slotType == InventoryType.SlotType.ARMOR ||
                    event.slot == 0 ||
                    event.slot == 9 ||
                    event.currentItem == Items.LEAVE.itemStack
                ) event.cancel()
        }.add()
        listen<EntityDamageEvent>(plugin) { if (it.cause == EntityDamageEvent.DamageCause.FALL) it.cancel() }.add()

        players.forEach { it.setup() }

    }

    override fun reset() {

        InGameEventsTemplate.reset()

        unregisterKeepInventory()

        super.reset()

    }

    private fun Player.setup() {
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
        teleport(gameMap.spawn.toBukkitLocation().randomLook())

        setKit()
        sendScoreBoard()
        showAll()
        sendHologram()

    }

    private fun Player.showAll(): Unit = players.forEach {
        if (name.equals(it.name, ignoreCase = true)) return@forEach
        it.hidePlayer(this)
        it.showPlayer(this)
    }

    private fun Player.sendHologram(location: Location? = gameMap.hologram?.toBukkitLocation()) {
        val lines = messages.hologram.withReplacements(uniqueId).mapNotNull { it }.toTypedArray()
        val holograms = Holograms(lines, location ?: return)
        setMetadata("holograms", FixedMetadataValue(javaPlugin, holograms))
        holograms.show(this)
    }

    private fun Player.removeHologram() {
        val metadata = getMetadata("holograms").firstOrNull()?.value() as? Holograms
        metadata?.hide(this)
        removeMetadata("holograms", javaPlugin)
    }

//    private fun broadcastKillStreak(killStreak: Int, killer: Player) {
//        val streaks = arrayOf(5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 60, 70, 80, 90, 100, 125, 150, 200, 250, 500, 1000)
//        if (streaks.any { it == killStreak })
//            Bukkit.broadcastMessage("${Messages.PREFIX}$IMPORTANT${killer.displayName}$TEXT hat eine $IMPORTANT$EXTRA${killStreak}er KillStreak")
//    }

}
