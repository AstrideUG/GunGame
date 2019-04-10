package de.astride.data

import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 01:38.
 * Current Version: 1.0 (05.04.2019 - 11.04.2019)
 */
class DataExpTest {

    //given
    companion object {
        const val expectedJson: String =
            """{"level":0,"exp":0.0,"expToLevel":0,"totalExperience":0}"""
        val expectedSource: DataExp = DataExp(0, 0f, 0, 0)
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}