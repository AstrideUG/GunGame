package de.astride.data

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import kotlinx.serialization.stringify
import org.bukkit.World
import kotlin.test.assertEquals

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 01:44.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 01:44.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */
inline fun <reified T : Any> `(de)serialize`(expectedJson: String, expectedSource: T) {

    //when
    //then
    //

    val json = Json.stringify(expectedSource)
    assertEquals(expectedJson, json)

    val source = Json.parse<T>(expectedJson)
    assertEquals(expectedSource, source)

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 02:45.
 * Current Version: 1.0 (05.04.2019 - 06.04.2019)
 */
val world: World = mock<World>().apply {
    whenever(toString()).thenReturn("WorldName")
    whenever(name).thenReturn("WorldName")
}