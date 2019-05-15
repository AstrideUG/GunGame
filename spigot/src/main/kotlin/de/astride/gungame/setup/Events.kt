package de.astride.gungame.setup

import de.astride.gungame.functions.configService
import de.astride.gungame.functions.isSetup
import net.darkdevelopers.darkbedrock.darkness.spigot.events.AnvilClickEvent
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.events.listen
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.execute
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.listenInventories
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.listenTop
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.schedule
import net.darkdevelopers.darkbedrock.darkness.spigot.manager.game.EventsTemplate
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.SECONDARY
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.TEXT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.AnvilGUI
import net.darkdevelopers.darkbedrock.darkness.universal.builder.textcomponent.builder
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.UNDERLINE
import org.bukkit.Material
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 07.05.2019 13:19.
 * Current Version: 1.0 (07.05.2019 - 08.05.2019)
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

        val commandName = configService.config.commands.gungame.name

        //block item movement
        setOf(Setup.all.name, Setup.shops.name, Setup.maps.name, "${SECONDARY}GunGame Setup Shops Edit")
            .listenTop(plugin, cancel = true).add()

        //back Item impl
        listenInventories(plugin, acceptCurrentItem = { it == Setup.backItem }) { event ->
            val whoClicked = event.whoClicked ?: return@listenInventories
            when (whoClicked.openInventory.topInventory.name) {
                Setup.shops.name -> event.whoClicked.execute("$commandName setup all")
                Setup.maps.name -> event.whoClicked.execute("$commandName setup all")
                "${SECONDARY}GunGame Setup Shops Edit" -> event.whoClicked.execute("$commandName setup shops")
                "${SECONDARY}GunGame Setup Maps Edit" -> event.whoClicked.execute("$commandName setup rawMaps")
            }
        }.add()

        //shops

        //open maps gui
        Setup.all.listenTop(plugin, acceptSlot = { it == 1 }) { event ->
            event.whoClicked.execute("$commandName setup maps")
        }.add()

        //open shops gui
        Setup.all.listenTop(plugin, acceptSlot = { it == 3 }) { event ->
            event.whoClicked.execute("$commandName setup shops")
        }.add()

        //open shops edit gui
        Setup.shops.listenTop(plugin, onlyCheckName = true, acceptSlot = { it in 19..25 }) { event ->
            val id = getID(event.currentItem) ?: return@listenTop
            val type = if (event.isShiftClick)
                if (event.isRightClick) {
                    event.whoClicked.closeInventory()
                    "delete"
                } else "teleport"
            else "edit"
            event.whoClicked.execute("$commandName setup shops $type $id")
        }.add()

        //add shop
        Setup.shops.listenTop(plugin, onlyCheckName = true, acceptSlot = { it == 40 }) { event ->
            event.whoClicked.execute("$commandName add shop")
            event.whoClicked.closeInventory()
        }.add()

        //shops page management
        Setup.shops.listenTop(
            plugin,
            onlyCheckName = true,
            acceptSlot = { it == 38 || it == 42 }
        ) { event ->
            event.whoClicked.openShops(event.whoClicked.page + if (event.slot == 38) -1 else 1)
        }.add()

        //open shops edit gui by value
        "${SECONDARY}GunGame Setup Shops Edit".listenTop(
            plugin,
            acceptSlot = { it in 18..26 || it == 40 },
            acceptCurrentItem = { it != null && it.type != Material.STAINED_GLASS_PANE }
        ) { event ->
            val id = event.whoClicked.editID ?: return@listenTop

            val type = when (event.slot) {
                18 -> "yaw"
                20 -> "x"
                22 -> "y"
                24 -> "z"
                26 -> "pitch"
                40 -> "world"
                else -> return@listenTop
            }

            event.whoClicked.editType = type
            event.whoClicked.execute("$commandName setup shops edit $id $type")
        }.add()


        //shops edit type to value
        listen<AnvilClickEvent>(plugin) { event ->
            if (event.slot != AnvilGUI.AnvilSlot.OUTPUT) return@listen

            val player = event.anvilGUI.player
            val editType = player.editType ?: return@listen
            val editID = player.editID ?: return@listen
            player.execute("$commandName setup shops edit $editID $editType ${event.itemStack?.itemMeta?.displayName}")
        }

        Setup.maps.listenTop(plugin) {

        }.add()

    }

    private fun getID(itemStack: ItemStack): Int? {
        val rawName: String? = ChatColor.stripColor(itemStack.itemMeta?.displayName)
        return rawName?.replaceFirst("Number ", "")?.toIntOrNull()
    }


}