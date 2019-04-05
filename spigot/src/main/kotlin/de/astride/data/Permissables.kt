package de.astride.data

import kotlinx.serialization.Serializable
import org.bukkit.permissions.PermissionAttachmentInfo

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 02:01.
 * Current Version: 1.0 (02.04.2019 - 05.04.2019)
 */
@Serializable
data class Permissables(
    val isOp: Boolean,
    @Serializable(with = PermissionAttachmentInfoSerializer::class) val effectivePermissions: Set<PermissionAttachmentInfo>
)