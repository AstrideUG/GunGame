package de.astride.gungame.shop.items

import de.astride.data.toDataPlayer
import de.astride.gungame.functions.actions
import de.astride.gungame.functions.equals
import de.astride.gungame.functions.playBuySound
import de.astride.gungame.functions.removedLore
import de.astride.gungame.shop.ShopItemListener
import de.astride.gungame.stats.Action
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.IMPORTANT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.TEXT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.removeItemInHand
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

    override fun Player.buy(): Boolean {
        var count = 0
        val item = itemStack.removedLore()
        inventory.filter { it.equals(item, true) }.forEach { count += it.amount }
        return if (count >= 1) {
            sendMessage("${Messages.PREFIX}${TEXT}Du darfst nur ein ${itemStack.itemMeta.displayName} ${TEXT}im ${IMPORTANT}Inventar ${TEXT}haben")
            false
        } else {
            inventory.addItem(item)
            playBuySound()
            true
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onEntityDamageByEntityEvent(event: EntityDamageByEntityEvent) {

        val damager = event.damager as? Player ?: return
        if (damager.itemInHand?.clone()?.apply { amount = 1 } != itemStack.removedLore()) return
//        if (event.entity !is Player) return
        damager.removeItemInHand()
        damager.uniqueId.actions += Action("used-${javaClass.simpleName}", mapOf("player" to damager.toDataPlayer()))

    }

    companion object {
        private val config get() = shopItems.instantKiller
    }

}