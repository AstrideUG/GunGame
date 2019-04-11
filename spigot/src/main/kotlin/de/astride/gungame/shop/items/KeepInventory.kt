package de.astride.gungame.shop.items

import de.astride.gungame.event.GunGamePlayerDowngradeLevelEvent
import de.astride.gungame.event.GunGamePlayerUpgradeLevelEvent
import de.astride.gungame.functions.actions
import de.astride.gungame.functions.javaPlugin
import de.astride.gungame.functions.messages
import de.astride.gungame.functions.playBuySound
import de.astride.gungame.kits.updateLevel
import de.astride.gungame.shop.ShopItemListener
import de.astride.gungame.stats.Action
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 07:51.
 * Current Version: 1.0 (27.03.2019 - 11.04.2019)
 */
class KeepInventory(javaPlugin: JavaPlugin) : ShopItemListener(
    javaPlugin,
    ItemBuilder(config.material, damage = config.damage)
        .setName(config.name)
        .setLore(config.lore)
        .build(),
    config.delay,
    config.price
) {

    override fun Player.buy(): Boolean = if (!keepInventory) {
        keepInventory = true
        playBuySound()
        messages.shop.keepInventory.successfully.sendTo(this)
        true
    } else {
        messages.shop.keepInventory.failed.sendTo(this)
        false
    }

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 29.03.2019 19:21.
     * Current Version: 1.0 (29.03.2019 - 29.03.2019)
     */
    @EventHandler
    fun onGunGamePlayerDowngradeLevelEvent(event: GunGamePlayerDowngradeLevelEvent) {
        if (event.player.keepInventory) event.cancel()
    }

    @EventHandler
    fun onGunGamePlayerUpgradeLevelEvent(event: GunGamePlayerUpgradeLevelEvent) {
        if (event.player.keepInventory) event.cancel()
    }

    @EventHandler
    fun onPlayerRespawnEvent(event: PlayerRespawnEvent) {

        event.player.apply {
            if (!keepInventory) return
            keepInventory = false
            updateLevel()
            uniqueId.actions += Action(
                "used-${this@KeepInventory.javaClass.simpleName}",
                mapOf(/*"player" to this.toDataPlayer()*/)
            )
        }

    }

    companion object {
        private val config get() = shopItems.keepInventory
    }

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 08:14.
 * Current Version: 1.0 (27.03.2019 - 01.04.2019)
 */
var Player.keepInventory
    get() = hasMetadata("keep-inventory")
    set(value) {
        if (!value) removeMetadata("keep-inventory", javaPlugin)
        else setMetadata("keep-inventory", FixedMetadataValue(javaPlugin, value))
    }