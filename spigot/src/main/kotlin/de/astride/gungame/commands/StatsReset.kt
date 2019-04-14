package de.astride.gungame.commands

import de.astride.gungame.functions.actions
import de.astride.gungame.functions.activeActions
import de.astride.gungame.functions.configService
import de.astride.gungame.functions.replace
import de.astride.gungame.kits.gunGameLevel
import de.astride.gungame.kits.upgrade
import de.astride.gungame.shop.items.keepInventory
import de.astride.gungame.stats.Action
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.isPlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.08.2017 14:30.
 * Current Version: 1.0 (19.08.2017 - 14.04.2019)
 */
class StatsReset(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    commandName = config.name,
    permission = config.permission,
    usage = "[Spieler]:${config.permissionOther}",
    maxLength = 2,
    aliases = *config.aliases
) {

    override fun perform(sender: CommandSender, args: Array<String>) {
        when {
            args.isEmpty() ||
                    args.size == 1 && !args[0].equals(confirmKey, true) ||
                    args.size == 2 && !args[1].equals(confirmKey, true) -> messages.infoConfirm.map {
                it.replace("command-name", commandName).replace("confirm-key", confirmKey)
            }.sendTo(sender)
            args.size == 1 -> sender.isPlayer(
                { player ->
                    if (player.uniqueId.activeActions.isEmpty())
                        messages.failedSelfNothing.sendTo(sender)
                    else {
                        player.reset(player.uniqueId.toString())
                        messages.successfullySelf.sendTo(sender)
                    }
                },
                {
                    messages.failedPlayer.map { it.replace("command-name", commandName) }.sendTo(sender)
                })
            args.size == 2 && !args[0].equals(confirmKey, true) ->
                hasPermission(sender, config.permissionOther) {
                    getTarget(sender, args[0]) { target ->
                        if (target.uniqueId.activeActions.isEmpty())
                            messages.failedTargetNothing.transformAndSend(sender, target.name)
                        else {
                            target.reset((sender as? Player)?.uniqueId?.toString() ?: sender.name)
                            messages.successfullySelfBy.map { it.replace("sender", sender.name) }.sendTo(target)
                            messages.successfullyTarget.transformAndSend(sender, target.name)
                        }
                    }
                }
        }
    }

    private fun List<String?>.transformAndSend(sender: CommandSender, name: String): Unit =
        map { it.replace("target", name) }.sendTo(sender)

    private fun Player.reset(by: String) {

        keepInventory = false
        gunGameLevel = 0
        upgrade()

        uniqueId.actions += Action(
            this@StatsReset.javaClass.simpleName,
            mapOf(/*"player" to this.toDataPlayer(),*/ "by" to by)
        )
    }

    companion object {
        private val config get() = configService.config.commands.statsReset
        private val messages get() = de.astride.gungame.functions.messages.commands.statsReset

        private const val confirmKey = "confirmed"
    }

}
