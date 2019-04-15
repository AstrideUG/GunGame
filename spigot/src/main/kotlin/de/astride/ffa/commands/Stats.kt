package de.astride.ffa.commands

import de.astride.ffa.functions.configService
import de.astride.ffa.functions.replace
import de.astride.ffa.functions.withReplacements
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.isPlayer
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.08.2017 14:30.
 * Current Version: 1.0 (19.08.2017 - 06.04.2019)
 */
class Stats(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    commandName = config.name,
    permission = config.permission,
    usage = "[Spieler]",
    maxLength = 1,
    aliases = *config.aliases
) {

    override fun perform(sender: CommandSender, args: Array<String>) = if (args.isEmpty()) sender.isPlayer({
        sendStats(sender, it.uniqueId)
    }, { messages.failedPlayer.map { it.replace("command-name", commandName) }.sendTo(sender) })
    else getTarget(sender, args[0]) { sendStats(sender, it.uniqueId) }

    private fun sendStats(sender: CommandSender, uuid: UUID): Unit =
        messages.successfully.withReplacements(uuid).sendTo(sender)

    companion object {
        private val config get() = configService.config.commands.stats
        private val messages get() = de.astride.ffa.functions.messages.commands.stats
    }

}
