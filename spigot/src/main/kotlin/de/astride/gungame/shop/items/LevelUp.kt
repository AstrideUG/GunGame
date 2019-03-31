package de.astride.gungame.shop.items

import de.astride.gungame.functions.playBuySound
import de.astride.gungame.kits.Kits
import de.astride.gungame.kits.gunGameLevel
import de.astride.gungame.kits.upgrade
import de.astride.gungame.shop.ShopItemListener
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import kotlin.concurrent.thread

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 07:51.
 * Current Version: 1.0 (27.03.2019 - 31.03.2019)
 */
class LevelUp(javaPlugin: JavaPlugin) : ShopItemListener(
    javaPlugin,
    ItemBuilder(Material.DIAMOND)
        .setName("${SECONDARY}Level Up")
        .setLore("${TEXT}Erhöht dein Level um 5")
        .build(),
    60,
    100
) {

    override fun Player.buy() = if (Kits.values().size == player.gunGameLevel) {
        sendMessage("${Messages.PREFIX}${TEXT}Du hast schon das maximahle Level ($IMPORTANT${Kits.values().size}$TEXT) erreicht")
        false
    } else {
        thread {
            for (i in 0..4) {
                upgrade()
                try {
                    Thread.sleep(50)
                } catch (ex: InterruptedException) {
                    ex.printStackTrace()
                }
            }
        }
        playBuySound()
        closeInventory()
        true
    }

}