package de.astride.data

import org.bukkit.Location
import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 02:42.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */
class TargetableTest {

    //given
    companion object {
        const val expectedJson: String =
            """{"spectatorTarget":null,"compassTarget":{"world":"WorldName","x":0.0,"y":0.0,"z":0.0,"yaw":0.0,"pitch":0.0}}"""
        val expectedSource: Targetable = Targetable(null, Location(world, 0.0, 0.0, 0.0))
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}