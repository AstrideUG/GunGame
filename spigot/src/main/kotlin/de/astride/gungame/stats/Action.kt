package de.astride.gungame.stats

import de.astride.data.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 17:14.
 * Current Version: 1.0 (30.03.2019 - 11.04.2019)
 */
@Serializable
data class Action(
    val key: String,
    val meta: Map<String, Any?> = mapOf(),
    val timestamp: Long = System.currentTimeMillis(),
    @Serializable(with = UUIDSerializer::class) val uuid: UUID = UUID.randomUUID()
)