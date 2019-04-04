package de.astride.gungame.shop.items

import de.astride.data.toDataPlayer
import de.astride.gungame.functions.*
import de.astride.gungame.shop.ShopItemListener
import de.astride.gungame.stats.Action
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.IMPORTANT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.TEXT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.removeItemInHand
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action.RIGHT_CLICK_AIR
import org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 07:51.
 * Current Version: 1.0 (27.03.2019 - 01.04.2019)
 */
class MagicHeal(javaPlugin: JavaPlugin) : ShopItemListener(
    javaPlugin,
    ItemBuilder(config.material, damage = config.damage)
        .setName(config.name)
        .setLore(config.lore)
        .build(),
    config.delay,
    config.price
) {

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 27.03.2019 05:58.
     * Current Version: 1.0 (27.03.2019 - 27.03.2019)
     */
    private var Player.lastHealerUse
        get() = getMetadata("lastHealerUse").firstOrNull()?.asLong() ?: 0
        set(value) = setMetadata("lastHealerUse", FixedMetadataValue(javaPlugin, value))

    override fun Player.buy(): Boolean {
        var count = 0
        val item = itemStack.removedLore()
        inventory.filter { it?.equals(item, true) ?: false }.forEach { count += it.amount }
        return if (count >= 3) {
            sendMessage("${Messages.PREFIX}${TEXT}Du darfst nur drei ${itemStack.itemMeta.displayName} ${TEXT}im ${IMPORTANT}Inventar ${TEXT}haben")
            false
        } else {
            inventory.addItem(item)
            playBuySound()
            true
        }
    }

    @EventHandler
    fun onEntityDamageByEntityEvent(event: EntityDamageByEntityEvent) {

        val player = event.entity as? Player ?: return
        if (player.lastHealerUse + 1000 < System.currentTimeMillis()) return
        event.damage = 0.0

    }

    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {

        if (event.item?.clone()?.apply { amount = 1 } != itemStack.removedLore()) return
        if (event.action != RIGHT_CLICK_BLOCK && event.action != RIGHT_CLICK_AIR) return
        event.cancel()
        event.player.apply {
            if (health.toInt() != maxHealth.toInt()) {
                removeItemInHand()
                heal()
                lastHealerUse = System.currentTimeMillis()
                uniqueId.actions += Action(
                    "used-${this@MagicHeal.javaClass.simpleName}",
                    mapOf("player" to this.toDataPlayer())
                )
            } else "${Messages.PREFIX}${TEXT}Du hast eine Behandlung echt nicht nötig ;)".sendTo(this)
        }

    }

    companion object {
        private val config get() = shopItems.magicHeal
    }

}