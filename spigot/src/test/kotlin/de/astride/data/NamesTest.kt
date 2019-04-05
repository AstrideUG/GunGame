package de.astride.data

import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 02:08.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */
class NamesTest {

    //given
    companion object {
        const val expectedJson: String = """{"name":"foo","displayName":"bar"}"""
        val expectedSource: Names = Names("foo", "bar")
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}