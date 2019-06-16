package de.astride.gungame.commands

import de.astride.gungame.functions.*
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendSubTitle
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTimings
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTitle
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Utils
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Utils.players
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.08.2017 14:30.
 * Current Version: 1.0 (27.03.2019 - 16.06.2019)
 */
class Teams(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    commandName = config.name,
    permission = config.permission,
    aliases = *config.aliases
) {

    private var lastUse = 0L

    override fun perform(sender: CommandSender, args: Array<String>) {

        val l = lastUse + config.delay - System.currentTimeMillis()
        if (l > 0) messages.failedDelay.map {
            it
                .replace("delay", Utils.getTime(config.delay / 1000))
                .replace("remaining", Utils.getTime(l / 1000))
        }.sendTo(sender) else {

            lastUse = System.currentTimeMillis()

            allowTeams = !allowTeams

            players.forEach { player ->
                player.playSound(player.location, Sound.ENDERDRAGON_DEATH, 100f, 1f)
                player.sendTitle(messages.title)
                player.sendSubTitle(messages.subTitle.replace("allowed", allowTeams.asString))
                player.sendTimings(10, 40, 10)
                player.sendScoreBoard()
                player.sendMessage(messages.successfully.replace("allowed", allowTeams.asString))
            }

            if (!allowTeams.result) teams.clear()

        }

    }

    companion object {
        private val config get() = configService.config.commands.teams
        private val messages get() = de.astride.gungame.functions.messages.commands.teams
    }

}
