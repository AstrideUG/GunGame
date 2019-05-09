package de.astride.location

import org.bukkit.Bukkit

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 13:02.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
fun Location.toBukkitLocation(): org.bukkit.Location = org.bukkit.Location(
    Bukkit.getWorld(world),
    vector.x,
    vector.y,
    vector.z,
    lookable?.yaw ?: 0f,
    lookable?.pitch ?: 0f
)