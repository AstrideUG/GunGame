package de.astride.location.vector.mutable

import de.astride.location.vector.Vector2I

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 13:08.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
interface MutableVector2I : Vector2I {
    override var x: Int
    override var z: Int
}