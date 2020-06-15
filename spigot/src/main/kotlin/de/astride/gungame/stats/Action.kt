package de.astride.gungame.stats

import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 17:14.
 * Current Version: 1.0 (30.03.2019 - 11.04.2019)
 */
data class Action(
    val key: String,
    val meta: Map<String, Any?> = mapOf(),
    val timestamp: Long = System.currentTimeMillis(),
    val uuid: UUID = UUID.randomUUID()
)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 15.05.2019 00:00.
 * Current Version: 1.0 (15.05.2019 - 15.05.2019)
 */
@Suppress("UNCHECKED_CAST")
fun Map<String, Any?>.toAction(): Action = Action(
    this["key"].toString(),
    this["meta"] as? Map<String, Any?> ?: mutableMapOf(),
    this["timestamp"]?.toString()?.toLongOrNull() ?: -1L,
    try {
        UUID.fromString(this["uuid"].toString())
    } catch (ex: IllegalArgumentException) {
        null
    } ?: UUID.randomUUID()
)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 15.05.2019 00:02.
 * Current Version: 1.0 (15.05.2019 - 15.05.2019)
 */
fun Action.toMap(): Map<String, Any> = mapOf(
    "key" to key,
    "meta" to meta,
    "timestamp" to timestamp,
    "uuid" to uuid.toString()
)