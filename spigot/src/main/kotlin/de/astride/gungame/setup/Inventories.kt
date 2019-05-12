package de.astride.gungame.setup

import de.astride.gungame.functions.configService
import de.astride.gungame.functions.javaPlugin
import de.astride.gungame.setup.page.ShopPage
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.inventory.InventoryBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.SkullItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.inventory.pages.Page
import net.darkdevelopers.darkbedrock.darkness.spigot.inventory.pages.setItems
import net.darkdevelopers.darkbedrock.darkness.spigot.location.*
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.*
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.Items
import net.darkdevelopers.darkbedrock.darkness.spigot.utils.map.GameMap
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
 * Current Version: 1.0 (07.05.2019 - 12.05.2019)
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

    fun generateMapsEdit(
        gameMap: GameMap
    ): Inventory =
        InventoryBuilder(5 * 9, "${SECONDARY}GunGame Setup Maps Edit").generateMapsEdit(gameMap)

    fun generateShopsEdit(
        location: Location,
        world: Boolean = true,
        yawAndPitch: Boolean = true
    ): Inventory =
        InventoryBuilder(5 * 9, "${SECONDARY}GunGame Setup Shops Edit").generateShopsEdit(location, world, yawAndPitch)

    fun generateMapDisplayItem(id: Int, gameMap: GameMap): ItemStack = ItemBuilder(ItemStack(Material.MAP))
        .setName("${SECONDARY}Number $id")
        .setLore(
            "",
            "${TEXT}Name: $IMPORTANT${gameMap.name}",
            "",
            "${GREEN}Klicken zum editieren",
            "${GREEN}Shift links klicken zum telportieren",
            "${RED}Shift rechts klicken zum löschen",
            ""
        )
        .addAllItemFlags()
        .build()

    fun generateShopDisplayItem(id: Int, location: Location): ItemStack = ItemBuilder(Items.CHEST.itemStack.clone())
        .setName("${SECONDARY}Number $id")
        .setLore(
            "",
            "${TEXT}Location:",
            "$TEXT    World: $IMPORTANT${location.world}",
            "$TEXT    X: $IMPORTANT${location.x}",
            "$TEXT    Y: $IMPORTANT${location.y}",
            "$TEXT    Z: $IMPORTANT${location.z}",
            "$TEXT    Yaw: $IMPORTANT${location.yawOr0}",
            "$TEXT    Pitch: $IMPORTANT${location.pitchOr0}",
            "",
            "${GREEN}Klicken zum editieren",
            "${GREEN}Shift links klicken zum telportieren",
            "${RED}Shift rechts klicken zum löschen",
            ""
        )
        .build()

    private fun InventoryBuilder.generateMapsEdit(
        gameMap: GameMap
    ): Inventory = setDesign()
        .setRange(ItemStack(Material.AIR), 19, 26)
        .setItem(
            19, ItemBuilder(Material.IRON_SWORD).setName("${SECONDARY}Region").setLore(
                mutableListOf("").apply {
                    val region = gameMap.region
                    if (region == null) add("Region is null") else addAll(
                        listOf(
                            "${TEXT}World: $IMPORTANT${region.world}",
                            "",
                            "${TEXT}Min:",
                            "$TEXT    X: $IMPORTANT${region.min.x}",
                            "$TEXT    Y: $IMPORTANT${region.min.y}",
                            "$TEXT    Z: $IMPORTANT${region.min.z}",
                            "",
                            "${TEXT}Max:",
                            "$TEXT    X: $IMPORTANT${region.max.x}",
                            "$TEXT    Y: $IMPORTANT${region.max.y}",
                            "$TEXT    Z: $IMPORTANT${region.max.z}",
                            ""
                        )
                    )
                }
            ).build()
        )
        .setItem(
            21, ItemBuilder(Material.PAPER).setName("${SECONDARY}Name")
                .setLore("", "${TEXT}Name: $IMPORTANT${gameMap.name}")
                .build()
        )
        .setItem(
            23, ItemBuilder(Material.EMPTY_MAP).setName("${SECONDARY}Spawn")
                .setLore(
                    "",
                    "${TEXT}Location:",
                    "$TEXT    World: $IMPORTANT${gameMap.spawn.world}",
                    "$TEXT    X: $IMPORTANT${gameMap.spawn.x}",
                    "$TEXT    Y: $IMPORTANT${gameMap.spawn.y}",
                    "$TEXT    Z: $IMPORTANT${gameMap.spawn.z}",
                    ""
                )
                .build()
        )
        .setItem(
            23, ItemBuilder(Material.ARMOR_STAND).setName("${SECONDARY}Hologram")
                .setLore(
                    mutableListOf("").apply {
                        val hologram = gameMap.hologram
                        if (hologram == null) add("Hologram is null") else addAll(
                            listOf(
                                "${TEXT}Location:",
                                "$TEXT    World: $IMPORTANT${hologram.world}",
                                "$TEXT    X: $IMPORTANT${hologram.x}",
                                "$TEXT    Y: $IMPORTANT${hologram.y}",
                                "$TEXT    Z: $IMPORTANT${hologram.z}",
                                ""
                            )
                        )
                    }

                )
                .build()
        )
        .setItem(44, backItem)
        .build()

    private fun InventoryBuilder.generateShopsEdit(
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

fun HumanEntity.openShops(pageID: Int, rawInventory: Inventory = Setup.shops) {

    val inventory = InventoryBuilder(rawInventory.size, rawInventory.title).build().apply {
        contents = rawInventory.contents
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

fun HumanEntity.openMaps(pageID: Int): Unit = open(pageID, Setup.maps, configService.maps.maps.size) { ShopPage(it) }
fun HumanEntity.openShops(pageID: Int): Unit =
    open(pageID, Setup.shops, configService.shops.locations.size) { ShopPage(it) }

fun HumanEntity.open(pageID: Int, rawInventory: Inventory, listSize: Int, pageGetter: (Int) -> Page) {

    val inventory = InventoryBuilder(rawInventory.size, rawInventory.title).build().apply {
        contents = rawInventory.contents
    }

    val pages = mutableListOf<Page>().apply {
        for (i in 0 until listSize / 7 + 1) add(pageGetter(i))
        if (isEmpty()) add(pageGetter(0)) else if (listSize % 7 == 0) removeAt(lastIndex)
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
