package de.astride.data

import kotlinx.serialization.Serializable
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
@Serializable
//TODO: improve
data class OtherData(
    @Serializable(with = AnySerializer::class) val playerListName: String,
    @Serializable(with = AnySerializer::class) val playerTime: Long,
    @Serializable(with = AnySerializer::class) val playerTimeOffset: Long,
    @Serializable(with = AnySerializer::class) val playerWeather: WeatherType?,

    @Serializable(with = AnySerializer::class) val isPlayerTimeRelative: Boolean,
    @Serializable(with = AnySerializer::class) val isBlocking: Boolean,
    @Serializable(with = AnySerializer::class) val isConversing: Boolean,
    @Serializable(with = AnySerializer::class) val isCustomNameVisible: Boolean,
    @Serializable(with = AnySerializer::class) val isInsideVehicle: Boolean,

    @Serializable(with = AnySerializer::class) val isLeashed: Boolean,
    @Serializable(with = AnySerializer::class) val leashHolder: Entity?,

    @Serializable(with = AnySerializer::class) val isEmpty: Boolean,
    @Serializable(with = AnySerializer::class) val passenger: Entity?,

    @Serializable(with = AnySerializer::class) val isDead: Boolean,
    @Serializable(with = AnySerializer::class) val isValid: Boolean,
    @Serializable(with = AnySerializer::class) val isOnline: Boolean,

    @Serializable(with = AnySerializer::class) val scoreboard: Scoreboard,
    @Serializable(with = AnySerializer::class) val killer: Player?,
    @Serializable(with = AnySerializer::class) val gameMode: GameMode,
    @Serializable(with = AnySerializer::class) val fallDistance: Float,
    @Serializable(with = AnySerializer::class) val type: EntityType,
    @Serializable(with = AnySerializer::class) val vehicle: Entity?,
    @Serializable(with = AnySerializer::class) val maximumAir: Int,
    @Serializable(with = AnySerializer::class) val remainingAir: Int,
    @Serializable(with = AnySerializer::class) val removeWhenFarAway: Boolean,
    @Serializable(with = AnySerializer::class) val server: Server,
    @Serializable(with = AnySerializer::class) val activePotionEffects: Collection<PotionEffect>,

    @Serializable(with = AnySerializer::class) val collidesWithEntities: Boolean,
    @Serializable(with = AnySerializer::class) val hiddenPlayers: Set<Player>
)