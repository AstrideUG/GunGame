package de.astride.gungame.commands

import de.astride.gungame.functions.configService
import de.astride.gungame.functions.isAllowTeams
import de.astride.gungame.functions.sendScoreBoard
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendSubTitle
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTimings
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTitle
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.IMPORTANT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.TEXT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.08.2017 14:30.
 * Current Version: 1.0 (27.03.2019 - 01.04.2019)
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
        if (l > 0) {
            val time = Utils.getTime(l / 1000)
            sender.sendMessage("${Messages.PREFIX}${TEXT}Teams kann nur alle ${IMPORTANT}5 Minuten ${TEXT}genutzt werden ($IMPORTANT$time$TEXT)")
            return
        }

        lastUse = System.currentTimeMillis()

        isAllowTeams = !isAllowTeams
        val a = if (isAllowTeams) "erlaubt" else "verboten"

        Utils.goThroughAllPlayers {
            it.playSound(it.location, Sound.ENDERDRAGON_DEATH, 100f, 1f)
            it.sendTitle("${IMPORTANT}Teams")
            it.sendSubTitle("${TEXT}sind jetzt $a")
            it.sendTimings(10, 40, 10)
            it.sendScoreBoard()
        }
        Bukkit.broadcastMessage("${Messages.PREFIX}${TEXT}Teams sind jetzt $a")

    }

    companion object {
        private val config = configService.config.commands.teams
    }

}
