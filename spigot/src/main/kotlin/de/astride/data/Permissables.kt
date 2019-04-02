package de.astride.data

import org.bukkit.permissions.PermissionAttachmentInfo

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 02:01.
 * Current Version: 1.0 (02.04.2019 - 02.04.2019)
 */
data class Permissables(
    val isOp: Boolean,
    val effectivePermissions: Set<PermissionAttachmentInfo>
)