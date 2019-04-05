package de.astride.data

import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 02:16.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */
class PermissablesTest {

    //given
    companion object {
        const val expectedJson: String = """{"isOp":false,"effectivePermissions":[]}"""
        val expectedSource: Permissables = Permissables(false, emptySet())
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}