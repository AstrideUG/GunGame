package de.astride.gungame.commands

import de.astride.data.DataPlayer
import de.astride.gungame.functions.*
import net.darkdevelopers.darkbedrock.darkness.general.minecraft.fetcher.Fetcher
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
 * Current Version: 1.0 (19.08.2017 - 01.04.2019)
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
    }, { "Nutze als nicht Spieler: /$commandName <Spieler>".sendTo(sender) })
    else getTarget(sender, args[0]) { sendStats(sender, it.uniqueId) }

    private fun sendStats(sender: CommandSender, uuid: UUID) {

        val name = Fetcher.getName(uuid) ?: uuid
        val deaths = uuid.count("PlayerDeathEvent")
        val kills = uuid.count("PlayerRespawnEvent")
        val kd = kills.toFloat() / deaths.toFloat()
        val deathStreak = uuid.maxStreak("PlayerDeathEvent", "PlayerRespawnEvent")
        val killStreak = uuid.maxStreak("PlayerRespawnEvent", "PlayerDeathEvent")

        val textPrefix = "$PREFIX$TEXT"
        val separator = "$IMPORTANT: $PRIMARY"
        val lineSeparator = "$PREFIX$TEXT$DESIGN                                                               "
        val messages = arrayOf(
            "$PREFIX$IMPORTANT$DESIGN                         $IMPORTANT[ $PRIMARY${EXTRA}STATS$IMPORTANT ]$DESIGN                         ",
            "$PREFIX$IMPORTANT$DESIGN                   $IMPORTANT[ $PRIMARY$EXTRA$name$IMPORTANT ]$DESIGN                   ",
            "${textPrefix}Rang$separator${uuid.rank}",
            "${textPrefix}Points$separator${uuid.points()}",
            lineSeparator,
            "${textPrefix}Deaths$separator$deaths",
            "${textPrefix}Kills$separator$kills",
            "${textPrefix}K/D$separator$kd",
            lineSeparator,
            uuid.toPlayer()?.run {
                "${textPrefix}DeathStreak$separator${uuid.streak("PlayerDeathEvent", "PlayerRespawnEvent")}"
            },
            uuid.toPlayer()?.run {
                "${textPrefix}KillStreak$separator${uuid.streak("PlayerRespawnEvent", "PlayerDeathEvent")}"
            },
            lineSeparator,
            "${textPrefix}MaxDeathStreak$separator$deathStreak",
            "${textPrefix}MaxKillStreak$separator$killStreak",
            lineSeparator,
            "${textPrefix}Bought LevelUps$separator${uuid.count("bought-LevelUp")}",
            "${textPrefix}Bought MagicHeal$separator${uuid.count("bought-MagicHeal")}",
            "${textPrefix}Bought InstantKiller$separator${uuid.count("bought-InstantKiller")}",
            "${textPrefix}Bought KeepInventory$separator${uuid.count("bought-KeepInventory")}",
            lineSeparator,
            "${textPrefix}Used MagicHeal$separator${uuid.count("used-MagicHeal")}",
            "${textPrefix}Used InstantKiller$separator${uuid.count("used-InstantKiller")}",
            "${textPrefix}Used KeepInventory$separator${uuid.count("used-KeepInventory")}",
            lineSeparator,
            "${textPrefix}Changed Shop Color$separator${uuid.count("shop-change-color")}",
            "${textPrefix}Shop openings$separator${uuid.count("shop-change-color", uuid.activeActions.filter {
                val player = it.meta["player"] as? DataPlayer ?: return@filter false
                !player.speeds.isSneaking
            })}",
            "$PREFIX$IMPORTANT$DESIGN                         $IMPORTANT[ $PRIMARY${EXTRA}STATS$IMPORTANT ]$DESIGN                         "
        )
        sender.sendMessage(messages)

    }

    companion object {
        private val config = configService.config.commands.stats
    }

}
