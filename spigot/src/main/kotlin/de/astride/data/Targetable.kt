package de.astride.data

import org.bukkit.Location

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 02:04.
 * Current Version: 1.0 (02.04.2019 - 02.04.2019)
 */
data class Targetable(
    val spectatorTarget: org.bukkit.entity.Entity,
    val compassTarget: Location
)