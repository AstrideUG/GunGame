package de.astride.data

import org.bukkit.Location
import org.bukkit.util.Vector

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 01:52.
 * Current Version: 1.0 (02.04.2019 - 04.04.2019)
 */
data class DataLocation(
//  val world: World, is already saved in location
    val location: Location,
    val bedSpawnLocation: Location?,
    val eyeLocation: Location,
    val eyeHeight: Double,
    val velocity: Vector,
    @Suppress("DEPRECATION") val isOnGround: Boolean
)