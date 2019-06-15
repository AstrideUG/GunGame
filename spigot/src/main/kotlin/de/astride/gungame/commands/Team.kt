package de.astride.gungame.commands

import de.astride.gungame.functions.allowTeams
import de.astride.gungame.functions.configService
import de.astride.gungame.functions.teams
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.IMPORTANT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.TEXT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.isPlayer
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.08.2017 14:30.
 * Current Version: 1.0 (19.08.2017 - 16.06.2019)
 */
class Team(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    commandName = config.name,
    permission = config.permission,
    usage = "<Spieler> [end]",
    minLength = 1,
    maxLength = 2,
    aliases = *config.aliases
) {

    override fun perform(sender: CommandSender, args: Array<String>) = sender.isPlayer { player ->

        if (allowTeams.result) getTarget(sender, args[0]) { target ->
            val playerUniqueId = player.uniqueId
            val targetUniqueId = target.uniqueId

            when {
                args.size == 2 -> if (args[1].toLowerCase() == "end") {
                    if (playerUniqueId in teams[targetUniqueId] ?: emptySet<UUID>()) {
                        teams[targetUniqueId]?.remove(playerUniqueId)
                        teams[playerUniqueId]?.remove(targetUniqueId)
                        "${Messages.PREFIX}${TEXT}Du bist jetzt nicht mehr mit ${target.displayName} in einem Team".sendTo(
                            sender
                        )
                        "${Messages.PREFIX}${TEXT}Du bist jetzt nicht mehr mit ${player.displayName} in einem Team".sendTo(
                            target
                        )
                    } else sender.sendMessage("PREFIX ${TEXT}Der Spieler $IMPORTANT${target.name}$TEXT ist nicht mit dir in einem Team")
                } else sendUseMessage(sender)
                playerUniqueId !in teams[targetUniqueId] ?: emptySet<UUID>() -> {
                    teams.getOrPut(targetUniqueId) { mutableSetOf() } += playerUniqueId
                    teams.getOrPut(playerUniqueId) { mutableSetOf() } += targetUniqueId
                    "${Messages.PREFIX}${TEXT}Du bist jetzt mit ${target.displayName} in einem Team".sendTo(sender)
                    "${Messages.PREFIX}${TEXT}Du bist jetzt mit ${player.displayName} in einem Team".sendTo(target)
                }
                else -> sender.sendMessage("PREFIX ${TEXT}Der Spieler $IMPORTANT${target.name}$TEXT ist schon mit dir im Team")
            }

        } else messages.failedTeamsNotAllowed.sendTo(sender)

    }

    companion object {
        private val config get() = configService.config.commands.team
        private val messages get() = de.astride.gungame.functions.messages.commands.team
    }

}
