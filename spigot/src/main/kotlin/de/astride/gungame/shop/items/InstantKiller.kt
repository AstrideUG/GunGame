package de.astride.gungame.shop.items

import de.astride.gungame.functions.playBuySound
import de.astride.gungame.functions.removedLore
import de.astride.gungame.shop.ShopItemListener
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.removeItems
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 07:51.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
class InstantKiller(javaPlugin: JavaPlugin) : ShopItemListener(
    javaPlugin,
    ItemBuilder(Material.FIREBALL)
        .setName("${Colors.SECONDARY}Instant Killer")
        .setLore("${Colors.TEXT}Töte einen Spieler sofort")
        .addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1000)
        .setUnbreakable()
        .addAllItemFlags()
        .build(),
    300,
    500
) {

    override fun Player.buy() {

        inventory.addItem(itemStack.removedLore())
        playBuySound()
        closeInventory()

    }

    @EventHandler
    fun onEntityDamageByEntityEvent(event: EntityDamageByEntityEvent) {

        val damager = event.damager as? Player ?: return
        if (damager.itemInHand.type != Material.FIREBALL) return
        if (event.isCancelled) return
        if (event.entity !is Player) return
        damager.removeItems(Material.FIREBALL, 1)

    }

}