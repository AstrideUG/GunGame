package de.astride.gungame.shop.items

import de.astride.gungame.event.GunGamePlayerDowngradeLevelEvent
import de.astride.gungame.event.GunGamePlayerUpgradeLevelEvent
import de.astride.gungame.functions.actions
import de.astride.gungame.functions.keepInventory
import de.astride.gungame.functions.playBuySound
import de.astride.gungame.kits.updateLevel
import de.astride.gungame.shop.ShopItemListener
import de.astride.gungame.stats.Action
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.SECONDARY
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.TEXT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 07:51.
 * Current Version: 1.0 (27.03.2019 - 31.03.2019)
 */
class KeepInventory(javaPlugin: JavaPlugin) : ShopItemListener(
    javaPlugin,
    ItemBuilder(Material.PAPER)
        .setName("${SECONDARY}KeepInventory")
        .setLore("${TEXT}Behalte nach deinem Tot deine Items")
        .build(),
    300,
    500
) {

    override fun Player.buy(): Boolean {

        sendMessage("${Messages.PREFIX}${TEXT}Du hast ${Colors.IMPORTANT}KeepInventory $TEXT${if (keepInventory) "schon " else ""}aktiviert")

        return if (!keepInventory) {
            keepInventory = true
            playBuySound()
            true
        } else false

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
            uniqueId.actions += Action("used-${this@KeepInventory.javaClass.simpleName}", mapOf("player" to this))
        }

    }

}