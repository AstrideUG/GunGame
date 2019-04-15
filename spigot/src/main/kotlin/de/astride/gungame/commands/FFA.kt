package de.astride.gungame.commands

import de.astride.gungame.functions.configService
import de.astride.gungame.functions.messages
import de.astride.gungame.functions.replace
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 18:33.
 * Current Version: 1.0 (04.04.2019 - 15.04.2019)
 */
class FFA(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    commandName = config.name,
    permission = config.permission,
    usage = "save <actions/kit> [Path]" +
            "|load <messages> [Path]",
    minLength = 2,
    maxLength = 3,
    aliases = *config.aliases
) {

    override fun perform(sender: CommandSender, args: Array<String>) {

        val configData = if (args.size < 3) when (args[0].toLowerCase()) {
            "save" -> when (args[1].toLowerCase()) {
                "actions" -> configService.actions.configData
                "kit" -> configService.kit.configData
                else -> {
                    sendUseMessage(sender)
                    return
                }
            }
            "load" -> when (args[1].toLowerCase()) {
                "messages" -> messages.configData
                else -> {
                    sendUseMessage(sender)
                    return
                }
            }
            else -> {
                sendUseMessage(sender)
                return
            }
        } else generatePath(args[2])

        val path = configData.file.toPath()
        val absolutePath = path.toAbsolutePath()
        val transform: (String?) -> String? = {
            var result = it.replace("path", path).replace("absolute-path", absolutePath)
            for (i in 0 until args.size) result = result.replace("arg$i", args[i])
            result
        }

        when (args[0].toLowerCase()) {
            "save" -> {
                when (args[1].toLowerCase()) {
                    "actions" -> configService.actions.save(configData = configData)
                    "kit" -> configService.kit.save(configData = configData)
                    else -> {
                        sendUseMessage(sender)
                        return
                    }
                }
                messages.commands.ffa.successfullySaved.map(transform).sendTo(sender)
            }
            "load" -> {
                configService.Messages(configData)
                messages.commands.ffa.successfullyLoaded.map(transform).sendTo(sender)
            }
            else -> {
                sendUseMessage(sender)
                return
            }
        }

    }

    private fun generatePath(input: String): ConfigData {
        val path = input.split('/')
        val directory = "${javaPlugin.dataFolder}${File.separator}${path.dropLast(1).joinToString(File.separator)}"
        return ConfigData(directory, path.last())
    }

    companion object {
        private val config get() = configService.config.commands.ffa
    }

}
