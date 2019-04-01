package de.astride.gungame.functions

import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import org.bukkit.inventory.ItemStack

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 12:15.
 * Current Version: 1.0 (30.03.2019 - 01.04.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 12:15.
 * Current Version: 1.0 (30.03.2019 - 30.03.2019)
 */
fun ItemStack.removedLore(line: Int = 0) = ItemBuilder(clone()).removeLore(line).build()

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 01.04.2019 16:43.
 * Current Version: 1.0 (01.04.2019 - 01.04.2019)
 */
fun ItemStack.equals(itemStack: ItemStack, ignoreAmount: Boolean = false): Boolean =
    (if (ignoreAmount) clone().apply { amount = 1 } else this) == itemStack