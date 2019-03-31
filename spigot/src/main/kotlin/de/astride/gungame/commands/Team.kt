package de.astride.gungame.commands

import de.astride.gungame.functions.configService
import de.astride.gungame.functions.isAllowTeams
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
 * Current Version: 1.0 (19.08.2017 - 01.04.2019)
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

        if (isAllowTeams) getTarget(sender, args[0]) { target ->
            //                if (Saves.getTeams().get(target.getName()).equals(sender.name)) {
//                    sender.sendMessage(Messages.getPrefix() + Colors.TEXT + "Der Spieler " + Colors.IMPORTANT + target.getName() + Colors.TEXT + " ist schon mit dir im Team")
//                    return true
//                }
//                "${Messages.PREFIX}${Colors.TEXT}Du bist jetzt mit ${target.displayName} in einem Team".sendTo(sender)
            "${Messages.PREFIX}${Colors.TEXT}Hm... leider wurde dieses Feature noch nicht programmiert"
                .sendTo(sender)
        } else "${Messages.PREFIX}${Colors.TEXT}Teams sind grade verboten".sendTo(sender)

    }

    companion object {
        private val config = configService.config.commands.team
    }

}
