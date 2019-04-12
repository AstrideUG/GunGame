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
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 17.02.2018 16:33. (KitManager)
 * Current Version: 1.0 (17.02.2018 - 12.04.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 12.04.2019 09:51.
 * Current Version: 1.0 (12.04.2019 - 12.04.2019)
 */
lateinit var kits: List<List<ItemStack?>>

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
 * Current Version: 1.0 (27.03.2019 - 12.04.2019)
 */
fun Player.upgrade() {

    val isNotCancelled = !GunGamePlayerUpgradeLevelEvent(this).call().isCancelled
    if (isNotCancelled && gunGameLevel <= kits.size) {
        gunGameLevel++
        playBuySound()
    }

    setKit()
}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 05:34.
 * Current Version: 1.0 (27.03.2019 - 12.04.2019)
 */
fun Player.setKit() {

    inventory.apply {
        val kit = kits[gunGameLevel - 1]//DefaultKits.valueOf("KIT$gunGameLevel")
        helmet = kit[0]
        chestplate = kit[1]
        leggings = kit[2]
        boots = kit[3]
        setItem(0, kit[4])
    }

    updateLevel()
    updateInventory()
}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 08:31.
 * Current Version: 1.0 (27.03.2019 - 12.04.2019)
 */
fun Player.updateLevel() = Bukkit.getScheduler().scheduleSyncDelayedTask(javaPlugin, { level = gunGameLevel }, 2)



