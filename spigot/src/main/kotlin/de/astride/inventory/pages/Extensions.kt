package de.astride.inventory.pages

import org.bukkit.inventory.Inventory

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 16:24.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 16:24.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
fun Page.setItems(inventory: Inventory) {
    val items = items
    val intRange = from until until
    for (i in intRange) {
        val i1 = i - from + (id * intRange.count())
        val item = if (i1 >= items.size) null else items[i1]
        inventory.setItem(i, item)
    }
}
