package de.astride.gungame.commands

import de.astride.gungame.functions.activeActions
import de.astride.gungame.functions.count
import de.astride.gungame.functions.points
import de.astride.gungame.functions.rank
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.isPlayer
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toPlayer
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages.PREFIX
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.08.2017 14:30.
 * Current Version: 1.0 (19.08.2017  - 27.03.2019)
 */
class Stats(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    "Stats",
    "gungame.commands.stats",
    usage = "[Spieler]",
    maxLength = 1
) {

    override fun perform(sender: CommandSender, args: Array<String>) = if (args.isEmpty()) sender.isPlayer({
        sendStats(sender, it.uniqueId)
    }, { "Nutze als nicht Spieler: /$commandName <Spieler>".sendTo(sender) })
    else getTarget(sender, args[0]) { sendStats(sender, it.uniqueId) }

    private fun sendStats(sender: CommandSender, uuid: UUID) {

        val deaths = uuid.count("PlayerDeathEvent")
        val kills = uuid.count("PlayerRespawnEvent")
        val kd = kills.toFloat() / deaths.toFloat()
        "$PREFIX$IMPORTANT$DESIGN                         $IMPORTANT[ $PRIMARY${EXTRA}STATS$IMPORTANT ]$DESIGN                         "
            .sendTo(sender)
        "$PREFIX${TEXT}Rang$IMPORTANT: $PRIMARY${uuid.rank}".sendTo(sender)
        "$PREFIX${TEXT}Points$IMPORTANT: $PRIMARY${uuid.points()}".sendTo(sender)
        "$PREFIX$TEXT$DESIGN                                                               ".sendTo(sender)
        "$PREFIX${TEXT}Deaths$IMPORTANT: $PRIMARY$deaths".sendTo(sender)
        "$PREFIX${TEXT}Kills$IMPORTANT: $PRIMARY$kills".sendTo(sender)
        //TODO: "$PREFIX${TEXT}Kills by water$IMPORTANT: $PRIMARY${uuid.count("PlayerRespawnEvent")}"
        "$PREFIX${TEXT}K/D$IMPORTANT: $PRIMARY$kd".sendTo(sender)
        "$PREFIX$TEXT$DESIGN                                                               ".sendTo(sender)
        "$PREFIX${TEXT}MaxDeathSteak$IMPORTANT: $PRIMARY${"TODO"}".sendTo(sender)//TODO Add MaxDeathSteak
        "$PREFIX${TEXT}MaxKillStreak$IMPORTANT: $PRIMARY${"TODO"}".sendTo(sender) //TODO Add MaxKillStreak
        uuid.toPlayer()?.apply {
            val count = uuid.count("PlayerDeathEvent", uuid.activeActions.takeWhile { it.id == "PlayerRespawnEvent" })
            "$PREFIX${TEXT}DeathSteak$IMPORTANT: $PRIMARY$count".sendTo(sender)
        }
        uuid.toPlayer()?.apply {
            val count = uuid.count("PlayerRespawnEvent", uuid.activeActions.takeWhile { it.id == "PlayerDeathEvent" })
            "$PREFIX${TEXT}KillStreak$IMPORTANT: $PRIMARY$count".sendTo(sender)
        }
        "$PREFIX$TEXT$DESIGN                                                               ".sendTo(sender)
        "$PREFIX${TEXT}Bought LevelUps$IMPORTANT: $PRIMARY${uuid.count("LevelUp")}".sendTo(sender)
        "$PREFIX${TEXT}Bought MagicHeal$IMPORTANT: $PRIMARY${uuid.count("MagicHeal")}".sendTo(sender)
        "$PREFIX${TEXT}Bought InstantKiller$IMPORTANT: $PRIMARY${uuid.count("InstantKiller")}".sendTo(sender)
        "$PREFIX${TEXT}Bought KeepInventory$IMPORTANT: $PRIMARY${uuid.count("KeepInventory")}".sendTo(sender)
        "$PREFIX$IMPORTANT$DESIGN                         $IMPORTANT[ $PRIMARY${EXTRA}STATS$IMPORTANT ]$DESIGN                         "
            .sendTo(sender)
    }

}
