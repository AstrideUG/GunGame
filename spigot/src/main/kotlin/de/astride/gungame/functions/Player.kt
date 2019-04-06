package de.astride.gungame.functions

import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendScoreBoard
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Items
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import kotlin.concurrent.thread

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
        "$IMPORTANT${allowTeams.asString}",
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

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 09:09.
 * Current Version: 1.0 (27.03.2019 - 01.04.2019)
 */
fun Player.heal() {
    if (health <= 0.0 || health.toInt() == maxHealth.toInt()) return
    thread {
        for (i in health.toInt()..maxHealth.toInt()) {
            try {
                Thread.sleep(50)
            } catch (ex: InterruptedException) {
                ex.printStackTrace()
            }
            if (health + 1 >= maxHealth) return@thread
            health++
        }
    }

}
