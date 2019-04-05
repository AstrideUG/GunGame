package de.astride.data

import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 02:42.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */
class WhiteAndBlackListTest {

    //given
    companion object {
        const val expectedJson: String = """{"isWhitelisted":false,"isBanned":false}"""
        val expectedSource: WhiteAndBlackList = WhiteAndBlackList(isWhitelisted = false, isBanned = false)
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}