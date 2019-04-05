package de.astride.data

import kotlinx.serialization.Serializable
import java.net.InetSocketAddress
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 02:18.
 * Current Version: 1.0 (02.04.2019 - 04.04.2019)
 */
@Serializable
data class MetaData(
    @Serializable(with = InetSocketAddressSerializer::class) val address: InetSocketAddress,
    @Serializable(with = InetSocketAddressSerializer::class) val rawAddress: InetSocketAddress,
    @Serializable(with = UUIDSerializer::class) val uniqueId: UUID,
    val hasPlayedBefore: Boolean,
    val lastPlayed: Long,
    val firstPlayed: Long,
    val locale: String,
    val entityId: Int,
    val listeningPluginChannels: Set<String>
)