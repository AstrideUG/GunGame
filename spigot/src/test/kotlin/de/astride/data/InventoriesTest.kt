package de.astride.data

import com.nhaarman.mockitokotlin2.mock
import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 02:28.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */
class InventoriesTest {

    //given
    companion object {
        const val expectedJson: String =
            """{"canPickupItems":false,"equipment":"Mock for EntityEquipment, hashCode: 839502880","enderChest":"Mock for Inventory, hashCode: 1515761538","openInventory":"Mock for InventoryView, hashCode: 875452550","inventory":"Mock for PlayerInventory, hashCode: 713455593","itemInHand":"Mock for ItemStack, hashCode: 1649837804","itemOnCursor":"Mock for ItemStack, hashCode: 1286559492"}"""
        val expectedSource: Inventories = Inventories(false, mock(), mock(), mock(), mock(), mock(), mock())
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}