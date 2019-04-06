package de.astride.gungame.commands

import de.astride.gungame.functions.allActions
import de.astride.gungame.functions.configService
import de.astride.gungame.functions.messages
import de.astride.gungame.functions.replace
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 18:33.
 * Current Version: 1.0 (04.04.2019 - 06.04.2019)
 */
class GunGame(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    commandName = config.name,
    permission = config.permission,
    usage = "save actions [File]",
    minLength = 2,
    maxLength = 3,
    aliases = *config.aliases
) {

    override fun perform(sender: CommandSender, args: Array<String>) {

        val configData =
            if (args.size < 3) configService.actions.configData else ConfigData(javaPlugin.dataFolder, args[2])
        configService.actions.save(allActions, configData)

        val path = configData.file.toPath()
        val absolutePath = path.toAbsolutePath()
        messages.commands.gungame.successfully.map {
            it.replace("path", path).replace("absolute-path", absolutePath)
        }.sendTo(sender)

    }

    companion object {
        private val config get() = configService.config.commands.gungame
    }

}
