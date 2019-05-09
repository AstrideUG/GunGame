package de.astride.location

import com.google.gson.JsonNull
import com.google.gson.JsonObject
import de.astride.gungame.functions.JsonArray
import de.astride.gungame.functions.toJsonObject
import de.astride.gungame.functions.toJsonPrimitive
import de.astride.location.data.DataLocation
import de.astride.location.data.DataLookable
import de.astride.location.data.DataVector3D
import de.astride.location.lookable.Lookable
import de.astride.location.vector.Vector3D
import org.bukkit.Bukkit

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 14:59.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
val Location.x: Double get() = vector.x
val Location.y: Double get() = vector.y
val Location.z: Double get() = vector.z
val Location.yaw: Float? get() = lookable?.yaw
val Location.pitch: Float? get() = lookable?.pitch
val Location.yawOr0: Float get() = yaw ?: 0f
val Location.pitchOr0: Float get() = pitch ?: 0f

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 13:02.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
fun Location.toBukkitLocation(): org.bukkit.Location =
    org.bukkit.Location(Bukkit.getWorld(world), x, y, z, yawOr0, pitchOr0)

fun org.bukkit.Location.toLocation(): Location = DataLocation(world?.name ?: "null", x, y, z, yaw, pitch)

fun Map<String, Any?>.toLocation(
    world: String? = null,
    x: Double = 0.0,
    y: Double = 0.0,
    z: Double = 0.0,
    yaw: Float = 0f,
    pitch: Float = 0f
): Location = DataLocation(
    this["world"]?.toString() ?: world.toString(),
    this["x"].toString().toDoubleOrNull() ?: x,
    this["y"].toString().toDoubleOrNull() ?: y,
    this["z"].toString().toDoubleOrNull() ?: z,
    this["yaw"].toString().toFloatOrNull() ?: yaw,
    this["pitch"].toString().toFloatOrNull() ?: pitch
)

fun Location.toMap(
    world: String? = null,
    x: Double = 0.0,
    y: Double = 0.0,
    z: Double = 0.0,
    yaw: Float = 0f,
    pitch: Float = 0f
): Map<String, Any?> = mutableMapOf<String, Any?>().apply {
    if (this@toMap.world != world) this["world"] = this@toMap.world
    if (this@toMap.x != x) this["x"] = this@toMap.x
    if (this@toMap.y != y) this["y"] = this@toMap.y
    if (this@toMap.z != z) this["z"] = this@toMap.z
    if (this@toMap.yawOr0 != yaw) this["yaw"] = this@toMap.yawOr0
    if (this@toMap.pitchOr0 != pitch) this["pitch"] = this@toMap.pitchOr0
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

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 14:37.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
fun Location.copy(
    world: String = this.world,
    vector: Vector3D = this.vector,
    lookable: Lookable? = this.lookable
): DataLocation = DataLocation(world, vector, lookable)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 14:48.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
fun Vector3D.copy(
    x: Double = this.x,
    y: Double = this.y,
    z: Double = this.z
): Vector3D = DataVector3D(y, x, z)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 14:50.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
fun Lookable.copy(
    yaw: Float = this.yaw,
    pitch: Float = this.pitch
): Lookable = DataLookable(yaw, pitch)