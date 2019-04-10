package de.astride.data

import kotlinx.serialization.Serializable

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 01:54.
 * Current Version: 1.0 (02.04.2019 - 10.04.2019)
 *
 * Bukkit:
 * level = expLevel
 * exp = exp
 * expToLevel = this.expLevel >= 30 ? 112 + (this.expLevel - 30) * 9 : (this.expLevel >= 15 ? 37 + (this.expLevel - 15) * 5 : 7 + this.expLevel * 2);
 * totalExperience = expTotal
 */
@Serializable
data class DataExp(
    val level: Int,
    val exp: Float,
    val expToLevel: Int,
    val totalExperience: Int
)