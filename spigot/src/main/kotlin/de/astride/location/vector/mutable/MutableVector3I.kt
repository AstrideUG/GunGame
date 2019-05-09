package de.astride.location.vector.mutable

import de.astride.location.vector.Vector3I

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 13:08.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
interface MutableVector3I : Vector3I {
    override var x: Int
    override var y: Int
    override var z: Int
}