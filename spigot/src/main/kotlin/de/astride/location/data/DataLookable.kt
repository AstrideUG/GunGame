package de.astride.location.data

import de.astride.location.lookable.Lookable

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 13:23.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
data class DataLookable(
    override val yaw: Float,
    override val pitch: Float
) : Lookable

fun Pair<Float, Float>.toDataLookable(): DataLookable? =
    if (first == 0f && second == 0f) null else DataLookable(first, second)