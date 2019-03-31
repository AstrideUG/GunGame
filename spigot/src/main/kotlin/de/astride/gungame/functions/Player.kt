package de.astride.gungame.functions

import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendScoreBoard
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Items
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.metadata.FixedMetadataValue

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 09:20.
 * Current Version: 1.0 (27.03.2019 - 31.03.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 29.03.2019 13:10.
 * Current Version: 1.0 (29.03.2019 - 31.03.2019)
 */
const val leaveSlot = 8

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 08:14.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
var Player.keepInventory
    get() = hasMetadata("KEEP_INVENTORY")
    set(value) {
        if (!value) removeMetadata("KEEP_INVENTORY", javaPlugin)
        else setMetadata("KEEP_INVENTORY", FixedMetadataValue(javaPlugin, value))
    }

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 09:22.
 * Current Version: 1.0 (27.03.2019 - 31.03.2019)
 */
fun Player.sendScoreBoard() = sendScoreBoard(
    "${PRIMARY}GunGame", setOf(
        " ",
        "${TEXT}Map$IMPORTANT:",
        "$IMPORTANT${gameMap.name}",
        "  ",
        "${TEXT}Teams$IMPORTANT:",
        "$IMPORTANT${if (isAllowTeams) "erlaubt" else "verboten"}",
        "   ",
        "${TEXT}Rang$IMPORTANT:",
        "$IMPORTANT${uniqueId.rank}",
        "    ",
        "${TEXT}Points$IMPORTANT:",
        "$IMPORTANT${uniqueId.points()} ",
        "     ",
        "${TEXT}Use $IMPORTANT/Stats",
        "${TEXT}for more"
    )
)


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 08:56.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
fun Player.playBuySound() = playSound(location, Sound.LEVEL_UP, 2f, 1f)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 29.03.2019 13:08.
 * Current Version: 1.0 (29.03.2019 - 31.03.2019)
 */
fun Inventory.setLeave() = setItem(leaveSlot, Items.LEAVE.itemStack)
