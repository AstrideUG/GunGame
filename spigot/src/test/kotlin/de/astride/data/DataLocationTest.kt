package de.astride.data

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Server
import org.bukkit.util.Vector
import org.junit.Before
import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 01:43.
 * Current Version: 1.0 (05.04.2019 - 06.04.2019)
 */
class DataLocationTest {

    //given
    companion object {
        const val expectedJson: String =
            """{"location":{"world":"WorldName","x":0.0,"y":0.0,"z":0.0,"yaw":0.0,"pitch":0.0},"bedSpawnLocation":null,"eyeLocation":{"world":"WorldName","x":0.0,"y":0.0,"z":0.0,"yaw":0.0,"pitch":0.0},"eyeHeight":0.0,"velocity":{"x":0.0,"y":0.0,"z":0.0},"isOnGround":false}"""
        val expectedSource: DataLocation = DataLocation(
            Location(world, 0.0, 0.0, 0.0),
            null,
            Location(world, 0.0, 0.0, 0.0),
            0.0,
            Vector(0, 0, 0),
            false
        )
    }

    @Before
    fun before() {
        Bukkit.setServer(mock<Server>().apply {
            whenever(getWorld("WorldName")).thenReturn(world)
            whenever(logger).thenReturn(mock())
        })
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}