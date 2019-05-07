package de.astride.gungame.setup

import net.darkdevelopers.darkbedrock.darkness.spigot.builder.inverntory.InventoryBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Items
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 07.05.2019 12:28.
 * Current Version: 1.0 (07.05.2019 - 07.05.2019)
 */


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 07.05.2019 12:30.
 * Current Version: 1.0 (07.05.2019 - 07.05.2019)
 */
object Setup {

    val backItem: ItemStack = ItemBuilder(Items.LEAVE.itemStack).setName("${SECONDARY}Zurück").build()

    val all: Inventory = InventoryBuilder(InventoryType.HOPPER, "${SECONDARY}GunGame Setup All")
        .setDesign()
        .setItem(1, ItemBuilder(Material.MAP).setName("${SECONDARY}Maps").addAllItemFlags().build())
        .setItem(3, ItemBuilder(Items.CHEST.itemStack).setName("${SECONDARY}Shops").build())
        .build()
    val maps: Inventory = InventoryBuilder(5 * 9, "${SECONDARY}GunGame Setup Maps").generate()
    val shops: Inventory = InventoryBuilder(5 * 9, "${SECONDARY}GunGame Setup Shops").generate()

    fun generateShopDisplayItem(id: Int, location: Location): ItemStack = ItemBuilder(Items.CHEST.itemStack)
        .setName("${SECONDARY}Nummer $id")
        .setLore(
            "",
            "${TEXT}Location:",
            "$TEXT    World: $IMPORTANT${location.world}",
            "$TEXT    X: $IMPORTANT${location.x}",
            "$TEXT    Y: $IMPORTANT${location.y}",
            "$TEXT    Z: $IMPORTANT${location.z}",
            ""
        )
        .build()

    private fun InventoryBuilder.generate(): Inventory = setDesign().apply {
        val base = ItemBuilder(Material.PAPER).addItemFlags(ItemFlag.HIDE_ENCHANTS)
        //TODO: add enchantment if can be used
        setItem(38, base.setName("${SECONDARY}Vorherige Seite").build())
        setItem(42, base.setName("${SECONDARY}Nächste Seite").build())
    }
        .setItem(40, ItemBuilder(Material.REDSTONE).setName("${SECONDARY}Erstelle einen neuen").build())
        .setItem(44, backItem)
        .setRange(ItemStack(Material.AIR), 19, 26)
        .build()

}
