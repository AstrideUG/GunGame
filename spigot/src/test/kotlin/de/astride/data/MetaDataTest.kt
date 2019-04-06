package de.astride.data

import org.junit.Test
import java.net.InetAddress
import java.net.InetSocketAddress
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 02:38.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */
class MetaDataTest {

    //given
    companion object {
        const val expectedJson: String =
            """{"address":"/0.0.0.0:55500","rawAddress":"/8.8.8.8:44400","uniqueId":"88eb0b03-d0e6-497f-9acb-2d1dcb6526f8","hasPlayedBefore":false,"lastPlayed":0,"firstPlayed":0,"locale":"de_DE","entityId":0,"listeningPluginChannels":[]}"""
        val expectedSource: MetaData = MetaData(
            InetSocketAddress(InetAddress.getByName("0.0.0.0"), 55500),
            InetSocketAddress(InetAddress.getByName("8.8.8.8"), 44400),
            UUID.fromString("88eb0b03-d0e6-497f-9acb-2d1dcb6526f8"),
            false,
            0,
            0,
            "de_DE",
            0,
            emptySet()
        )
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}