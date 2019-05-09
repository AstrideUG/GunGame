package de.astride.location.data

import de.astride.location.Location
import de.astride.location.lookable.Lookable
import de.astride.location.vector.Vector3D

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 13:01.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
data class DataLocation(
    override val world: String,
    override val vector: Vector3D,
    override val lookable: Lookable?
) : Location {

    constructor(
        world: String,
        x: Double,
        y: Double,
        z: Double,
        yaw: Float = 0f,
        pitch: Float = 0f
    ) : this(world, DataVector3D(y, x, z), (yaw to pitch).toDataLookable())

}