package de.astride.gungame.shop.items

import de.astride.gungame.functions.actions
import de.astride.gungame.functions.playBuySound
import de.astride.gungame.functions.removedLore
import de.astride.gungame.kits.heal
import de.astride.gungame.kits.lastHealerUse
import de.astride.gungame.shop.ShopItemListener
import de.astride.gungame.stats.Action
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.hasItems
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.removeItemInHand
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action.RIGHT_CLICK_AIR
import org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 07:51.
 * Current Version: 1.0 (27.03.2019 - 31.03.2019)
 */
class MagicHeal(javaPlugin: JavaPlugin) : ShopItemListener(
    javaPlugin,
    ItemBuilder(Material.INK_SACK, damage = 1)
        .setName("${SECONDARY}Magic Heal")
        .setLore("${TEXT}Er regeneriert dich sofort")
        .build(),
    30,
    50
) {

    override fun Player.buy() = if (inventory.hasItems(Material.INK_SACK) >= 3) {
        sendMessage("${Messages.PREFIX}${TEXT}Du darfst nur drei ${itemStack.itemMeta.displayName} ${TEXT}im ${IMPORTANT}Inventar ${TEXT}haben")
        false
    } else {
        inventory.addItem(itemStack.removedLore())
        playBuySound()
        true
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
                uniqueId.actions += Action("used-${this@MagicHeal.javaClass.simpleName}", mapOf("player" to this))
            } else "${Messages.PREFIX}${TEXT}Du hast eine Behandlung echt nicht nötig ;)".sendTo(this)
        }

    }

}