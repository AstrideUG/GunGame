package de.astride.location

import com.google.gson.JsonNull
import com.google.gson.JsonObject
import de.astride.gungame.functions.JsonArray
import de.astride.gungame.functions.toJsonObject
import de.astride.gungame.functions.toJsonPrimitive
import de.astride.location.data.DataLocation
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

fun org.bukkit.Location.toLocation(): Location = DataLocation(world?.name ?: "null", x, y, z, yaw, pitch)

fun Map<String, Any?>.toLocation(): Location = DataLocation(
    this["world"] as? String ?: "null",
    this["x"] as? Double ?: 0.0,
    this["y"] as? Double ?: 0.0,
    this["z"] as? Double ?: 0.0,
    this["yaw"] as? Float ?: 0f,
    this["pitch"] as? Float ?: 0f
)

fun Location.toMap(): Map<String, Any?> = mutableMapOf<String, Any?>().apply {
    this["world"] = world
    if (vector.x != 0.0) this["x"] = vector.x
    if (vector.y != 0.0) this["y"] = vector.y
    if (vector.z != 0.0) this["z"] = vector.z
    val lookable = lookable ?: return@apply
    if (lookable.yaw != 0f) this["yaw"] = lookable.yaw
    if (lookable.pitch != 0f) this["pitch"] = lookable.pitch
}

fun Location.toJsonObject(serializeNull: Boolean = false): JsonObject = toMap().mapNotNull { (key, value) ->
    val jsonElement = when (value) {
        null -> if (serializeNull) JsonNull.INSTANCE else null
        is Iterable<*> -> JsonArray(value.mapNotNull { it?.toJsonPrimitive() })
        //TODO     is Map<String, Any?> -> JsonObject()
        else -> value.toJsonPrimitive()
    }
    jsonElement ?: return@mapNotNull null
    key to jsonElement
}.toMap().toJsonObject()