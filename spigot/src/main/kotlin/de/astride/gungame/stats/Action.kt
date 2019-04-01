package de.astride.gungame.stats

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 17:14.
 * Current Version: 1.0 (30.03.2019 - 01.04.2019)
 */
data class Action(
    val id: String,
    val meta: Map<String, Any?> = mapOf(),
    val timestamp: Long = System.currentTimeMillis()
) {

    init {
        if ("id" in meta) throw IllegalArgumentException("\"id\" must not be present in \"meta\"")
        if ("timestamp" in meta) throw IllegalArgumentException("\"timestamp\" must not be present in \"meta\"")
    }

}