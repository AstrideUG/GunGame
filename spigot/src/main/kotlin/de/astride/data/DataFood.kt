package de.astride.data

import kotlinx.serialization.Serializable

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 01:57.
 * Current Version: 1.0 (02.04.2019 - 04.04.2019)
 */
@Serializable
data class DataFood(
    val foodLevel: Float,
    val saturation: Float,
    val exhaustion: Float
)