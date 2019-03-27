package de.astride.gungame.commands

import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.TEXT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.08.2017 14:30.
 * Current Version: 1.0 (19.08.2017  - 27.03.2019)
 */
class Stats(javaPlugin: JavaPlugin) : net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command(
    javaPlugin,
    "Stats",
    "gungame.commands.stats",
    usage = "[Spieler]",
    maxLength = 1
) {
    override fun perform(sender: CommandSender, args: Array<String>) {
        sender.sendMessage("${Messages.PREFIX}${TEXT}not impl")
//        if (args.isEmpty()) {
//            if (sender !is Player) {
//                sender.sendMessage(Messages.getOnlyPlayer())
//                sender.sendMessage("Nutze als nicht Spieler: /Stats <Spieler>")
//            }
//            sendStats(sender, sender.uniqueId, sender.getName())
//        }
//        if (args.size == 1) {
//            val target = Bukkit.getPlayer(args[0])
//            if (target == null) {
//                //				UUID uuid = Saves.getStatsAPI().getUUID(args[0]);
//                //				if (uuid == null)
//                //				{
//                //					sender.sendMessage(Messages.getPlayerNotInDataBase());
//                //					return true;
//                //				}
//                sender.sendMessage(Messages.getNotOnline())
//                //				ResultSet resultSet = Saves.getMySQL().query("SELECT `uuid` FROM Stats WHERE `name` = '" + args[0] + "'");
//                //				try
//                //				{
//                //					if (!resultSet.next())
//                //					{
//                //						sender.sendMessage(Messages.getPlayerNotInDataBase());
//                //						return true;
//                //					}
//                //				} catch (SQLException ex)
//                //				{
//                //					ex.printStackTrace();
//                //				}
//                //				sendOfflineStats(sender, uuid);
//            }
//            sendStats(sender, target.uniqueId, target.name)
//        }
//        sender.sendMessage(Messages.PREFIX.toString() + TEXT + "/Stats [Spieler]")
    }


//    private fun sendStats(sender: CommandSender, uuid: UUID, playerName: String) {
//        val kd = Saves.getStatsAPI().get(uuid, "Kills") as Float / Saves.getStatsAPI().get(uuid, "Tode") as Float
//        sender.sendMessage(Messages.PREFIX.toString() + TEXT + "Hir sind die Stats von " + IMPORTANT + playerName + TEXT + " (Online) ...")
//        sender.sendMessage(Messages.PREFIX.toString() + IMPORTANT + DESIGN + "                         " + IMPORTANT + "[ " + PRIMARY + EXTRA + "STATS" + IMPORTANT + " ]" + DESIGN + "                         ")
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "Rang" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().getRank(
//                uuid,
//                "Punkte"
//            )
//        )
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "Punkte" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "Punkte"
//            )
//        )
//        sender.sendMessage(Messages.PREFIX.toString() + TEXT + DESIGN + "                                                               ")
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "Tode" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "Tode"
//            )
//        )
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "WasserTode" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "WasserTode"
//            )
//        )
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "Kills" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "Kills"
//            )
//        )
//        sender.sendMessage(Messages.PREFIX.toString() + TEXT + "K/D" + IMPORTANT + ": " + PRIMARY + kd)
//        sender.sendMessage(Messages.PREFIX.toString() + TEXT + DESIGN + "                                                               ")
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "KillStreak" + IMPORTANT + ": " + PRIMARY + Saves.getKillStreak().get(
//                playerName
//            )
//        )
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "MaxKillStreak" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "MaxKillStreak"
//            )
//        )
//        sender.sendMessage(Messages.PREFIX.toString() + TEXT + DESIGN + "                                                               ")
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "UsedLevelUps" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "UsedLevelUps"
//            )
//        )
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "UsedHealer" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "UsedHealer"
//            )
//        )
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "UsedKiller" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "UsedKiller"
//            )
//        )
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "UsedKeepInventory" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "UsedKeepInventory"
//            )
//        )
//        sender.sendMessage(Messages.PREFIX.toString() + IMPORTANT + DESIGN + "                         " + IMPORTANT + "[ " + PRIMARY + EXTRA + "STATS" + IMPORTANT + " ]" + DESIGN + "                         ")
//    }
//
//    private fun sendOfflineStats(sender: CommandSender, uuid: UUID) {
//        val kd = Saves.getStatsAPI().get(uuid, "Kills") as Float / Saves.getStatsAPI().get(uuid, "Tode") as Float
//        sender.sendMessage(Messages.PREFIX.toString() + IMPORTANT + DESIGN + "                         " + IMPORTANT + "[ " + PRIMARY + EXTRA + "STATS" + IMPORTANT + " ]" + DESIGN + "                         ")
//        sender.sendMessage(Messages.PREFIX.toString() + IMPORTANT + DESIGN + "                       " + IMPORTANT + "[ " + PRIMARY + EXTRA + "STATS" + IMPORTANT + " ]" + DESIGN + "                       ")
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "Rang" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().getRank(
//                uuid,
//                "Punkte"
//            )
//        )
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "Punkte" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "Punkte"
//            )
//        )
//        sender.sendMessage(Messages.PREFIX.toString() + TEXT + DESIGN + "                                                               ")
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "Tode" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "Tode"
//            )
//        )
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "WasserTode" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "WasserTode"
//            )
//        )
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "Kills" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "Kills"
//            )
//        )
//        sender.sendMessage(Messages.PREFIX.toString() + TEXT + "K/D" + IMPORTANT + ": " + PRIMARY + kd)
//        sender.sendMessage(Messages.PREFIX.toString() + TEXT + DESIGN + "                                                               ")
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "MaxKillStreak" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "MaxKillStreak"
//            )
//        )
//        sender.sendMessage(Messages.PREFIX.toString() + TEXT + DESIGN + "                                                               ")
//        sender.sendMessage(
//            "${Messages.PREFIX}${TEXT}UsedLevelUps$IMPORTANT: $PRIMARY" + Saves.getStatsAPI().get(
//                uuid,
//                "UsedLevelUps"
//            )
//        )
//        sender.sendMessage(
//            "${Messages.PREFIX}${TEXT}UsedHealer$IMPORTANT: $PRIMARY" + Saves.getStatsAPI().get(
//                uuid,
//                "UsedHealer"
//            )
//        )
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "UsedKiller" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "UsedKiller"
//            )
//        )
//        sender.sendMessage(
//            Messages.PREFIX.toString() + TEXT + "UsedKeepInventory" + IMPORTANT + ": " + PRIMARY + Saves.getStatsAPI().get(
//                uuid,
//                "UsedKeepInventory"
//            )
//        )
//        sender.sendMessage("${Messages.PREFIX}$IMPORTANT$DESIGN                         $IMPORTANT[ $PRIMARY${EXTRA}STATS$IMPORTANT ]$DESIGN                         ")
//    }
}
