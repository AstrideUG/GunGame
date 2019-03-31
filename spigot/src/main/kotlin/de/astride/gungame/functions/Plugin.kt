package de.astride.gungame.functions

import de.astride.gungame.GunGame
import de.astride.gungame.services.ConfigService
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Map
import org.bukkit.plugin.java.JavaPlugin
import kotlin.random.Random

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 09:17.
 * Current Version: 1.0 (27.03.2019 - 01.04.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 05:34.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
val javaPlugin: JavaPlugin get() = JavaPlugin.getPlugin(GunGame::class.java)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 29.03.2019 13:49.
 * Current Version: 1.0 (29.03.2019 - 01.04.2019)
 */
val configService get() = ConfigService.instance


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