package de.astride.gungame.functions

import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.LeatherArmorItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Map
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import kotlin.random.Random

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 06:37.
 * Current Version: 1.0 (27.03.2019 - 29.03.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 06:37.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
var isAllowTeams: Boolean = Random.nextBoolean()

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 06:47.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
lateinit var gameMap: Map


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 07:14.
 * Current Version: 1.0 (27.03.2019 - 29.03.2019)
 */
fun ArmorStand.changeColor() {
    val itemStack = LeatherArmorItemBuilder(Material.LEATHER_CHESTPLATE)
        .setColor(Color.fromRGB(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)))
        .addAllItemFlags()
        .build()

    chestplate = itemStack
    leggings = itemStack
    boots = itemStack
}

