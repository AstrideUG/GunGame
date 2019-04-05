package de.astride.data

import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 02:11.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */
class SleepableTest {

    //given
    companion object {
        const val expectedJson: String = """{"isSleeping":false,"isSleepingIgnored":false}"""
        val expectedSource: Sleepable = Sleepable(isSleeping = false, isSleepingIgnored = false)
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}