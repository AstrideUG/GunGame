package de.astride.gungame.shop.items

import de.astride.gungame.functions.playBuySound
import de.astride.gungame.kits.heal
import de.astride.gungame.kits.lastHealerUse
import de.astride.gungame.shop.ShopItemListener
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.hasItems
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.removeItems
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 07:51.
 * Current Version: 1.0 (27.03.2019 - 29.03.2019)
 */
class MagicHeal(javaPlugin: JavaPlugin) : ShopItemListener(
    javaPlugin,
    ItemBuilder(Material.INK_SACK, damage = 1)
        .setName("${SECONDARY}Magic Heal")
        .setLore("${TEXT}Er regeneriert dich sofort")
        .setUnbreakable()
        .addAllItemFlags()
        .build(),
    30,
    50
) {

    override fun Player.buy() = if (inventory.hasItems(Material.INK_SACK) >= 3)
        sendMessage("${Messages.PREFIX}${TEXT}Du darfst nur drei ${itemStack.itemMeta.displayName} ${TEXT}im ${IMPORTANT}Inventar ${TEXT}haben")
    else {
        inventory.addItem(ItemBuilder(itemStack).removeLore(0).build())
        playBuySound()
        closeInventory()
    }

    @EventHandler
    fun onEntityDamageByEntityEvent(event: EntityDamageByEntityEvent) {

        val player = event.entity as? Player ?: return
        if (player.lastHealerUse + 1000 < System.currentTimeMillis()) return
        event.damage = 0.0

    }

    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {

        if (event.item?.clone()?.apply { amount = 1 } != itemStack) return
        if (event.action != Action.RIGHT_CLICK_BLOCK && event.action != Action.RIGHT_CLICK_AIR) return
        event.cancel()
        event.player.apply {
            if (health.toInt() != maxHealth.toInt()) {
                removeItems(Material.INK_SACK, 1)
                heal()
            } else "${Messages.PREFIX}${TEXT}Du hast schon volle Herzen".sendTo(this)
        }

    }

}