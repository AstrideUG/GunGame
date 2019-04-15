package de.astride.ffa.commands

import de.astride.ffa.functions.allowTeams
import de.astride.ffa.functions.configService
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.isPlayer
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.08.2017 14:30.
 * Current Version: 1.0 (19.08.2017 - 14.04.2019)
 */
//TODO: impl Team with
class Team(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    commandName = config.name,
    permission = config.permission,
    usage = "<Spieler>",
    minLength = 1,
    maxLength = 1,
    aliases = *config.aliases
) {

    override fun perform(sender: CommandSender, args: Array<String>) = sender.isPlayer {

        if (allowTeams.result) getTarget(sender, args[0]) { target ->
            //                if (Saves.getTeams().get(target.getName()).equals(sender.name)) {
//                    sender.sendMessage(Messages.getPrefix() + Colors.TEXT + "Der Spieler " + Colors.IMPORTANT + target.getName() + Colors.TEXT + " ist schon mit dir im Team")
//                    return true
//                }
//                "${Messages.PREFIX}${Colors.TEXT}Du bist jetzt mit ${target.displayName} in einem Team".sendTo(sender)
            "${Messages.PREFIX}${Colors.TEXT}Hm... leider wurde dieses Feature noch nicht programmiert"
                .sendTo(sender)
        } else messages.failedTeamsNotAllowed.sendTo(sender)

    }

    companion object {
        private val config get() = configService.config.commands.team
        private val messages get() = de.astride.ffa.functions.messages.commands.team
    }

}
