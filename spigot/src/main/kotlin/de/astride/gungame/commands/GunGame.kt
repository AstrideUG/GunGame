package de.astride.gungame.commands

import de.astride.gungame.functions.configService
import de.astride.gungame.functions.edit
import de.astride.gungame.functions.messages
import de.astride.gungame.functions.replace
import de.astride.gungame.setup.Setup
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.inverntory.InventoryBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.AnvilGUI
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.book.Book
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.book.ClickAction
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.book.openBook
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.isPlayer
import org.bukkit.Bukkit
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.UNDERLINE
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 18:33.
 * Current Version: 1.0 (04.04.2019 - 07.05.2019)
 */
class GunGame(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    commandName = config.name,
    permission = config.permission,
    usage = "save actions/kits [<Path>.json]" +
            "|load messages [<Path>.json]" +
            "|set map <id> name <value> [<Path>.json]" +
            "|set map <id> world <value> [<Path>.json]" +
            "|set map <id> worldboarder damage buffer/amount <value> [<Path>.json]" +
            "|set map <id> worldboarder warning time/distance <value> [<Path>.json]" +
            "|set map <id> worldboarder size <value> [<Path>.json]" +
            "|set map <id> worldboarder center [-o] [<Path>.json]" +
            "|set map <id> spawn/hologram [-o] [<Path>.json]" +
            "|set map <id> region pos1/pos2 [-o] [<Path>.json]" +
            "|add shop [-o] [<Path>.json]" +
            "|setup help" +
            "|setup all" +
            "|setup maps" +
            "|setup shops" +
            "|setup shops edit <id> <world/x/y/z/yaw/pitch> <value>" +
            "|setup reload",
    minLength = 2,
    maxLength = 8,
    aliases = *config.aliases
) {

    override fun perform(sender: CommandSender, args: Array<String>) {

        if (args[0].toLowerCase() == "setup") {
            setup(sender, args.drop(1))
            return
        }

        var failed = false

        val toLowerCase = args[1].toLowerCase()
        val maps = configService.maps
        @Suppress("IMPLICIT_CAST_TO_ANY")
        val configData = if (!args.last().endsWith(".json")) when (args[0].toLowerCase()) {
            "save" -> when (toLowerCase) {
                "actions" -> configService.actions.configData
                "kits" -> configService.kits.configData
                else -> failed = true
            }
            "load" -> when (toLowerCase) {
                "messages" -> messages.configData
                else -> failed = true
            }
            "set" -> if (toLowerCase == "map") maps.bukkitGsonConfig.configData else failed = true
            "add" -> if (toLowerCase == "shop") configService.shops.configData else failed = true
            else -> failed = true
        } else generatePath(args[args.lastIndex])

        if (failed) {
            sendUseMessage(sender)
            return
        }

        configData as ConfigData

        val path = configData.file.toPath()
        val absolutePath = path.toAbsolutePath()
        val transform: (String?) -> String? = {
            var result = it.replace("path", path).replace("absolute-path", absolutePath)
            for (i in 0 until args.size) result = result.replace("arg$i", args[i])
            result
        }

        fun isSizeOrHigher(size: Int, sender: CommandSender, block: () -> Unit): Unit =
            args.isSizeOrHigher(size, sender, block)

        when (args[0].toLowerCase()) {
            "save" -> {
                when (toLowerCase) {
                    "actions" -> configService.actions.save(configData = configData)
                    "kits" -> configService.kits.save(configData = configData)
                    else -> {
                        sendUseMessage(sender)
                        return
                    }
                }
                messages.commands.gungame.successfullySaved.map(transform).sendTo(sender)
            }
            "load" -> if (toLowerCase == "messages") {
                configService.Messages(configData)
                messages.commands.gungame.successfullyLoaded.map(transform).sendTo(sender)
            } else sendUseMessage(sender)
            "set" -> if (toLowerCase == "map" && args.size >= 4) {
                val id = args[2].toIntOrNull()
                if (id == null) {
                    sendUseMessage(sender)
                    return
                }
                when (args[3].toLowerCase()) {
                    "name" -> isSizeOrHigher(5, sender) { maps.setNameAndSave(id, args[4], configData) }
                    "world" -> isSizeOrHigher(5, sender) { maps.setWorldAndSave(id, args[4], configData) }
                    "worldboarder" -> isSizeOrHigher(5, sender) {
                        when (args[4].toLowerCase()) {
                            "center" -> sender.isPlayer {
                                val location = roundLocation(args, it.location)
                                maps.setWorldBoarderCenterAndSave(id, location, configData)
                            }
                            "size" -> isSizeOrHigher(6, sender) {
                                val value = args[5].toDoubleOrNull()
                                if (value != null)
                                    maps.setWorldBoarderSizeAndSave(id, value, configData)
                                else sendUseMessage(sender)
                            }
                            "warning" -> isSizeOrHigher(7, sender) {
                                when (args[5].toLowerCase()) {
                                    "time" -> {
                                        val value = args[6].toIntOrNull()
                                        if (value != null)
                                            maps.setWorldBoarderWarningTimeAndSave(id, value, configData)
                                        else sendUseMessage(sender)
                                    }
                                    "distance" -> {
                                        val value = args[6].toIntOrNull()
                                        if (value != null)
                                            maps.setWorldBoarderWarningDistanceAndSave(id, value, configData)
                                        else sendUseMessage(sender)
                                    }
                                    else -> sendUseMessage(sender)
                                }
                            }
                            "damage" -> isSizeOrHigher(7, sender) {
                                when (args[5].toLowerCase()) {
                                    "buffer" -> {
                                        val value = args[6].toDoubleOrNull()
                                        if (value != null)
                                            maps.setWorldBoarderDamageBufferAndSave(id, value, configData)
                                        else sendUseMessage(sender)
                                    }
                                    "amount" -> {
                                        val value = args[6].toDoubleOrNull()
                                        if (value != null)
                                            maps.setWorldBoarderDamageAmountAndSave(id, value, configData)
                                        else sendUseMessage(sender)
                                    }
                                    else -> sendUseMessage(sender)
                                }
                            }
                            else -> sendUseMessage(sender)
                        }
                    }
                    "spawn" -> sender.isPlayer {
                        val location = roundLocation(args, it.location)
                        maps.setSpawnAndSave(id, location, configData)
                    }
                    "hologram" -> sender.isPlayer {
                        val location = roundLocation(args, it.location)
                        maps.setHologramAndSave(id, location, configData)
                    }
                    "region" -> sender.isPlayer {
                        val location = roundLocation(args, it.location)
                        if (args.size >= 5) when {
                            args[4].equals("pos1", true) ->
                                maps.setRegionPos1AndSave(id, location, configData)
                            args[4].equals("pos2", true) ->
                                maps.setRegionPos2AndSave(id, location, configData)
                            else -> sendUseMessage(sender)
                        } else sendUseMessage(sender)
                    }
                    else -> sendUseMessage(sender)
                }
            } else sendUseMessage(sender)
            "add" -> if (toLowerCase == "shop") sender.isPlayer {
                val location = roundLocation(args, it.location)
                configService.shops.addAndSave(location, configData)
                if (configData == configService.shops.configData) configService.shops.locations += location
            } else sendUseMessage(sender)
            else -> {
                sendUseMessage(sender)
                return
            }
        }

    }

    private fun setup(sender: CommandSender, args: List<String>): Unit = sender.isPlayer { player ->
        when (args[0].toLowerCase()) {
            "help" -> {
                val book = Book("", "")
                book.addPage()
                    .add("\n\nDas ").build()
                    .add("$PRIMARY${EXTRA}CraftPlugin$IMPORTANT$EXTRA.$PRIMARY${EXTRA}net")
                    .clickEvent(ClickAction.OPEN_URL, "https://portal.craftplugin.net").build()
                    .add(" Team wünscht dir viel Spaß mit GunGame.\n\n").build()
                    .add("Die Einrichtung ist für dich so einfach wie möglich gehalten.\n\n\n").build()
                    .add("$GREEN${UNDERLINE}Jetzt los legen")
                    .clickEvent(ClickAction.RUN_COMMAND, "/$commandName setup all").build()
                    .build()
                player.openBook(book.build())
            }
            "all" -> player.openInventory(Setup.all)
            "maps" -> player.openInventory(Setup.maps) //TODO: generate maps display-items
            "shops" -> when {
                args.size == 1 -> {

                    val shops = Setup.shops
                    val inventory = InventoryBuilder(shops.size, shops.title).build().apply {
                        contents = shops.contents
                    }

                    val itemsPerPage = 7
                    val page = 0
                    val add = page * itemsPerPage

                    val locations = configService.shops.locations
                    if (locations.isNotEmpty()) for (i in 0 until Math.min(locations.size, itemsPerPage)) {
                        val location = locations[i + add]
                        inventory.setItem(i + 19, Setup.generateShopDisplayItem(i + add, location))
                    }

                    player.openInventory(inventory)
                }
                args.size >= 3 -> if (args[1].toLowerCase() == "edit") {
                    val id = args[2].toIntOrNull()
                    if (id != null) {
                        val location = configService.shops.locations[id]
                        when (args.size) {
                            3 -> player.openInventory(Setup.generateShopsEdit(location))
                            4 -> {
                                AnvilGUI(javaPlugin, player).apply {
                                    setSlot(
                                        AnvilGUI.AnvilSlot.INPUT_LEFT,
                                        ItemBuilder(Material.PAPER).setName("Value").build()
                                    )
                                }.open("GunGame Setup Shops Edit ${args[3]}")
                            }
                            5 -> try {
                                val value = args[4]
                                when (args[3].toLowerCase()) {
                                    "world" -> location.world =
                                        Bukkit.getWorld(value) ?: return@isPlayer //TODO: Add message
                                    "x" -> location.x = value.toDouble()
                                    "y" -> location.y = value.toDouble()
                                    "Z" -> location.z = value.toDouble()
                                    "yaw" -> location.yaw = value.toFloat()
                                    "pitch" -> location.pitch = value.toFloat()
                                }
                            } catch (ex: IndexOutOfBoundsException) {
                                sendUseMessage(sender) //TODO: Add message
                            } catch (ex: NumberFormatException) {
                                sendUseMessage(sender)//TODO: Add message
                            }
                            else -> sendUseMessage(sender)
                        }
                    } else sendUseMessage(sender) //TODO: Add message
                } else sendUseMessage(sender)
                else -> sendUseMessage(sender)
            }
            "reload" -> {
                val pluginManager = javaPlugin.server.pluginManager
                pluginManager.disablePlugin(javaPlugin)
                pluginManager.enablePlugin(javaPlugin)
            }
        }
    }

    private inline fun Array<String>.isSizeOrHigher(size: Int, sender: CommandSender, block: () -> Unit): Unit =
        if (this.size >= size) block() else sendUseMessage(sender)

    private fun roundLocation(args: Array<String>, location: Location): Location =
        if (args.isNotEmpty() &&
            (args.last().equals("-o", true) ||
                    (args.last().endsWith(".json") &&
                            args.size >= 2 &&
                            args.dropLast(1).last().equals("-o", true)))
        ) location
        else location.edit(x = location.blockX + 0.5, z = location.blockZ + 0.5)

    private fun generatePath(input: String): ConfigData {
        val path = input.split('/')
        val directory = "${javaPlugin.dataFolder}${File.separator}${path.dropLast(1).joinToString(File.separator)}"
        return ConfigData(directory, path.last())
    }

    companion object {
        private val config get() = configService.config.commands.gungame
    }

}
