package de.astride.gungame.setup.page

import de.astride.gungame.functions.configService
import de.astride.gungame.setup.Setup
import net.darkdevelopers.darkbedrock.darkness.spigot.inventory.pages.Page
import org.bukkit.inventory.ItemStack

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 22:01.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
data class MapsPage(
    override val id: Int,
    override val from: Int = 19,
    override val until: Int = 26
) : Page {

    override val items: List<ItemStack>
        get() = configService.maps.maps.mapIndexed { index, map ->
            Setup.generateMapDisplayItem(index, map)
        }

}