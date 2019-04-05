package de.astride.data

import kotlinx.serialization.Serializable
import org.bukkit.event.entity.EntityDamageEvent

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 02:44.
 * Current Version: 1.0 (02.04.2019 - 04.04.2019)
 */
@Serializable
data class Damageable(
    val maximumNoDamageTicks: Int,
    val noDamageTicks: Int,
    val lastDamage: Double?,
    @Serializable(with = EntityDamageEventSerializer::class) val lastDamageCause: EntityDamageEvent?
)