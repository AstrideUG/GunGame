package de.astride.data

import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.entity.Entity

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 02:04.
 * Current Version: 1.0 (02.04.2019 - 04.04.2019)
 */
@Serializable
data class Targetable(
    @Serializable(with = AnySerializer::class) val spectatorTarget: Entity?,
    @Serializable(with = LocationSerializer::class) val compassTarget: Location
)