package de.astride.gungame.commands

import de.astride.gungame.functions.configService
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 18:33.
 * Current Version: 1.0 (04.04.2019 - 04.04.2019)
 */
class GunGame(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    commandName = config.name,
    permission = config.permission,
    usage = "save actions",
    minLength = 2,
    maxLength = 2,
    aliases = *config.aliases
) {

    override fun perform(sender: CommandSender, args: Array<String>) {

        "${Messages.PREFIX}${Colors.TEXT}Actions gesichert".sendTo(sender)

    }

    companion object {
        private val config = configService.config.commands.gungame
    }

}
