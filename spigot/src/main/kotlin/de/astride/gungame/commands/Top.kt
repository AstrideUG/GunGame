package de.astride.gungame.commands

import de.astride.gungame.functions.configService
import de.astride.gungame.functions.points
import de.astride.gungame.functions.ranks
import net.darkdevelopers.darkbedrock.darkness.general.minecraft.fetcher.Fetcher
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.08.2017 14:30
 * Current Version: 1.0 (19.08.2017 - 01.04.2019)
 */
class Top(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    commandName = config.name,
    permission = config.permission,
    aliases = *config.aliases
) {

    override fun perform(sender: CommandSender, args: Array<String>) {

        "${Messages.PREFIX}$IMPORTANT$DESIGN                         $IMPORTANT[ $PRIMARY${EXTRA}TOP 10$IMPORTANT ]$DESIGN                         "
            .sendTo(sender)
        ranks().asReversed().take(10).withIndex().forEach {
            "${Messages.PREFIX}$TEXT#${it.index + 1}$IMPORTANT: $PRIMARY${it.value}$TEXT ($IMPORTANT${Fetcher.getUUID(it.value).points()}$TEXT)"
                .sendTo(sender)
        }
        "${Messages.PREFIX}$IMPORTANT$DESIGN                         $IMPORTANT[ $PRIMARY${EXTRA}TOP 10$IMPORTANT ]$DESIGN                         "
            .sendTo(sender)

    }

    companion object {
        private val config = configService.config.commands.top
    }

}
