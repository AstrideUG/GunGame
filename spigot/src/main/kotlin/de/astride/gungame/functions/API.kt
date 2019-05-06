package de.astride.gungame.functions

import org.bukkit.Location
import org.bukkit.World

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 06.04.2019 04:08.
 * Current Version: 1.0 (06.04.2019 - 06.05.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 06.04.2019 03:06.
 * Current Version: 1.0 (06.04.2019 - 06.04.2019)
 */
@JvmName("replaceNullable")
fun String?.replace(key: String, value: Any): String? = this?.replace(key, value)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 06.04.2019 06:24.
 * Current Version: 1.0 (06.04.2019 - 06.04.2019)
 */
fun String.replace(key: String, value: Any): String = replace("@$key@", value.toString(), ignoreCase = true)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 06.05.2019 08:30.
 *
 * TODO: move to Darkness
 *
 * Current Version: 1.0 (06.05.2019 - 06.05.2019)
 */
fun Location.edit(
    world: World? = null,
    x: Double? = null,
    y: Double? = null,
    z: Double? = null,
    yaw: Float? = null,
    pitch: Float? = null
): Location = Location(
    world ?: this.world,
    x ?: this.z,
    y ?: this.y,
    z ?: this.z,
    yaw ?: this.yaw,
    pitch ?: this.pitch
)