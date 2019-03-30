package de.astride.gungame.commands

import de.astride.gungame.functions.mySQL
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.darkdevelopers.darkbedrock.darkness.general.minecraft.fetcher.Fetcher
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.sql.ResultSet

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.08.2017 14:30
 * Current Version: 1.0 (19.08.2017 - 29.03.2019)
 */
class Top(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    "Top",
    "gungame.commands.top"
) {

    override fun perform(sender: CommandSender, args: Array<String>) {

        sender.sendMessage("${Messages.PREFIX}$IMPORTANT$DESIGN                         $IMPORTANT[ $PRIMARY${EXTRA}TOP 10$IMPORTANT ]$DESIGN                         ")

        GlobalScope.launch {
            val resultSet: ResultSet =
                mySQL.query("SELECT * FROM `GunGame` ORDER BY `GunGame`.`punkte` DESC")

            for (i in 1..10) {
                if (!resultSet.next()) return@launch
                val name: String = Fetcher.getName(resultSet.getString("uuid")) ?: "unknown"
                val points = resultSet.getInt("points")
                sender.sendMessage("${Messages.PREFIX}$TEXT#$i$IMPORTANT: $PRIMARY$name$TEXT ($IMPORTANT$points$TEXT)")
            }

        }

        sender.sendMessage("${Messages.PREFIX}$IMPORTANT$DESIGN                         $IMPORTANT[ $PRIMARY${EXTRA}TOP 10$IMPORTANT ]$DESIGN                         ")

    }

}
