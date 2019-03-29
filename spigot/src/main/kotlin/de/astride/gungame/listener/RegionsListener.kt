/*
 * © Copyright - MineWar.net | Lars Artmann aka. LartyHD 2017
 */
package de.astride.gungame.listener

import de.astride.gungame.functions.gameMap
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.IMPORTANT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.TEXT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Items
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 07.08.2017 03:20.
 * Current Version: 1.0 (07.08.2017 - 29.03.2019)
 */
class RegionsListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    private val region = gameMap.region

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 29.03.2019 12:55.
     * Current Version: 1.0 (29.03.2019 - 29.03.2019)
     */
    @EventHandler
    fun onEntityDamageEvent(event: EntityDamageEvent) {
        if (event.entity !is Player) return
        if (!region.isInside(event.entity.location)) return
        event.cancel()
    }

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 29.03.2019 12:56.
     * Current Version: 1.0 (29.03.2019 - 29.03.2019)
     */
    @EventHandler
    fun onEntityDamageByEntityEvent(event: EntityDamageByEntityEvent) {

        if (event.entity !is Player) return

        val damager = event.damager ?: return
        if (region.isInside(event.entity.location)) damager
            .sendMessage("${Messages.PREFIX}$IMPORTANT${event.entity.name}$TEXT befindet sich im ${IMPORTANT}Spawn-Bereich")
        else if (region.isInside(damager.location)) damager
            .sendMessage("${Messages.PREFIX}${TEXT}Du befindet sich im ${IMPORTANT}Spawn-Bereich")

        event.cancel()
    }

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 29.03.2019 12:59.
     * Current Version: 1.0 (29.03.2019 - 29.03.2019)
     */
    @EventHandler
    fun onProjectileLaunchEvent(event: ProjectileLaunchEvent) {
        val arrow = event.entity as? Arrow ?: return
        val player = arrow.shooter as? Player ?: return
        if (!region.isInside(player.location)) return
        event.cancel()
        player.sendMessage("${Messages.PREFIX}${TEXT}Du befindet sich im ${IMPORTANT}Spawn-Bereich")
    }

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 29.03.2019 13:07.
     * Current Version: 1.0 (29.03.2019 - 29.03.2019)
     */
    @EventHandler
    fun onPlayerMoveEvent(event: PlayerMoveEvent) {
        val inventory = event.player.inventory
        if (region.isInside(event.from) && !region.isInside(event.to)) inventory.setItem(leaveSlot, null)
        else inventory.setLeave()
    }

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 29.03.2019 12:54.
     * Current Version: 1.0 (29.03.2019 - 29.03.2019)
     */
    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) = event.player.inventory.setLeave()

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 29.03.2019 13:08.
     * Current Version: 1.0 (29.03.2019 - 29.03.2019)
     */
    private fun Inventory.setLeave() = setItem(leaveSlot, Items.LEAVE.itemStack)

    companion object {
        /**
         * @author Lars Artmann | LartyHD
         * Created by Lars Artmann | LartyHD on 29.03.2019 13:10.
         * Current Version: 1.0 (29.03.2019 - 29.03.2019)
         */
        private val leaveSlot = 8
    }

}