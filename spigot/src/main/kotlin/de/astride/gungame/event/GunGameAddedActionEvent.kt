package de.astride.gungame.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 11.04.2019 03:58.
 * Current Version: 1.0 (11.04.2019 - 11.04.2019)
 */
class GunGameAddedActionEvent(val uuid: UUID) : Event() {

    override fun getHandlers(): HandlerList = handlerList

    companion object {
        @JvmStatic //Important for Bukkit due to the Java ByteCode
        val handlerList = HandlerList()
    }

}
