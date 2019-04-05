package de.astride.data

import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 02:14.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */
class SpeedsTest {

    //given
    companion object {
        const val expectedJson: String = """{"isSprinting":false,"isSneaking":false,"walkSpeed":0.0,"flySpeed":0.0}"""
        val expectedSource: Speeds = Speeds(false, false, 0f, 0f)
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}