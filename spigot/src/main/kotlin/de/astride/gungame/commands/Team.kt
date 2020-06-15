package de.astride.gungame.commands

import de.astride.gungame.functions.allowTeams
import de.astride.gungame.functions.configService
import de.astride.gungame.functions.teamRequests
import de.astride.gungame.functions.teams
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.isPlayer
import net.darkdevelopers.darkbedrock.darkness.universal.builder.textcomponent.builder
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
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

            if (args.size == 2) when (args[1].toLowerCase()) {
                "end" -> if (playerUniqueId in teams[targetUniqueId] ?: emptySet<UUID>()) {
                    teams[targetUniqueId]?.remove(playerUniqueId)
                    teams[playerUniqueId]?.remove(targetUniqueId)

                    messages.playerNowYouAreNotInATeamWith.map {
                        it?.replace("@target@", target.name, true)?.replace("@player@", player.name, true)
                    }.sendTo(sender)
                    messages.targetNowYouAreNotInATeamWith.map {
                        it?.replace("@target@", target.name, true)?.replace("@player@", player.name, true)
                    }.sendTo(target)

                } else messages.targetAreNoInATeamWithYou.map {
                    it?.replace("@target@", target.name, true)
                }.sendTo(sender)
                confirmedKey -> when (playerUniqueId) {
                    !in teamRequests[targetUniqueId] ?: emptySet<UUID>() -> messages.noRequestsKnown.map {
                        it?.replace("@target@", target.name, true)?.replace("@player@", player.name, true)
                    }.sendTo(sender)
                    !in teams[targetUniqueId] ?: emptySet<UUID>() -> {
                        teams.getOrPut(targetUniqueId) { mutableSetOf() } += playerUniqueId
                        teams.getOrPut(playerUniqueId) { mutableSetOf() } += targetUniqueId
                        messages.playerNowYouAreInATeamWith.map {
                            it?.replace("@target@", target.name, true)?.replace("@player@", player.name, true)
                        }.sendTo(sender)
                        messages.targetNowYouAreInATeamWith.map {
                            it?.replace("@target@", target.name, true)?.replace("@player@", player.name, true)
                        }.sendTo(target)

                        teamRequests.getOrPut(targetUniqueId) { mutableSetOf() } -= playerUniqueId
                    }
                    else -> messages.playerAlreadyInTeam.map {
                        it?.replace("@target@", target.name, true)
                    }.sendTo(sender)
                }
                else -> sendUseMessage(sender)
            } else {
                messages.teamRequestToTarget.map {
                    TextComponent(
                        it.orEmpty()
                            .replace("@target@", target.name, true)
                            .replace("@player@", player.name, true)
                    ).builder()
                        .setHoverEvent(
                            HoverEvent(
                                HoverEvent.Action.SHOW_TEXT,
                                arrayOf(TextComponent(messages.teamRequestToTargetHover))
                            )
                        )
                        .setClickEvent(
                            ClickEvent(ClickEvent.Action.RUN_COMMAND, "/$commandName ${args[0]} $confirmedKey")
                        )
                        .build()
                }.forEach { player.spigot().sendMessage(it) }
                messages.teamRequestToPlayer.map {
                    it?.replace("@target@", target.name, true)?.replace("@player@", player.name, true)
                }.sendTo(target)
                teamRequests.getOrPut(playerUniqueId) { mutableSetOf() } += targetUniqueId
            }

        } else messages.failedTeamsNotAllowed.sendTo(sender)

    }

    companion object {
        private val config get() = configService.config.commands.team
        private val messages get() = de.astride.gungame.functions.messages.commands.team

        private const val confirmedKey = "confirmed"
    }

}
