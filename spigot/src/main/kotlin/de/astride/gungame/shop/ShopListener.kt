/*
 * © Copyright - Lars Artmann | LartyHD 2018.
 */
package de.astride.gungame.shop

import de.astride.gungame.functions.changeColor
import de.astride.gungame.shop.items.InstantKiller
import de.astride.gungame.shop.items.KeepInventory
import de.astride.gungame.shop.items.LevelUp
import de.astride.gungame.shop.items.MagicHeal
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.inverntory.InventoryBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.SECONDARY
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Items
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.02.2018 02:32.
 * Current Version: 1.0 (19.02.2018 - 29.03.2019)
 */
class ShopListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    private val inventory: Inventory = InventoryBuilder(InventoryType.HOPPER, "${SECONDARY}Shop").setDesign().build()
    private val items: List<ShopItemListener> =
        listOf(MagicHeal(javaPlugin), LevelUp(javaPlugin), InstantKiller(javaPlugin), KeepInventory(javaPlugin))

    init {
        for (i in 0..1) inventory.setItem(i, items[i].itemStack)
        for (i in 3..4) inventory.setItem(i, items[i - 1].itemStack)
    }

    @EventHandler
    fun onInventoryClickEvent(event: InventoryClickEvent) {

        if (event.whoClicked.openInventory.topInventory != inventory) return
        val item = items.find { event.currentItem == it.itemStack } ?: return
        item.checkedBuy(event.whoClicked as Player)

    }

    @EventHandler
    fun onPlayerInteractAtEntityEvent(event: PlayerInteractAtEntityEvent) {

        val armorStand = event.rightClicked as? ArmorStand ?: return
        armorStand.changeColor()
        event.cancel()
        if (!event.player.isSneaking) event.player.openInventory(inventory)

    }

    @EventHandler
    fun on(event: PlayerToggleSneakEvent) {
        if (event.isSneaking) return

        val armorStand =
            event.player.location.world.spawnEntity(event.player.location, EntityType.ARMOR_STAND) as ArmorStand
        armorStand.apply {

            customName = "${SECONDARY}Shop"
            isCustomNameVisible = true
            setGravity(false)
            isVisible = false
            isSmall = true
            helmet = Items.CHEST.itemStack
            changeColor()

        }

    }

}

