package de.astride.data

import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 01:42.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */
class DataFoodTest {

    //given
    companion object {
        const val expectedJson: String = """{"foodLevel":0.0,"saturation":0.0,"exhaustion":0.0}"""
        val expectedSource: DataFood = DataFood(0f, 0f, 0f)
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}