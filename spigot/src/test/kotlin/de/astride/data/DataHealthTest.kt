package de.astride.data

import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 01:43.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */
class DataHealthTest {

    //given
    companion object {
        const val expectedJson: String =
            """{"health":0.0,"maxHealth":0.0,"healthScale":0.0,"isHealthScaled":false,"isInvulnerable":false}"""
        val expectedSource: DataHealth = DataHealth(
            0.0,
            0.0,
            0.0,
            isHealthScaled = false,
            isInvulnerable = false
        )
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}