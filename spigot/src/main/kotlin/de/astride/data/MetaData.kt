package de.astride.data

import java.net.InetSocketAddress
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 02:18.
 * Current Version: 1.0 (02.04.2019 - 02.04.2019)
 */
data class MetaData(
    val address: InetSocketAddress,
    val rawAddress: InetSocketAddress,
    val uniqueId: UUID,
    val hasPlayedBefore: Boolean,
    val lastPlayed: Long,
    val firstPlayed: Long,
    val locale: String,
    val entityId: Int,
    val listeningPluginChannels: Set<String>
)