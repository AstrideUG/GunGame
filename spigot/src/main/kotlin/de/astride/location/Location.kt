package de.astride.location

import de.astride.location.lookable.Lookable
import de.astride.location.vector.Vector3D

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 12:58.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
interface Location {
    val world: String
    val vector: Vector3D
    val lookable: Lookable?
}