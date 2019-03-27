package de.astride.gungame.commands

import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.08.2017 14:30
 * Current Version: 1.0 (19.08.2017 - 27.03.2019)
 */
class Top(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    "Top",
    "gungame.commands.top"
) {

    override fun perform(sender: CommandSender, args: Array<String>) {

        sender.sendMessage("${Messages.PREFIX}$IMPORTANT$DESIGN                         $IMPORTANT[ $PRIMARY${EXTRA}TOP 10$IMPORTANT ]$DESIGN                         ")

        sender.sendMessage("${Messages.PREFIX}$TEXT not impl")
//        val name: String = resultSet.getString("name")
//        val ponits = Saves.getStatsAPI().get(UUID.fromString(resultSet.getString("uuid")), "punkte")
//
//        val resultSet: ResultSet =
//            Saves.getStatsMySQL().query("SELECT * FROM `GunGame` ORDER BY `GunGame`.`punkte` DESC")
//
//        val list: Sequence<Any>
//        list
//            .take(10)
//            .withIndex()
//            .forEach { (index, any) ->
//                sender.sendMessage("${Messages.PREFIX}$TEXT#$index$IMPORTANT: $PRIMARY$name$TEXT ($IMPORTANT$ponits$TEXT)")
//            }

        sender.sendMessage("${Messages.PREFIX}$IMPORTANT$DESIGN                         $IMPORTANT[ $PRIMARY${EXTRA}TOP 10$IMPORTANT ]$DESIGN                         ")

    }

}
