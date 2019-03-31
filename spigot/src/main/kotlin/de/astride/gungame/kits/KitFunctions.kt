/*
 * © Copyright - Lars Artmann | LartyHD 2018.
 */
package de.astride.gungame.kits

import de.astride.gungame.event.GunGamePlayerDowngradeLevelEvent
import de.astride.gungame.event.GunGamePlayerUpgradeLevelEvent
import de.astride.gungame.functions.javaPlugin
import de.astride.gungame.functions.playBuySound
import net.darkdevelopers.darkbedrock.darkness.universal.functions.call
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue
import kotlin.concurrent.thread

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 17.02.2018 16:33. (KitManager)
 * Current Version: 1.0 (17.02.2018 - 30.03.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 04:43.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
private const val metadataKey = "gungame-level"

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 05:34.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
var Player.gunGameLevel: Int
    get() = getMetadata(metadataKey).firstOrNull()?.asInt() ?: 1
    set(value) = setMetadata(metadataKey, FixedMetadataValue(javaPlugin, value))

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 05:34.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
fun Player.downgrade() {

    if (!GunGamePlayerDowngradeLevelEvent(this).call().isCancelled) {
        val level = gunGameLevel / 2
        gunGameLevel = if (level > 0) level else 1
    }

    setKit()
}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 05:34.
 * Current Version: 1.0 (27.03.2019 - 29.03.2019)
 */
fun Player.upgrade() {

    val isNotCancelled = !GunGamePlayerUpgradeLevelEvent(this).call().isCancelled
    if (isNotCancelled && gunGameLevel < Kits.values().size) {
        gunGameLevel++
        playBuySound()
    }

    setKit()
}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 05:34.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
fun Player.setKit() {

    inventory.apply {
        val kit = Kits.valueOf("KIT$gunGameLevel")
        helmet = kit.helmet
        chestplate = kit.chestplate
        leggings = kit.leggins
        boots = kit.boots
        setItem(0, kit.item)
    }

    updateLevel()
    updateInventory()
}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 08:31.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
fun Player.updateLevel() = Bukkit.getScheduler().scheduleSyncDelayedTask(javaPlugin, { level = gunGameLevel }, 3)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 05:58.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
var Player.lastHealerUse
    get() = getMetadata("lastHealerUse").firstOrNull()?.asLong() ?: 0
    set(value) = setMetadata("lastHealerUse", FixedMetadataValue(javaPlugin, value))

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 09:09.
 * Current Version: 1.0 (27.03.2019 - 30.03.2019)
 */
fun Player.heal() {
    val delay = 10000 //TODO add config
    if (health <= 0.0 || lastHealerUse + delay > System.currentTimeMillis()) return
    lastHealerUse = System.currentTimeMillis()
    thread {
        for (i in 1..maxHealth.toInt()) {
            try {
                Thread.sleep(50)
            } catch (ex: InterruptedException) {
                ex.printStackTrace()
            }
            if (health >= maxHealth) return@thread
            health++
        }
    }

}


