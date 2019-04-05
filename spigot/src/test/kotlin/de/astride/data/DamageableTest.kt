package de.astride.data

import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 22:40.
 * Current Version: 1.0 (04.04.2019 - 05.04.2019)
 */
class DamageableTest {

    //given
    companion object {
        const val expectedJson: String =
            """{"maximumNoDamageTicks":0,"noDamageTicks":0,"lastDamage":null,"lastDamageCause":null}"""
        val expectedSource: Damageable = Damageable(0, 0, null, null)
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}