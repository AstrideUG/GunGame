package de.astride.gungame.commands

import de.astride.gungame.functions.configService
import de.astride.gungame.functions.ranks
import de.astride.gungame.functions.replace
import de.astride.gungame.functions.withReplacements
import net.darkdevelopers.darkbedrock.darkness.general.minecraft.fetcher.Fetcher
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.08.2017 14:30
 * Current Version: 1.0 (19.08.2017 - 06.04.2019)
 */
class Top(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    commandName = config.name,
    permission = config.permission,
    aliases = *config.aliases
) {

    override fun perform(sender: CommandSender, args: Array<String>) {

        messages.success.sendTo(sender)
        ranks().asReversed().take(10).forEach { name ->
            messages.entry.map { it.replace("name", name) }.withReplacements(Fetcher.getUUID(name)).sendTo(sender)
        }
        messages.successfully.sendTo(sender)

    }

    companion object {
        private val config get() = configService.config.commands.top
        private val messages get() = de.astride.gungame.functions.messages.commands.top
    }

}
