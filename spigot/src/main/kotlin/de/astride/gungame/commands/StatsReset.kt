package de.astride.gungame.commands

import de.astride.gungame.functions.actions
import de.astride.gungame.functions.activeActions
import de.astride.gungame.functions.configService
import de.astride.gungame.kits.gunGameLevel
import de.astride.gungame.kits.upgrade
import de.astride.gungame.shop.items.keepInventory
import de.astride.gungame.stats.Action
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.IMPORTANT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.TEXT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.isPlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.08.2017 14:30.
 * Current Version: 1.0 (19.08.2017 - 01.04.2019)
 */
class StatsReset(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    commandName = config.name,
    permission = config.permission,
    usage = "<Spieler>:gungame.commands.statsreset.other",
    maxLength = 2,
    aliases = *config.aliases
) {

    override fun perform(sender: CommandSender, args: Array<String>) {
        when {
            args.isEmpty() ||
                    args.size == 1 && !args[0].equals("confirmed", true) ||
                    args.size == 2 && !args[1].equals("confirmed", true) -> sender.sendConfirm()
            args.size == 1 -> sender.isPlayer(
                {
                    if (it.uniqueId.activeActions.isEmpty()) {
                        "${Messages.PREFIX}${TEXT}Du hast keine Stats die man resetten könnte!".sendTo(sender)
                        return@isPlayer
                    }
                    it.reset()
                    it.successMessagePlayer()
                },
                { sender.sendMessage("Nutze: \"/$commandName <Spieler>\" da du kein Spieler bist") })
            args.size == 2 && !args[0].equals("confirmed", true) ->
                hasPermission(sender, "gungame.commands.statsreset.other") {
                    getTarget(sender, args[0]) { target ->
                        if (target.uniqueId.activeActions.isEmpty()) {
                            "${Messages.PREFIX}$TEXT${target.name} hat keine Stats die man resetten könnte!"
                                .sendTo(sender)
                            return@getTarget
                        }
                        target.reset()
                        target.successMessagePlayer()
                        sender.successMessageTarget(target.name)
                    }
                }
        }
    }

    private fun Player.reset() {

        keepInventory = false
        gunGameLevel = 0
        upgrade()

        uniqueId.actions += Action(this@StatsReset.javaClass.simpleName, mapOf("player" to this))
    }

    private fun CommandSender.successMessagePlayer() {
        sendMessage("")
        sendMessage("${Messages.PREFIX}${TEXT}Deine$IMPORTANT GunGame Stats$TEXT wurden zurückgesetzt")
        sendMessage("")
    }

    private fun CommandSender.successMessageTarget(name: String) {
        sendMessage("")
        sendMessage("${Messages.PREFIX}${TEXT}Du hast die$IMPORTANT GunGame Stats$TEXT von$IMPORTANT $name$TEXT zurückgesetzt")
        sendMessage("")
    }

    private fun CommandSender.sendConfirm() =
        sendMessage("${Messages.PREFIX}${TEXT}Nutze $IMPORTANT\"/$commandName [Spieler] confirmed\"$TEXT um deine$IMPORTANT GunGame Stats$TEXT zurückzusetzen")

    companion object {
        private val config = configService.config.commands.statsReset
    }

}
