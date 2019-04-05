package de.astride.data

import kotlinx.serialization.Serializable
import org.bukkit.inventory.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 02:14.
 * Current Version: 1.0 (02.04.2019 - 04.04.2019)
 */
@Serializable
data class Inventories(
    val canPickupItems: Boolean,
    @Serializable(with = EntityEquipmentSerializer::class) val equipment: EntityEquipment,
    @Serializable(with = InventorySerializer::class) val enderChest: Inventory,
    @Serializable(with = InventoryViewSerializer::class) val openInventory: InventoryView?,
    @Serializable(with = InventorySerializer::class) val inventory: PlayerInventory,
    @Serializable(with = ItemStackSerializer::class) val itemInHand: ItemStack?,
    @Serializable(with = ItemStackSerializer::class) val itemOnCursor: ItemStack?
)