package de.astride.gungame.setup

import de.astride.gungame.functions.configService
import de.astride.gungame.functions.javaPlugin
import de.astride.gungame.setup.page.ShopPage
import de.astride.inventory.pages.Page
import de.astride.inventory.pages.setItems
import de.astride.location.*
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.inverntory.InventoryBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.SkullItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Items
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.RED
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.metadata.Metadatable

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 07.05.2019 12:28.
 * Current Version: 1.0 (07.05.2019 - 07.05.2019)
 */

var Metadatable.editID: Int?
    get() = getMetadata("edit-id").firstOrNull()?.asInt()
    set(value) {
        removeMetadata("edit-id", javaPlugin)
        setMetadata("edit-id", FixedMetadataValue(javaPlugin, value))
    }

var Metadatable.editType: String?
    get() = getMetadata("edit-type").firstOrNull()?.asString()
    set(value) {
        removeMetadata("edit-type", javaPlugin)
        setMetadata("edit-type", FixedMetadataValue(javaPlugin, value))
    }

var Metadatable.page: Int
    get() = getMetadata("page").firstOrNull()?.asInt() ?: 0
    set(value) {
        removeMetadata("page", javaPlugin)
        setMetadata("page", FixedMetadataValue(javaPlugin, value))
    }

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 07.05.2019 12:30.
 * Current Version: 1.0 (07.05.2019 - 07.05.2019)
 */
object Setup {

    val backItem: ItemStack = ItemBuilder(Items.LEAVE.itemStack.clone()).setName("${SECONDARY}Zurück").build()

    val all: Inventory = InventoryBuilder(InventoryType.HOPPER, "${SECONDARY}GunGame Setup All")
        .setDesign()
        .setItem(1, ItemBuilder(Material.MAP).setName("${SECONDARY}Maps").addAllItemFlags().build())
        .setItem(3, ItemBuilder(Items.CHEST.itemStack).setName("${SECONDARY}Shops").build())
        .build()
    val maps: Inventory = InventoryBuilder(5 * 9, "${SECONDARY}GunGame Setup Maps").generate()
    val shops: Inventory = InventoryBuilder(5 * 9, "${SECONDARY}GunGame Setup Shops").generate()

    fun generateShopsEdit(
        location: Location,
        world: Boolean = true,
        yawAndPitch: Boolean = true
    ): Inventory =
        InventoryBuilder(5 * 9, "${SECONDARY}GunGame Setup Shops Edit").generateEdit(location, world, yawAndPitch)

    fun generateShopDisplayItem(id: Int, location: Location): ItemStack = ItemBuilder(Items.CHEST.itemStack.clone())
        .setName("${SECONDARY}Number $id")
        .setLore(
            "",
            "${TEXT}Location:",
            "$TEXT    World: $IMPORTANT${location.world}",
            "$TEXT    X: $IMPORTANT${location.x}",
            "$TEXT    Y: $IMPORTANT${location.y}",
            "$TEXT    Z: $IMPORTANT${location.z}",
            "",
            "${GREEN}Klicken zum editieren",
            "${GREEN}Shift links klicken zum telportieren",
            "${RED}Shift rechts klicken zum löschen",
            ""
        )
        .build()

    private fun InventoryBuilder.generateEdit(
        location: Location,
        world: Boolean = true,
        yawAndPitch: Boolean = false
    ): Inventory = setDesign()
        .setRange(ItemStack(Material.AIR), 19, 26)
        .setItem(20, ItemBuilder(Material.BANNER, damage = 1).setName("${TEXT}X: $IMPORTANT${location.x}").build())
        .setItem(22, ItemBuilder(Material.BANNER, damage = 10).setName("${TEXT}Y: $IMPORTANT${location.y}").build())
        .setItem(24, ItemBuilder(Material.BANNER, damage = 4).setName("${TEXT}Z: $IMPORTANT${location.z}").build())
        .apply {
            if (world) setItem(
                40,
                ItemBuilder(Material.EMPTY_MAP).setName("${TEXT}World: $IMPORTANT${location.world}").build()
            )
            if (yawAndPitch) {
                setItem(
                    18,
                    SkullItemBuilder().setOwner("MHF_ArrowLeft").setName("${TEXT}Yaw: $IMPORTANT${location.yaw}").build()
                )
                setItem(
                    26,
                    SkullItemBuilder().setOwner("MHF_ArrowRight").setName("${TEXT}Pitch: $IMPORTANT${location.pitch}").build()
                )
            }
        }
        .setItem(44, backItem)
        .build()

    private fun InventoryBuilder.generate(): Inventory = setDesign().apply {
        val base = ItemBuilder(Material.PAPER).addItemFlags(ItemFlag.HIDE_ENCHANTS)
        setItem(38, base.setName("${SECONDARY}Vorherige Seite").build())
        setItem(42, base.setName("${SECONDARY}Nächste Seite").build())
    }
        .setItem(40, ItemBuilder(Material.REDSTONE).setName("${SECONDARY}Erstelle einen neuen").build())
        .setItem(44, backItem)
        .setRange(ItemStack(Material.AIR), 19, 26)
        .build()

}

fun HumanEntity.openShop(pageID: Int) {

    val shops = Setup.shops
    val inventory = InventoryBuilder(shops.size, shops.title).build().apply {
        contents = shops.contents
    }

    val pages = mutableListOf<Page>().apply {
        for (i in 0 until configService.shops.locations.size / 7 + 1) add(ShopPage(i))
        if (isEmpty()) add(ShopPage(0)) else if (configService.shops.locations.size % 7 == 0) removeAt(lastIndex)
    }
    val page = try {
        pages[pageID].apply { page = pageID }
    } catch (ex: IndexOutOfBoundsException) {
        (this as? Player)?.playSound(location, Sound.ANVIL_LAND, 1f, 1f)
        return
    }

    page.setItems(inventory)
    openInventory(inventory)

}
