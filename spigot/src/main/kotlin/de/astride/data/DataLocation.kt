package de.astride.data

import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.util.Vector

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 01:52.
 * Current Version: 1.0 (02.04.2019 - 04.04.2019)
 */
@Serializable
data class DataLocation(
//  val world: World, is already saved in location
    @Serializable(with = LocationSerializer::class) val location: Location,
    @Serializable(with = LocationSerializer::class) val bedSpawnLocation: Location?,
    @Serializable(with = LocationSerializer::class) val eyeLocation: Location,
    val eyeHeight: Double,
    @Serializable(with = VectorSerializer::class) val velocity: Vector,
    @Suppress("DEPRECATION") val isOnGround: Boolean
)