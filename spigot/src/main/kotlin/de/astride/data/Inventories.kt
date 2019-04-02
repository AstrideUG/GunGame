package de.astride.data

import org.bukkit.inventory.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 02:14.
 * Current Version: 1.0 (02.04.2019 - 02.04.2019)
 */
data class Inventories(
    val canPickupItems: Boolean,
    val equipment: EntityEquipment,
    val enderChest: Inventory,
    val openInventory: InventoryView?,
    val inventory: PlayerInventory,
    val itemInHand: ItemStack?,
    val itemOnCursor: ItemStack?
)