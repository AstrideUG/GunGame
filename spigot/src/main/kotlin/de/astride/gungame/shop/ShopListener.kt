/*
 * © Copyright - Lars Artmann | LartyHD 2018.
 */
package de.astride.gungame.shop

import de.astride.gungame.functions.actions
import de.astride.gungame.functions.changeColor
import de.astride.gungame.functions.messages
import de.astride.gungame.shop.items.Arrows
import de.astride.gungame.shop.items.InstantKiller
import de.astride.gungame.shop.items.MagicHeal
import de.astride.gungame.stats.Action
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.inverntory.InventoryBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.02.2018 02:32.
 * Current Version: 1.0 (19.02.2018 - 15.04.2019)
 */
class ShopListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    private val inventory: Inventory = InventoryBuilder(InventoryType.HOPPER, messages.shop.name).setDesign().build()
    private val items: List<ShopItemListener> =
        listOf(MagicHeal(javaPlugin), InstantKiller(javaPlugin), Arrows(javaPlugin))

    init {
        for (i in 0 until 4 step 2) inventory.setItem(i, items[i].itemStack)
    }

    @EventHandler
    fun onInventoryClickEvent(event: InventoryClickEvent) {

        if (event.whoClicked.openInventory.topInventory != inventory) return
        event.cancel()
        val item = items.find { event.currentItem == it.itemStack } ?: return
        item.checkedBuy(event.whoClicked as Player)

    }

    @EventHandler
    fun onPlayerInteractAtEntityEvent(event: PlayerInteractAtEntityEvent) {

        val armorStand = event.rightClicked as? ArmorStand ?: return
        if (armorStand.customName != messages.shop.entityName) return
        val player = event.player ?: return
        event.cancel()
        armorStand.changeColor()
        if (!player.isSneaking) player.openInventory(inventory)
        player.uniqueId.actions += Action(
            "shop-change-color",
            mapOf("sneaking" to player.isSneaking/*"player" to player.toDataPlayer()*/)
        )

    }

}

