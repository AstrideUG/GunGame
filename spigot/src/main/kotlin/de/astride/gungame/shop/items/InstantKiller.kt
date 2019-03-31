package de.astride.gungame.shop.items

import de.astride.gungame.functions.actions
import de.astride.gungame.functions.playBuySound
import de.astride.gungame.functions.removedLore
import de.astride.gungame.shop.ShopItemListener
import de.astride.gungame.stats.Action
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.IMPORTANT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.TEXT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.hasItems
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.removeItemInHand
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 07:51.
 * Current Version: 1.0 (27.03.2019 - 31.03.2019)
 */
class InstantKiller(javaPlugin: JavaPlugin) : ShopItemListener(
    javaPlugin,
    ItemBuilder(config.material, damage = config.damage)
        .setName(config.name)
        .setLore(config.lore)
        .addEnchant(Enchantment.DAMAGE_ALL, 1000, true)
        .addItemFlags(ItemFlag.HIDE_ENCHANTS)
        .build(),
    config.delay,
    config.price
) {

    override fun Player.buy() = if (inventory.hasItems(Material.FIREBALL) >= 1) {
        sendMessage("${Messages.PREFIX}${TEXT}Du darfst nur ein ${itemStack.itemMeta.displayName} ${TEXT}im ${IMPORTANT}Inventar ${TEXT}haben")
        false
    } else {
        inventory.addItem(itemStack.removedLore())
        playBuySound()
        true
    }

    @EventHandler(ignoreCancelled = true)
    fun onEntityDamageByEntityEvent(event: EntityDamageByEntityEvent) {

        val damager = event.damager as? Player ?: return
        if (damager.itemInHand?.clone()?.apply { amount = 1 } != itemStack.removedLore()) return
//        if (event.entity !is Player) return
        damager.removeItemInHand()
        damager.uniqueId.actions += Action("used-${javaClass.simpleName}", mapOf("player" to damager))

    }

    companion object {
        private val config get() = shopItems.instantKiller
    }

}