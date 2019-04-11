/*
 * © Copyright - MineWar.net | Lars Artmann aka. LartyHD 2017
 */
package de.astride.gungame.listener

import de.astride.gungame.functions.*
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.plugin.java.JavaPlugin


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 07.08.2017 03:20.
 * Current Version: 1.0 (07.08.2017 - 11.04.2019)
 */
class RegionsListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    private val region = gameMap.region

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 29.03.2019 12:56.
     * Current Version: 1.0 (29.03.2019 - 11.04.2019)
     */
    @EventHandler
    fun onEntityDamageByEntityEvent(event: EntityDamageByEntityEvent) {

        if (event.entity !is Player) return

        val damager = event.damager ?: return
        val transform: (String?) -> String? = { it.replace("target", event.entity.name) }
        when {
            region.isInside(event.entity.location) -> messages.regions.damageInTarget.map(transform).sendTo(damager)
            region.isInside(damager.location) -> messages.regions.damageInPlayer.map(transform).sendTo(damager)
            else -> return
        }

        event.cancel()
    }

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 29.03.2019 12:59.
     * Current Version: 1.0 (29.03.2019 - 11.04.2019)
     */
    @EventHandler
    fun onProjectileLaunchEvent(event: ProjectileLaunchEvent) {
        val arrow = event.entity as? Arrow ?: return
        val player = arrow.shooter as? Player ?: return
        if (!region.isInside(player.location)) return
        event.cancel()
        messages.regions.launchArrow.sendTo(player)
    }

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 29.03.2019 13:07.
     * Current Version: 1.0 (29.03.2019 - 31.03.2019)
     */
    @EventHandler
    fun onPlayerMoveEvent(event: PlayerMoveEvent) {
        val inventory = event.player.inventory
        if (inventory.getItem(leaveSlot) != null && !region.isInside(event.to)) inventory.setItem(leaveSlot, null)
        else if (inventory.getItem(leaveSlot) == null && region.isInside(event.to)) inventory.setLeave()
    }

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 29.03.2019 12:54.
     * Current Version: 1.0 (29.03.2019 - 29.03.2019)
     */
    @EventHandler
    fun onPlayerRespawnEvent(event: PlayerRespawnEvent) = event.player.inventory.setLeave()

}