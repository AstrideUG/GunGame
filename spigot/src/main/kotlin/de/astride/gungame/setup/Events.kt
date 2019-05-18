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
import org.bukkit.entity.HumanEntity
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 07.05.2019 13:19.
 * Current Version: 1.0 (07.05.2019 - 08.05.2019)
 */
object Events : EventsTemplate() {

    private val commandName: String = configService.config.commands.gungame.name

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
        setOf(
            Setup.all.name,
            Setup.shopsInventoryName,
            Setup.mapsInventoryName,
            Setup.shopsInventoryEditName,
            Setup.mapsInventoryEditName
        ).listenTop(plugin, cancel = true).add()

        //back Item impl
        listenInventories(plugin, acceptCurrentItem = { it == Setup.backItem }) { event ->
            val whoClicked = event.whoClicked ?: return@listenInventories
            when (whoClicked.openInventory.topInventory.name) {
                Setup.shopsInventoryName -> whoClicked.execute("$commandName setup all")
                Setup.mapsInventoryName -> whoClicked.execute("$commandName setup all")
                Setup.shopsInventoryEditName -> whoClicked.execute("$commandName setup shops")
                Setup.mapsInventoryEditName -> whoClicked.execute("$commandName setup maps")
            }
        }.add()

        openSubGUIs(plugin)
        pageManagement(plugin)
        Setup.maps.addEntry(plugin, "add map")
        Setup.shops.addEntry(plugin, "add shop")

        //open shops edit gui
        Setup.shops.listenTop(plugin, onlyCheckName = true, acceptSlot = { it in 19..25 }) { event ->
            val id = getID(event.currentItem) ?: return@listenTop
            event.whoClicked.closeInventory()
            val type = when {
                event.isShiftClick && event.isLeftClick -> "teleport"
                event.isShiftClick && event.isRightClick -> "delete"
                event.isLeftClick -> "edit"
                event.isRightClick -> "movehere"
                else -> return@listenTop
            }
            event.whoClicked.execute("$commandName setup shops $type $id")
        }.add()

        //open maps edit gui
        Setup.maps.listenTop(plugin, onlyCheckName = true, acceptSlot = { it in 19..25 }) { event ->
            val id = getID(event.currentItem) ?: return@listenTop
            val type = if (event.isShiftClick && event.isRightClick) {
                event.whoClicked.closeInventory()
                "delete"
            } else "edit"
            event.whoClicked.execute("$commandName setup maps $type $id")
        }.add()

        //open shops edit gui by value
        Setup.shopsInventoryEditName.listenTop(
            plugin,
            acceptSlot = { it in 18..26 || it == 40 },
            acceptCurrentItem = { it != null && it.type != Material.STAINED_GLASS_PANE }
        ) { event ->
            val whoClicked = event.whoClicked ?: return@listenTop
            val id = whoClicked.editID ?: return@listenTop
            val type = event.slot.toType() ?: return@listenTop

            whoClicked.editType = type
            whoClicked.execute("$commandName setup shops edit $id $type")
        }.add()

        //open "maps edit name" gui
        Setup.mapsInventoryEditName.listenTop(
            plugin,
            acceptSlot = { it == 21 }
        ) { event ->
            val id = event.whoClicked.editID ?: return@listenTop
            event.whoClicked.execute("$commandName setup maps edit $id name")
        }.add()

        //maps edit hologram/spawn ##<world/x/y/z> <value>
        Setup.mapsInventoryEditName.listenTop(
            plugin,
            acceptSlot = { it in 19 until 26 step 2 }
        ) { event ->
            val player: HumanEntity = event.whoClicked ?: return@listenTop
            val id = player.editID ?: return@listenTop
            player.closeInventory()
            val type = when (event.slot) {
                19 -> if (event.isShiftClick && event.isRightClick) {
                    player.execute("$commandName setup maps delete $id region")
                    return@listenTop
                } else "region ${if (event.isLeftClick) "pos1" else "pos2"}"
                23 -> "spawn"
                25 -> if (event.isShiftClick && event.isRightClick) {
                    player.execute("$commandName setup maps delete $id hologram")
                    return@listenTop
                } else "hologram"
                else -> return@listenTop
            }
            player.execute("$commandName setup maps edit $id $type")
        }.add()

        //shops edit type to value
        listen<AnvilClickEvent>(plugin) { event ->
            val player = event.anvilGUI.player
            val editType = player.editType ?: return@listen
            val editID = player.editID ?: return@listen

            if (event.slot != AnvilGUI.AnvilSlot.OUTPUT) return@listen
            if (player.anvilType != "setup-shops-edit") return@listen

            player.execute("$commandName setup shops edit $editID $editType ${event.itemStack?.itemMeta?.displayName}")
        }

        //add map
        listen<AnvilClickEvent>(plugin) { event ->
            val player = event.anvilGUI.player

            if (event.slot != AnvilGUI.AnvilSlot.OUTPUT) return@listen
            if (player.anvilType != "add-map") return@listen

            player.execute("$commandName add map ${event.itemStack?.itemMeta?.displayName}")
            player.closeInventory()
        }

        //anvil gui for "setup maps edit <id> name/world <value>"
        listen<AnvilClickEvent>(plugin) { event ->
            val player = event.anvilGUI.player
            val id = player.editID ?: return@listen

            if (event.slot != AnvilGUI.AnvilSlot.OUTPUT) return@listen
            if (player.anvilType != "setup-maps-edit-4") return@listen

            player.execute("$commandName setup maps edit $id ${player.editType} ${event.itemStack?.itemMeta?.displayName}")
            player.closeInventory()
        }

    }

    private fun pageManagement(plugin: Plugin) {
        //shops page management
        Setup.shops.listenTop(
            plugin,
            onlyCheckName = true,
            acceptSlot = { it == 38 || it == 42 }
        ) { event ->
            event.whoClicked.openShops(event.whoClicked.page + if (event.slot == 38) -1 else 1)
        }.add()

        //maps page management
        Setup.maps.listenTop(
            plugin,
            onlyCheckName = true,
            acceptSlot = { it == 38 || it == 42 }
        ) { event ->
            event.whoClicked.openMaps(event.whoClicked.page + if (event.slot == 38) -1 else 1)
        }.add()
    }

    private fun Inventory.addEntry(plugin: Plugin, command: String): Unit =
        listenTop(plugin, onlyCheckName = true, acceptSlot = { it == 40 }) { event ->
            event.whoClicked.closeInventory()
            event.whoClicked.execute("$commandName $command")
        }.add()

    private fun openSubGUIs(plugin: Plugin) {
        //open maps gui
        Setup.all.listenTop(plugin, acceptSlot = { it == 1 }) { event ->
            event.whoClicked.execute("$commandName setup maps")
        }.add()

        //open shops gui
        Setup.all.listenTop(plugin, acceptSlot = { it == 3 }) { event ->
            event.whoClicked.execute("$commandName setup shops")
        }.add()
    }


    private fun getID(itemStack: ItemStack): Int? {
        val rawName: String? = ChatColor.stripColor(itemStack.itemMeta?.displayName)
        return rawName?.replaceFirst("Number ", "")?.toIntOrNull()
    }

    private fun Int.toType(): String? = when (this) {
        18 -> "yaw"
        20 -> "x"
        22 -> "y"
        24 -> "z"
        26 -> "pitch"
        40 -> "world"
        else -> null
    }


}