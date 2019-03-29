package de.astride.gungame.event

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 10:53.
 * Current Version: 1.0 (27.03.2019 - 29.03.2019)
 */
class GunGamePlayerShopHasEnoughMoneyEvent(who: Player, val price: Double) : PlayerEvent(who), Cancellable {

    private var isCancelled = false

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }

    override fun isCancelled(): Boolean = isCancelled

    override fun getHandlers(): HandlerList = handlerList

    companion object {
        @JvmStatic //Important for Bukkit due to the Java ByteCode
        val handlerList = HandlerList()
    }

}
