package de.astride.gungame.listener

import de.astride.gungame.functions.messages
import de.astride.gungame.functions.teams
import net.darkdevelopers.darkbedrock.darkness.spigot.events.PlayerDisconnectEvent
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.events.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.events.listen
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toPlayer
import net.darkdevelopers.darkbedrock.darkness.spigot.manager.game.EventsTemplate
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.plugin.Plugin
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 16.06.2019 01:15.
 * Last edit 16.06.2019
 */
object TeamsEvents : EventsTemplate() {

    fun setup(plugin: Plugin) {
        listen<EntityDamageByEntityEvent>(plugin) { event ->
            val entity = event.entity as? Player ?: return@listen
            val damager = event.damager as? Player ?: return@listen
            if (damager.uniqueId !in teams[entity.uniqueId] ?: emptySet<UUID>()) return@listen
            event.cancel()
        }.add()
        listen<PlayerDisconnectEvent>(plugin) { event ->
            val uniqueId = event.player.uniqueId ?: return@listen
            teams[uniqueId]?.forEach { uuid ->
                uuid.toPlayer()?.sendMessage(messages.commands.team.teamPlayerDisconnected.map {
                    it
                        ?.replace("@uniqueId@", uniqueId.toString(), true)
                        ?.replace("@player@", uniqueId.toPlayer()?.name.toString(), true)
                }.toTypedArray())
            }
            teams.remove(uniqueId)
            teams.values.forEach { members -> members.removeIf { it == uniqueId } }
        }.add()
    }

}