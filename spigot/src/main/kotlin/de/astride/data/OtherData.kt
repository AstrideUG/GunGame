package de.astride.data

import org.bukkit.GameMode
import org.bukkit.Server
import org.bukkit.WeatherType
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.scoreboard.Scoreboard

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 02:52.
 * Current Version: 1.0 (02.04.2019 - 04.04.2019)
 */
//TODO: improve
data class OtherData(
    val playerListName: String,
    val playerTime: Long,
    val playerTimeOffset: Long,
    val playerWeather: WeatherType?,

    val isPlayerTimeRelative: Boolean,
    val isBlocking: Boolean,
    val isConversing: Boolean,
    val isCustomNameVisible: Boolean,
    val isInsideVehicle: Boolean,

    val isLeashed: Boolean,
    val leashHolder: Entity?,

    val isEmpty: Boolean,
    val passenger: Entity?,

    val isDead: Boolean,
    val isValid: Boolean,
    val isOnline: Boolean,

    val scoreboard: Scoreboard,
    val killer: Player,
    val gameMode: GameMode,
    val fallDistance: Float,
    val type: EntityType,
    val vehicle: Entity,
    val maximumAir: Int,
    val remainingAir: Int,
    val removeWhenFarAway: Boolean,
    val server: Server,
    val activePotionEffects: Collection<PotionEffect>,

    val collidesWithEntities: Boolean,
    val hiddenPlayers: Set<Player>
)