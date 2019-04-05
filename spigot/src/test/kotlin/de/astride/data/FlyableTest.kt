package de.astride.data

import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 02:08.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */
class FlyableTest {

    //given
    companion object {
        const val expectedJson: String = """{"isFlying":false,"allowFlight":false}"""
        val expectedSource: Flyable = Flyable(isFlying = false, allowFlight = false)
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}