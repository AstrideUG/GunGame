package de.astride.data

import kotlinx.serialization.Serializable

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 01:54.
 * Current Version: 1.0 (02.04.2019 - 04.04.2019)
 */
@Serializable
data class DataExp(
    val level: Int,
    val exp: Float,
    val exhaustion: Float,
    val expToLevel: Int,
    val totalExperience: Int
)