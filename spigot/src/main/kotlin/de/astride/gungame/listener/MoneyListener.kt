package de.astride.gungame.listener

import de.astride.gungame.event.GunGamePlayerShopHasEnoughMoneyEvent
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 11:12.
 * Current Version: 1.0 (27.03.2019 - 27.03.2019)
 */
class MoneyListener(javaPlugin: JavaPlugin) : Listener(javaPlugin) {

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 29.03.2019 13:29.
     * Current Version: 1.0 (29.03.2019 - 29.03.2019)
     */
    private val economy get() = Bukkit.getServicesManager().getRegistration(Economy::class.java)?.provider!!

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 29.03.2019 13:29.
     * Current Version: 1.0 (29.03.2019 - 29.03.2019)
     */
    @EventHandler
    fun onGunGamePlayerShopHasEnoughMoneyEvent(event: GunGamePlayerShopHasEnoughMoneyEvent) {
        if (!economy.has(event.player, event.price)) event.cancel() else event.cancel(false)
    }

}