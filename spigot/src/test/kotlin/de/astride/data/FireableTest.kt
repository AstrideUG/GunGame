package de.astride.data

import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 02:06.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */
class FireableTest {

    //given
    companion object {
        const val expectedJson: String = """{"fireTicks":0,"maxFireTicks":0}"""
        val expectedSource: Fireable = Fireable(0, 0)
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}