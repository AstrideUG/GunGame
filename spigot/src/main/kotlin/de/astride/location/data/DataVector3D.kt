package de.astride.location.data

import de.astride.location.vector.Vector3D

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 13:21.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
data class DataVector3D(
    override val y: Double,
    override val x: Double,
    override val z: Double
) : Vector3D