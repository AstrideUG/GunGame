package de.astride.data

import com.nhaarman.mockitokotlin2.mock
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.junit.Before
import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 02:28.
 * Current Version: 1.0 (05.04.2019 - 06.04.2019)
 */
class InventoriesTest {

    //given
    companion object {
        const val expectedJson: String =
            """{"canPickupItems":false,"equipment":"Mock for EntityEquipment, hashCode: 839502880","enderChest":"Mock for Inventory, hashCode: 1515761538","openInventory":"Mock for InventoryView, hashCode: 875452550","inventory":"Mock for PlayerInventory, hashCode: 713455593","itemInHand":"Mock for ItemStack, hashCode: 1649837804","itemOnCursor":"Mock for ItemStack, hashCode: 1286559492"}"""
        val expectedSource: Inventories = Inventories(
            false,
            mock(),
            mock(),
            mock(),
            mock(),
            itemInHand = ItemStack(Material.REDSTONE_BLOCK),
            itemOnCursor = ItemStack(Material.FIREBALL, 10)
        )
    }

    @Before
    fun before() {
//        Bukkit.setServer(mock<Server>().apply {
//            whenever(logger).thenReturn(mock())
//            whenever(itemFactory).thenReturn(mock<ItemFactory>().apply {
//                whenever(equals(null, null)).then {
//                    val itemMeta: ItemMeta = it.getArgument(0)
//                    val itemMeta1: ItemMeta = it.getArgument(1)
//
//                    itemMeta.displayName == itemMeta1.displayName && itemMeta.lore == itemMeta1.lore && itemMeta.itemFlags == itemMeta1.itemFlags
//                }
//            })
//        })
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}