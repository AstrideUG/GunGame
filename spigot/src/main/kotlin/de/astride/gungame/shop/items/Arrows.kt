package de.astride.gungame.shop.items

import de.astride.gungame.functions.configService
import de.astride.gungame.functions.messages
import de.astride.gungame.functions.playBuySound
import de.astride.gungame.shop.ShopItemListener
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 27.03.2019 07:51.
 * Current Version: 1.0 (27.03.2019 - 15.04.2019)
 */
class Arrows(javaPlugin: JavaPlugin) : ShopItemListener(
    javaPlugin,
    ItemBuilder(config.material, damage = config.damage)
        .setName(config.name)
        .setLore(config.lore)
        .build(),
    config.delay,
    config.price
) {

    override fun Player.buy(): Boolean {
        inventory.addItem(configService.kit.kit.find { it?.type == Material.ARROW })
        playBuySound()
        messages.shop.arrows.successfully.sendTo(this)
        return true
    }

    companion object {
        private val config get() = shopItems.arrows
    }

}