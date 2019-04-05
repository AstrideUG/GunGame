package de.astride.gungame.stats

import kotlinx.serialization.Serializable

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 17:14.
 * Current Version: 1.0 (30.03.2019 - 04.04.2019)
 */
@Serializable
data class Action(
    val id: String,
    val meta: Map<String, Any?> = mapOf(),
    val timestamp: Long = System.currentTimeMillis()
)