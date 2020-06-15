package de.astride.gungame.commands

import de.astride.gungame.functions.configService
import de.astride.gungame.functions.messages
import de.astride.gungame.functions.replace
import de.astride.gungame.setup.*
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.commands.Command
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.execute
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.location.ReadOnlyLocation
import net.darkdevelopers.darkbedrock.darkness.spigot.location.copy
import net.darkdevelopers.darkbedrock.darkness.spigot.location.lookable.Lookable
import net.darkdevelopers.darkbedrock.darkness.spigot.location.lookable.copy
import net.darkdevelopers.darkbedrock.darkness.spigot.location.toBukkitLocation
import net.darkdevelopers.darkbedrock.darkness.spigot.location.toLocation
import net.darkdevelopers.darkbedrock.darkness.spigot.location.vector.Vector3D
import net.darkdevelopers.darkbedrock.darkness.spigot.location.vector.copy
import net.darkdevelopers.darkbedrock.darkness.spigot.location.vector.toVector3D
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.region.Region
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.AnvilGUI
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.book.Book
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.book.ClickAction
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.book.openBook
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.isPlayer
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.map.DataGameMap
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.map.GameMap
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.map.setupWorldBorder
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.map.worldborder.WorldBorder
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
 * Current Version: 1.0 (04.04.2019 - 18.05.2019)
 */
class GunGame(javaPlugin: JavaPlugin) : Command(
    javaPlugin,
    commandName = config.name,
    permission = config.permission,
    usage = "save actions/kits/shops/maps [<Path>.json]" +
            "|load messages [<Path>.json]" +
//            "|set map <id> worldboarder damage buffer/amount <value> [<Path>.json]" +
//            "|set map <id> worldboarder warning time/distance <value> [<Path>.json]" +
//            "|set map <id> worldboarder size <value> [<Path>.json]" +
//            "|set map <id> worldboarder center [-o] [<Path>.json]" +
            "|add shop [-o] [<Path>.json]" +
            "|add map <name> [-o] [<Path>.json]" +
            "|setup help/all/shops/maps" +
            "|setup shops delete <id>" +
            "|setup shops teleport <id>" +
            "|setup shops movehere <id>" +
            "|setup shops edit <id> <world/x/y/z/yaw/pitch> <value>" +
            "|setup maps delete <id> [region/hologram]" +
            "|setup maps edit <id> name/world <value>" +
            "|setup maps edit <id> hologram/spawn" +//" <world/x/y/z> <value>" +
            "|setup maps edit <id> region pos1/pos2" +//" <world/x/y/z> <value>" +
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
        val shops = configService.shops

        @Suppress("IMPLICIT_CAST_TO_ANY")
        val configData = if (!args.last().endsWith(".json")) when (args[0].toLowerCase()) {
            "save" -> when (toLowerCase) {
                "actions" -> configService.actions.configData
                "kits" -> configService.kits.configData
                "shops" -> shops.configData
                "maps" -> maps.configData
                else -> failed = true
            }
            "load" -> when (toLowerCase) {
                "messages" -> messages.configData
                else -> failed = true
            }
            "set" -> if (toLowerCase == "map") maps.bukkitGsonConfig.configData else failed = true
            "add" -> when (toLowerCase) {
                "shop" -> shops.configData
                "map" -> maps.configData
                else -> failed = true
            }
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
                    "shops" -> shops.save(configData = configData)
                    "maps" -> maps.save(configData = configData)
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
                    "worldboarder" -> isSizeOrHigher(5, sender) {
                        when (args[4].toLowerCase()) {
                            "center" -> sender.isPlayer {
                                val location = it.location.round(args)
                                maps.setWorldBoarderCenterAndSave(id, location.toBukkitLocation(), configData)
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
                    else -> sendUseMessage(sender)
                }
            } else sendUseMessage(sender)
            "add" -> sender.isPlayer { player ->
                val location = player.location.round(args)
                when (toLowerCase) {
                    "shop" -> {
                        if (configData == shops.configData) shops.locations += location
                        shops.save(configData)
                    }
                    "map" -> when (args.size) {
                        2 -> {
                            AnvilGUI(javaPlugin, player).apply {
                                setSlot(
                                    AnvilGUI.AnvilSlot.INPUT_LEFT,
                                    ItemBuilder(Material.PAPER).setName("MapName").build()
                                )
                            }.open("GunGame Add Map")
                            player.anvilType = "add-map"
                        }
                        3 -> {
                            val gameMap: GameMap = DataGameMap(
                                args[2],
                                location,
                                null,
                                null,
                                null
                            )
                            maps.addAndSave(gameMap, configData)
                            if (configData == maps.configData) maps.maps += gameMap
                        }
                        else -> sendUseMessage(sender)
                    }
                    else -> sendUseMessage(sender)
                }
            }
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
                    .add("$RESET Team wünscht dir viel Spaß mit GunGame.\n\n").build()
                    .add("Die Einrichtung ist für dich so einfach wie möglich gehalten.\n\n\n").build()
                    .add("$GREEN${UNDERLINE}Jetzt los legen")
                    .clickEvent(ClickAction.RUN_COMMAND, "/$commandName setup all").build()
                    .build()
                player.openBook(book.build())
            }
            "all" -> player.openInventory(Setup.all)
            "maps" -> when {
                args.size == 1 -> player.openMaps(0)
                args.size >= 3 -> {
                    val maps = configService.maps.maps
                    val id = args[2].toIntOrNull()
                    if (id != null) {
                        val gameMap = maps[id]
                        player.editID = id
                        when (args[1].toLowerCase()) {
                            "delete" -> if (args.size == 4) when (args[3].toLowerCase()) {
                                "region" -> gameMap.editTo(maps, id, region = null)
                                "hologram" -> gameMap.editTo(maps, id, hologram = null)
                                else -> sendUseMessage(sender)
                            } else configService.maps.maps -= gameMap
                            "edit" -> when (args.size) {
                                3 -> player.openInventory(Setup.generateMapsEdit(gameMap))
                                4 -> when (args[3].toLowerCase()) {
                                    "name", "world" -> {
                                        AnvilGUI(javaPlugin, player).apply {
                                            setSlot(
                                                AnvilGUI.AnvilSlot.INPUT_LEFT,
                                                ItemBuilder(Material.PAPER).setName("Value").build()
                                            )
                                        }.open("GunGame Setup Maps Edit ${args[3]}")
                                        player.editType = args[3]
                                        player.anvilType = "setup-maps-edit-4"
                                    }
                                    "hologram" -> gameMap.editTo(
                                        maps, id, hologram = player.location.toLocation().round()
                                    )
                                    "spawn" -> gameMap.editTo(maps, id, spawn = player.location.toLocation().round())
                                    else -> sendUseMessage(sender)
                                }
                                5 -> {
                                    val arg4 = args[4]
                                    when (args[3].toLowerCase()) {
                                        "name" -> gameMap.editTo(maps, id, name = arg4)
                                        "world" -> {
                                            val region = gameMap.region
                                            gameMap.editTo(
                                                maps,
                                                id,
                                                spawn = gameMap.spawn.copy(world = arg4),
                                                hologram = gameMap.hologram?.copy(world = arg4),
                                                region = if (region == null) null else Region.of(
                                                    arg4,
                                                    region.min,
                                                    region.max
                                                )
                                            )
                                            gameMap.setupWorldBorder()
                                        }
                                        "region" -> {
                                            val region =
                                                gameMap.region ?: Region.of("generated", 0.toVector3D(), 0.toVector3D())

                                            val worldName = player.world.name
                                            val playerVector = player.location.toLocation().vector.round()
                                            if (gameMap.region == null) gameMap.editTo(
                                                maps, id, region = Region.of(worldName, playerVector, playerVector)
                                            ) else when (arg4.toLowerCase()) {
                                                "pos1" -> gameMap.editTo(
                                                    maps, id, region = Region.of(worldName, playerVector, region.max)
                                                )
                                                "pos2" -> gameMap.editTo(
                                                    maps, id, region = Region.of(worldName, region.min, playerVector)
                                                )
                                                else -> sendUseMessage(sender)
                                            }
                                        }
                                        else -> sendUseMessage(sender)
                                    }
                                }
                                else -> sendUseMessage(sender)
                            }
                            else -> sendUseMessage(sender)
                        }
                    } else sendUseMessage(sender) //TODO: Add message
                }
                else -> sendUseMessage(sender)
            }
            "shops" -> {
                val shopLocations = configService.shops.locations
                when {
                    args.size == 1 -> player.openShops(0)
                    args.size >= 3 -> {
                        val id = args[2].toIntOrNull()
                        if (id != null) {
                            val location = shopLocations[id]
                            player.editID = id
                            when (args[1].toLowerCase()) {
                                "delete" -> {
                                    configService.shops.locations -= location
                                    Bukkit.getConsoleSender().execute("$commandName save shops")
                                }
                                "teleport" -> player.teleport(location.toBukkitLocation())
                                "movehere" -> shopLocations.editTo(id, player.location.toLocation().round())
                                "edit" -> when (args.size) {
                                    3 -> player.openInventory(Setup.generateLocationEdit(location))
                                    4 -> {
                                        AnvilGUI(javaPlugin, player).apply {
                                            setSlot(
                                                AnvilGUI.AnvilSlot.INPUT_LEFT,
                                                ItemBuilder(Material.PAPER).setName("Value").build()
                                            )
                                        }.open("GunGame Setup Shops Edit ${args[3]}")
                                        player.editType = args[3]
                                        player.anvilType = "setup-shops-edit"
                                    }
                                    5 -> location.edit(sender, args[3], args[4], shopLocations, id)
                                    else -> sendUseMessage(sender)
                                }
                                else -> sendUseMessage(sender)
                            }
                        } else sendUseMessage(sender) //TODO: Add message
                    }
                    else -> sendUseMessage(sender)
                }
            }
            "reload" -> {
                val pluginManager = javaPlugin.server.pluginManager
                Bukkit.getOnlinePlayers().forEach { it.kickPlayer("GunGame Plugin reload") }
                pluginManager.disablePlugin(javaPlugin)
                pluginManager.enablePlugin(javaPlugin)
            }
        }
    }

    private fun ReadOnlyLocation.edit(
        sender: CommandSender,
        type: String,
        value: String,
        destination: MutableList<ReadOnlyLocation>,
        id: Int
    ) {
        try {
            when (type.toLowerCase()) {
                "world" -> editTo(destination, id, world = value)
                "x" -> editTo(destination, id, vector = vector.copy(x = value.toDouble()))
                "y" -> editTo(destination, id, vector = vector.copy(y = value.toDouble()))
                "z" -> editTo(destination, id, vector = vector.copy(z = value.toDouble()))
                "yaw" -> editTo(destination, id, lookable = lookable?.copy(yaw = value.toFloat()))
                "pitch" -> editTo(destination, id, lookable = lookable?.copy(pitch = value.toFloat()))
            }
            /*} catch (ex: IndexOutOfBoundsException) {
                sendUseMessage(sender) //TODO: Add message*/
        } catch (ex: NumberFormatException) {
            sendUseMessage(sender)//TODO: Add message
        }
    }

    private fun ReadOnlyLocation.editTo(
        destination: MutableList<ReadOnlyLocation>,
        id: Int,
        world: String = this.world,
        vector: Vector3D = this.vector,
        lookable: Lookable? = this.lookable
    ): Unit = destination.editTo(id, copy(world, vector, lookable))

    private fun GameMap.editTo(
        destination: MutableList<GameMap>,
        id: Int,
        name: String = this.name,
        spawn: ReadOnlyLocation = this.spawn,
        hologram: ReadOnlyLocation? = this.hologram,
        region: Region? = this.region,
        worldBorder: WorldBorder? = this.worldBorder
    ): Unit = destination.editTo(id, DataGameMap(name, spawn, hologram, region, worldBorder))

    //@TODO replace with the Darkness function in the the new update
    private fun <E> MutableList<E>.editTo(id: Int, new: E) {
        removeAt(id)
        add(id, new)
    }

    private inline fun Array<String>.isSizeOrHigher(size: Int, sender: CommandSender, block: () -> Unit): Unit =
        if (this.size >= size) block() else sendUseMessage(sender)

    private fun Location.round(args: Array<String>): ReadOnlyLocation = toLocation().round(args)

    private fun ReadOnlyLocation.round(args: Array<String>): ReadOnlyLocation = if (args.isNotEmpty() &&
        (args.last().equals("-o", true) ||
                (args.last().endsWith(".json") &&
                        args.size >= 2 &&
                        args.dropLast(1).last().equals("-o", true)))
    ) this else round()

    //TODO darkness
    private fun ReadOnlyLocation.round(): ReadOnlyLocation = copy(vector = vector.round())

    //TODO darkness
    private fun Vector3D.round(): Vector3D =
        copy(x = x.toInt() + if (x < 0) -0.5 else 0.5, z = z.toInt() + if (z < 0) -0.5 else 0.5)

    private fun generatePath(input: String): ConfigData {
        val path = input.split('/')
        val directory = "${javaPlugin.dataFolder}${File.separator}${path.dropLast(1).joinToString(File.separator)}"
        return ConfigData(directory, path.last())
    }

    companion object {
        private val config get() = configService.config.commands.gungame
    }

}
