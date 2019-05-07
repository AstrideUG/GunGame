package de.astride.gungame.setup

import de.astride.gungame.functions.configService
import de.astride.gungame.functions.isSetup
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.events.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.events.listen
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.events.unregister
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.listenInventories
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.listenTop
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.schedule
import net.darkdevelopers.darkbedrock.darkness.spigot.manager.game.EventsTemplate
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.TEXT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.universal.builder.textcomponent.builder
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.UNDERLINE
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 07.05.2019 13:19.
 * Current Version: 1.0 (07.05.2019 - 07.05.2019)
 */
object Events : EventsTemplate() {

    fun setup(plugin: Plugin) {

        //perform setup help command
        if (isSetup) listen<PlayerJoinEvent>(plugin) { event ->
            val player = event.player
            val config = configService.config.commands.gungame
            if (!player.hasPermission(config.permission)) return@listen
            if (!player.hasPermission("gungame.setup.auto")) return@listen

            plugin.schedule(delay = 2) {
                val command = "${config.name} setup help"
                val hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent("${TEXT}Klick hier!")))
                val component = TextComponent().builder()
                    .setText("${Messages.PREFIX}$GREEN${UNDERLINE}Starte das Setup jetzt!")
                    .setHoverEvent(hoverEvent)
                    .setClickEvent(ClickEvent(ClickEvent.Action.RUN_COMMAND, "/$command"))
                    .build()
                player.spigot().sendMessage(component)
                player.performCommand(command)
            }
        }.add()

        //impl setup GUI
        setupInventory(plugin)

    }

    private fun setupInventory(plugin: Plugin) {

        //block item movement
        setOf(Setup.all, Setup.shops, Setup.maps).listenTop(plugin) { it.cancel() }.add()

        //back Item impl
        listenInventories(plugin, acceptCurrentItem = { it == Setup.backItem }) { event ->
            val whoClicked = event.whoClicked ?: return@listenInventories
            when (whoClicked.openInventory.topInventory) {
                Setup.shops -> whoClicked.openInventory(Setup.all)
                Setup.maps -> whoClicked.openInventory(Setup.all)
            }
        }.add()

        //open maps
        Setup.all.listenTop(plugin, acceptSlot = { it == 1 }) { event ->
            event.whoClicked.openInventory(Setup.maps)

            //TODO: generate maps display-items
        }.add()

        //open shops
        Setup.all.listenTop(plugin, acceptSlot = { it == 3 }) { event ->
            event.whoClicked.openInventory(Setup.shops)
            //TODO: generate shop display-items
        }.add()


        Setup.shops.listenTop(plugin) {

        }.add()
        Setup.maps.listenTop(plugin) {

        }.add()

    }

    fun reset() {
        listener.unregister()
        listener.clear()
    }


}