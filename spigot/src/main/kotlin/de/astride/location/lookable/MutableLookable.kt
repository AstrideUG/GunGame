package de.astride.location.lookable

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 09.05.2019 13:14.
 * Current Version: 1.0 (09.05.2019 - 09.05.2019)
 */
interface MutableLookable : Lookable {
    override var yaw: Float
    override var pitch: Float
}