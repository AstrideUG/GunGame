package de.astride.inventory.pages

import org.bukkit.inventory.ItemStack

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 16:02.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
interface Page {

    val id: Int

    val from: Int
    val until: Int

    val items: List<ItemStack>

}