package de.astride.gungame.functions

import de.astride.gungame.GunGame
import de.astride.gungame.services.ConfigService
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Map
import org.bukkit.plugin.java.JavaPlugin

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 09:17.
 * Current Version: 1.0 (27.03.2019 - 06.04.2019)
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
 * Created by Lars Artmann | LartyHD on 06.04.2019 00:49.
 * Current Version: 1.0 (06.04.2019 - 06.04.2019)
 */
val messages get() = ConfigService.messagesInstance

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 06:37.
 * Current Version: 1.0 (27.03.2019 - 06.04.2019)
 */
lateinit var allowTeams: AllowTeams

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 06:47.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
lateinit var gameMap: Map

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 06.04.2019 00:03.
 * Current Version: 1.0 (06.04.2019 - 06.04.2019)
 */
sealed class AllowTeams {

    abstract val result: Boolean
    val type: String get() = javaClass.simpleName!!
    val asString: String get() = if (result) messages.teamsAllow else messages.teamsDisAllow

    object Random : AllowTeams() {
        override val result: Boolean get() = kotlin.random.Random.nextBoolean()
    }

    object True : AllowTeams() {
        override val result: Boolean get() = true
    }

    object False : AllowTeams() {
        override val result: Boolean get() = false
    }

    companion object {

        @Suppress("MemberVisibilityCanBePrivate")
        val default: AllowTeams = Random

        fun byName(name: String?) = when (name?.toLowerCase()) {
            True.type.toLowerCase() -> True
            False.type.toLowerCase() -> False
            else -> default
        }

    }

    operator fun not(): AllowTeams = if (result == AllowTeams.True.result) AllowTeams.True else AllowTeams.False

}
