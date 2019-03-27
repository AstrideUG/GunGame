package de.astride.gungame.listener

import de.astride.gungame.event.GunGamePlayerShopHasEnoughMoneyEvent
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import org.bukkit.event.EventHandler
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 11:12.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
class MoneyListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    @EventHandler
    fun on(event: GunGamePlayerShopHasEnoughMoneyEvent) {
        event.cancel()//TODO add vault support
    }

}