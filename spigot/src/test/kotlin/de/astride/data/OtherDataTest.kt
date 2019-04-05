package de.astride.data

import com.nhaarman.mockitokotlin2.mock
import org.bukkit.GameMode
import org.bukkit.entity.EntityType
import org.junit.Test

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 05.04.2019 02:40.
 * Current Version: 1.0 (05.04.2019 - 05.04.2019)
 */
class OtherDataTest {

    //given
    companion object {
        const val expectedJson: String =
            """{"playerListName":"lulu","playerTime":"0","playerTimeOffset":"0","playerWeather":null,"isPlayerTimeRelative":"false","isBlocking":"false","isConversing":"false","isCustomNameVisible":"false","isInsideVehicle":"false","isLeashed":"false","leashHolder":null,"isEmpty":"false","passenger":null,"isDead":"false","isValid":"false","isOnline":"false","scoreboard":"Mock for Scoreboard, hashCode: 1927393634","killer":null,"gameMode":"SURVIVAL","fallDistance":"0.0","type":"PLAYER","vehicle":null,"maximumAir":"0","remainingAir":"0","removeWhenFarAway":"false","server":"Mock for Server, hashCode: 972581327","activePotionEffects":[],"collidesWithEntities":"true","hiddenPlayers":[]}"""
        val expectedSource: OtherData = OtherData(
            "lulu",
            0,
            0,
            null,
            false,
            false,
            false,
            false,
            false,
            false,
            null,
            false,
            null,
            false,
            false,
            false,
            mock(),
            null,
            GameMode.SURVIVAL,
            0f,
            EntityType.PLAYER,
            null,
            0,
            0,
            false,
            mock(),
            emptyList(),
            true,
            emptySet()
        )
    }

    @Test
    fun `(de)serialize`(): Unit = `(de)serialize`(expectedJson, expectedSource)

}