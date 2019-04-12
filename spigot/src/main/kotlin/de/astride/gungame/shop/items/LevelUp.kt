package de.astride.gungame.shop.items

import de.astride.gungame.functions.messages
import de.astride.gungame.functions.playBuySound
import de.astride.gungame.functions.replace
import de.astride.gungame.kits.gunGameLevel
import de.astride.gungame.kits.kits
import de.astride.gungame.kits.upgrade
import de.astride.gungame.shop.ShopItemListener
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import kotlin.concurrent.thread

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 07:51.
 * Current Version: 1.0 (27.03.2019 - 12.04.2019)
 */
class LevelUp(javaPlugin: JavaPlugin) : ShopItemListener(
    javaPlugin,
    ItemBuilder(config.material, damage = config.damage)
        .setName(config.name)
        .setLore(config.lore)
        .build(),
    config.delay,
    config.price
) {

    override fun Player.buy() = if (kits.size == player.gunGameLevel) {
        messages.shop.maxLevel.map { it.replace("kits", kits.size) }.sendTo(this)
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
        true
    }

    companion object {
        private val config get() = shopItems.levelUp
    }

}