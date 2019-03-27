/*
 * © Copyright - Lars Artmann | LartyHD 2018.
 */
package de.astride.gungame.shop

import de.astride.gungame.event.GunGamePlayerShopHasEnoughMoneyEvent
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.SECONDARY
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.TEXT
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Utils
import net.darkdevelopers.darkbedrock.darkness.universal.functions.call
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.02.2018 02:33.
 * Current Version: 1.0 (19.02.2018 - 27.03.2019)
 */
abstract class ShopItemListener protected constructor(
    javaPlugin: JavaPlugin,
    val itemStack: ItemStack,
    private val delay: Long,
    private val price: Int
) : Listener(javaPlugin) {

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 27.03.2019 08:44.
     * Current Version: 1.0 (27.03.2019 - 27.03.2019)
     */
    init {

        itemStack.itemMeta = itemStack.itemMeta.apply {
            lore.add(0, "${TEXT}Kaufe ihn dir für ${Colors.PRIMARY}$price ${Colors.IMPORTANT}Coins")
        }

    }

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 27.03.2019 07:20.
     * Current Version: 1.0 (27.03.2019 - 27.03.2019)
     */
    var Player.lastItemUse
        get() = getMetadata("lastItemUse-$displayName").firstOrNull()?.asLong() ?: 0
        set(value) = setMetadata("lastItemUse-$displayName", FixedMetadataValue(javaPlugin, value))

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 27.03.2019 08:22.
     * Current Version: 1.0 (27.03.2019 - 27.03.2019)
     */
    abstract fun Player.buy()

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 27.03.2019 08:22.
     * Current Version: 1.0 (27.03.2019 - 27.03.2019)
     */
    fun checkedBuy(player: Player) {
        if (!player.delayed() && player.enoughMoney()) player.buy()
    }

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 27.03.2019 08:18.
     * Current Version: 1.0 (27.03.2019 - 27.03.2019)
     */
    private fun Player.enoughMoney(): Boolean =
        !GunGamePlayerShopHasEnoughMoneyEvent(this, price.toDouble()).call().isCancelled //#Broke

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 27.03.2019 08:19.
     * Current Version: 1.0 (27.03.2019 - 27.03.2019)
     */
    private fun Player.delayed(): Boolean = if (lastItemUse < System.currentTimeMillis() + delay) {
        lastItemUse = System.currentTimeMillis()
        false
    } else {
        val time = Utils.getTime(lastItemUse - System.currentTimeMillis())
        "${Messages.PREFIX}${TEXT}Du kannst $SECONDARY$displayName$TEXT in $time wieder kaufen".sendTo(this)
        true
    }

}
