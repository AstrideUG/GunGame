package de.astride.gungame.functions

import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendScoreBoard
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.IMPORTANT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.TEXT
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 09:20.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 09:20.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
var Player.killStreak
    get() = getMetadata(MetadataKeys.KILL_STREAK.toString()).firstOrNull()?.asInt() ?: 0
    set(value) = setMetadata(MetadataKeys.KILL_STREAK.toString(), FixedMetadataValue(javaPlugin, value))

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 08:14.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
var Player.keepInventory
    get() = hasMetadata(MetadataKeys.KEEP_INVENTORY.toString())
    set(value) {
        if (!value) removeMetadata(MetadataKeys.KEEP_INVENTORY.toString(), javaPlugin)
        else setMetadata(MetadataKeys.KEEP_INVENTORY.toString(), FixedMetadataValue(javaPlugin, value))
    }

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 09:22.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
fun Player.sendScoreBoard() = sendScoreBoard(
    "SERVERNAME", setOf(
        " ",
        "${TEXT}Map$IMPORTANT:",
        "$IMPORTANT${gameMap.name}",
        "  ",
        "${TEXT}Teams$IMPORTANT:",
        "$IMPORTANT${if (isAllowTeams) "erlaubt" else "verboten"}",
        "   ",
        "${TEXT}Rang$IMPORTANT:",
        "$IMPORTANT${"In work"/*rank.toInt()*/}",
        "    ",
        "${TEXT}Punkte$IMPORTANT:",
        "$IMPORTANT${"In work"/*punkte.toInt()*/}",
        "     ",
        "${TEXT}Weitere Stats",
        "${TEXT}mit $IMPORTANT/Stats"
    )
)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 08:56.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
fun Player.playBuySound() = playSound(location, Sound.LEVEL_UP, 2f, 1f)

enum class MetadataKeys {

    KEEP_INVENTORY,
    KILL_STREAK

}