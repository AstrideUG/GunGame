/*
 * © Copyright - MineWar.net | Lars Artmann aka. LartyHD 2017
 */
package de.astride.gungame.listener

import de.astride.gungame.functions.*
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.events.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.events.listen
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.events.unregister
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.manager.game.EventsTemplate
import net.darkdevelopers.darkbedrock.darkness.spigot.region.isInside
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Items
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.plugin.Plugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 07.08.2017 03:20.
 * Current Version: 1.0 (07.08.2017 - 12.05.2019)
 */
object RegionsEventsTemplate : EventsTemplate() {

    private val region get() = gameMap.region

    fun setup(plugin: Plugin) {
        listen<EntityDamageByEntityEvent>(plugin) { event ->
            if (event.entity !is Player) return@listen

            val damager = event.damager ?: return@listen
            val transform: (String?) -> String? = { it.replace("target", event.entity.name) }
            val region = region ?: return@listen
            when {
                region.isInside(event.entity.location) -> messages.regions.damageInTarget.map(transform).sendTo(damager)
                region.isInside(damager.location) -> messages.regions.damageInPlayer.map(transform).sendTo(damager)
                else -> return@listen
            }

            event.cancel()
        }.add()
        listen<ProjectileLaunchEvent>(plugin) { event ->
            val arrow = event.entity as? Arrow ?: return@listen
            val player = arrow.shooter as? Player ?: return@listen
            if (region?.isInside(player.location) != true) return@listen
            event.cancel()
            messages.regions.launchArrow.sendTo(player)
        }.add()
        listen<PlayerMoveEvent>(plugin) { event ->
            val inventory = event.player.inventory ?: return@listen
            val region = region ?: return@listen
            if (inventory.getItem(leaveSlot) == Items.LEAVE.itemStack && !region.isInside(event.to))
                inventory.setItem(leaveSlot, null)
            else if (inventory.getItem(leaveSlot) == null && region.isInside(event.to))
                inventory.setLeave()
        }.add()
        listen<PlayerRespawnEvent>(plugin) { it.player.inventory.setLeave() }.add()
    }

    fun reset() {
        listener.unregister()
        listener.clear()
    }

}