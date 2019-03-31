/*
 * © Copyright - Lars Artmann | LartyHD 2018.
 */
package de.astride.gungame.shop

import de.astride.gungame.event.GunGamePlayerShopHasEnoughMoneyEvent
import de.astride.gungame.functions.actions
import de.astride.gungame.stats.Action
import net.darkdevelopers.darkbedrock.darkness.spigot.functions.sendTo
import net.darkdevelopers.darkbedrock.darkness.spigot.listener.Listener
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Messages
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Utils
import net.darkdevelopers.darkbedrock.darkness.universal.functions.call
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.TimeUnit

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 19.02.2018 02:33.
 * Current Version: 1.0 (19.02.2018 - 31.03.2019)
 */
abstract class ShopItemListener protected constructor(
    javaPlugin: JavaPlugin,
    val itemStack: ItemStack,
    private val delay: Long,
    private val price: Int
) : Listener(javaPlugin) {

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 30.03.2019 11:06.
     * Current Version: 1.0 (30.03.2019 - 30.03.2019)
     */
    private val metadataKey = "lastItemUse-${javaClass.simpleName}"

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 27.03.2019 07:20.
     * Current Version: 1.0 (27.03.2019 - 29.03.2019)
     */
    private var Player.lastItemUse: Long
        get() = getMetadata(metadataKey).firstOrNull()?.asLong() ?: 0
        set(value) = setMetadata(metadataKey, FixedMetadataValue(javaPlugin, value))

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 27.03.2019 08:44.
     * Current Version: 1.0 (27.03.2019 - 30.03.2019)
     */
    init {

        itemStack.itemMeta = itemStack.itemMeta.apply {
            lore = lore.toMutableList().apply { add(0, "${TEXT}Kaufe ihn dir für $PRIMARY$price ${IMPORTANT}Coins") }
        }

    }

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 27.03.2019 08:22.
     * Current Version: 1.0 (27.03.2019 - 27.03.2019)
     */
    abstract fun Player.buy()

    /**
     * @author Lars Artmann | LartyHD
     * Created by Lars Artmann | LartyHD on 27.03.2019 08:22.
     * Current Version: 1.0 (27.03.2019 - 31.03.2019)
     */
    fun checkedBuy(player: Player) = player.run {
        if (delayed() || !enoughMoney()) return@run
        uniqueId.actions += Action(this@ShopItemListener.javaClass.simpleName, mapOf("player" to this))
        buy()
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
     * Current Version: 1.0 (27.03.2019 - 30.03.2019)
     */
    private fun Player.delayed(): Boolean {
        val l = (lastItemUse + TimeUnit.SECONDS.toMillis(delay)) - System.currentTimeMillis()
        return if (l <= 0) {
            lastItemUse = System.currentTimeMillis()
            false
        } else {
            val time = Utils.getTime(l / 1000)
            "${Messages.PREFIX}${TEXT}Du kannst $SECONDARY${itemStack.itemMeta.displayName}$TEXT in $time wieder kaufen"
                .sendTo(this)
            true
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ShopItemListener) return false

        if (itemStack != other.itemStack) return false
        if (delay != other.delay) return false
        if (price != other.price) return false
        if (metadataKey != other.metadataKey) return false

        return true
    }

    override fun hashCode(): Int {
        var result = itemStack.hashCode()
        result = 31 * result + delay.hashCode()
        result = 31 * result + price
        result = 31 * result + metadataKey.hashCode()
        return result
    }

    override fun toString(): String =
        "${javaClass.simpleName}(itemStack=$itemStack, delay=$delay, price=$price, metadataKey='$metadataKey')"


}
