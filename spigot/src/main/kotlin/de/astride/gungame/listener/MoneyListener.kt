package de.astride.gungame.listener

import de.astride.gungame.event.GunGameAddedActionEvent
import de.astride.gungame.event.GunGamePlayerShopHasEnoughMoneyEvent
import de.astride.gungame.functions.configService
import de.astride.gungame.functions.messages
import de.astride.gungame.functions.replace
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.cancel
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.toOfflinePlayer
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.event.EventHandler
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 11:12.
 * Current Version: 1.0 (27.03.2019 - 11.04.2019)
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
     * Current Version: 1.0 (29.03.2019 - 11.04.2019)
     */
    @EventHandler
    fun onGunGamePlayerShopHasEnoughMoneyEvent(event: GunGamePlayerShopHasEnoughMoneyEvent) {
        val price = event.price
        val player = event.player
        val transform: (String?) -> String? = {
            val balance = economy.getBalance(player)
            it.replace("price", price).replace("balance", balance).replace("difference", price - balance)
        }
        if (economy.has(player, price)) {
            event.cancel(false)
            economy.withdrawPlayer(player, price)
            messages.shop.money.successfully.map(transform).sendTo(player)
        } else {
            event.cancel()
            messages.shop.money.failed.map(transform).sendTo(player)
        }
    }

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 11.04.2019 04:08.
     * Current Version: 1.0 (11.04.2019 - 12.04.2019)
     */
    @EventHandler
    fun onGunGameAddedActionEvent(event: GunGameAddedActionEvent) {

        val offlinePlayer = event.uuid.toOfflinePlayer()
        if (offlinePlayer != null) {
            val reward = configService.config.rewards[event.action.key]
            economy.depositPlayer(offlinePlayer, reward ?: return)
            messages.addAction.map {
                it
                    .replace("uuid", event.uuid.toString())
                    .replace("name", offlinePlayer.name)
                    .replace("reward", reward)
                    .replace("action-key", event.action.key)
                    .replace("action-timestamp", event.action.timestamp)
                    .replace("action-uuid", event.action.uuid)
            }.sendTo(offlinePlayer as? CommandSender ?: return)
        } else javaPlugin.logger.warning("Can't transfer '${event.uuid}' to a OfflinePlayer on GunGameAddedActionEvent")

    }

}