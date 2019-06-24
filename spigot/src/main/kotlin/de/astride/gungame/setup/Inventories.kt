package de.astride.gungame.setup

import de.astride.gungame.functions.configService
import de.astride.gungame.functions.javaPlugin
import de.astride.gungame.setup.page.MapsPage
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
import org.bukkit.entity.Entity
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

var Metadatable.anvilType: String?
    get() = getMetadata("anvil-type").firstOrNull()?.asString()
    set(value) {
        removeMetadata("anvil-type", javaPlugin)
        setMetadata("anvil-type", FixedMetadataValue(javaPlugin, value))
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
 * Current Version: 1.0 (07.05.2019 - 17.05.2019)
 */
object Setup {

    private val gungamePrefix: String = "${SECONDARY}GunGame Setup "
    private val mapsName: String = "${SECONDARY}Maps"
    private val shopsName: String = "${SECONDARY}Shops"
    private const val editSuffix: String = " Edit"
    val mapsInventoryName: String = "$gungamePrefix$mapsName"
    val shopsInventoryName: String = "$gungamePrefix$shopsName"
    val mapsInventoryEditName = "$mapsInventoryName$editSuffix"
    val shopsInventoryEditName = "$shopsInventoryName$editSuffix"

    val backItem: ItemStack = ItemBuilder(Items.LEAVE.itemStack.clone()).setName("${SECONDARY}Zurück").build()

    val all: Inventory = InventoryBuilder(InventoryType.HOPPER, "${gungamePrefix}All")
        .setDesign()
        .setItem(1, ItemBuilder(Material.MAP).setName(mapsName).addAllItemFlags().build())
        .setItem(3, ItemBuilder(Items.CHEST.itemStack).setName(shopsName).build())
        .build()
    val maps: Inventory = InventoryBuilder(5 * 9, mapsInventoryName).generate()
    val shops: Inventory = InventoryBuilder(5 * 9, shopsInventoryName).generate()

    fun generateMapsEdit(
        gameMap: GameMap
    ): Inventory =
        InventoryBuilder(5 * 9, mapsInventoryEditName).generateMapsEdit(gameMap)

    fun generateMapsEditLocation(
        gameMap: GameMap,
        entity: Entity,
        type: String
    ): Inventory = InventoryBuilder(5 * 9, mapsInventoryEditName + type).generateMapsEditLocation(
        if (type == "hologram") gameMap.hologram ?: entity.location.toLocation() else gameMap.spawn
    )

    fun generateLocationEdit(
        location: ReadOnlyLocation,
        world: Boolean = true,
        yawAndPitch: Boolean = true
    ): Inventory =
        InventoryBuilder(5 * 9, shopsInventoryEditName).generateLocationEdit(location, world, yawAndPitch)

    fun generateMapDisplayItem(id: Int, gameMap: GameMap): ItemStack = ItemBuilder(ItemStack(Material.MAP))
        .setName("${SECONDARY}Number $id")
        .setLore(
            "",
            "${TEXT}Name: $IMPORTANT${gameMap.name}",
            "",
            "${GREEN}Klicken zum editieren",
            "${RED}Shift rechts klicken zum löschen",
            ""
        )
        .addAllItemFlags()
        .build()

    fun generateShopDisplayItem(id: Int, location: ReadOnlyLocation): ItemStack =
        ItemBuilder(Items.CHEST.itemStack.clone())
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
                "${GREEN}Links klicken zum editieren",
                "${GREEN}Rechts klicken um es hier her zu verschieben",
                "",
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
                    addAll(
                        if (region == null) listOf(
                            "${TEXT}Region ist nicht gesetzt",
                            "",
                            "${GREEN}Kicken zum erstellen (hier)",
                            ""
                        ) else listOf(
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
                            "",
                            "${GREEN}Links klicken um min hier her zu verschieben",
                            "${GREEN}Rechts klicken um max hier her zu verschieben",
                            "",
                            "${RED}Shift rechts klicken zum löschen",
                            ""
                        )
                    )
                }
            ).build()
        ).setItem(
            21, ItemBuilder(Material.PAPER).setName("${SECONDARY}Name").setLore(
                "",
                "${TEXT}Name: $IMPORTANT${gameMap.name}",
                "",
                "${GREEN}Klicken zum editieren",
                ""
            ).build()
        ).setItem(
            23, ItemBuilder(Material.EMPTY_MAP).setName("${SECONDARY}Spawn").setLore(
                "",
                "${TEXT}Location:",
                "$TEXT    World: $IMPORTANT${gameMap.spawn.world}",
                "$TEXT    X: $IMPORTANT${gameMap.spawn.x}",
                "$TEXT    Y: $IMPORTANT${gameMap.spawn.y}",
                "$TEXT    Z: $IMPORTANT${gameMap.spawn.z}",
                "",
                "${GREEN}Klicken um es hier her zu verschieben",
                ""
            ).build()
        ).setItem(
            25, ItemBuilder(Material.ARMOR_STAND).setName("${SECONDARY}Hologram")
                .setLore(
                    mutableListOf("").apply {
                        val hologram = gameMap.hologram
                        addAll(
                            if (hologram == null) listOf(
                                "${TEXT}Hologram ist nicht gesetzt",
                                "",
                                "${GREEN}Klicken zum erstellen",
                                ""
                            ) else listOf(
                                "${TEXT}Location:",
                                "$TEXT    World: $IMPORTANT${hologram.world}",
                                "$TEXT    X: $IMPORTANT${hologram.x}",
                                "$TEXT    Y: $IMPORTANT${hologram.y}",
                                "$TEXT    Z: $IMPORTANT${hologram.z}",
                                "",
                                "${GREEN}Klicken um es hier her zu verschieben",
                                "",
                                "${RED}Shift rechts klicken zum löschen",
                                ""
                            )
                        )
                    }
                ).build()
        ).setItem(
            40, ItemBuilder(Material.MAP).addAllItemFlags().setName("${SECONDARY}Worlds").setLore(
                "",
                "${TEXT}World: $IMPORTANT${gameMap.spawn.world}",
                "",
                "${GREEN}Klicken zum editieren",
                ""
            ).build()
        )
        .setItem(44, backItem)
        .build()

    private fun InventoryBuilder.generateMapsEditLocation(
        location: ReadOnlyLocation
    ): Inventory = generateLocationEdit(location, world = false, yawAndPitch = false)

    private fun InventoryBuilder.generateLocationEdit(
        location: ReadOnlyLocation,
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

fun HumanEntity.openMaps(pageID: Int): Unit = open(pageID, Setup.maps, configService.maps.maps.size) { MapsPage(it) }
fun HumanEntity.openShops(pageID: Int): Unit =
    open(pageID, Setup.shops, configService.shops.locations.size) { ShopPage(it) }

fun HumanEntity.open(pageID: Int, rawInventory: Inventory, listSize: Int, pageGetter: (Int) -> Page) {

    val inventory = InventoryBuilder(rawInventory.size, rawInventory.title).build().apply {
        contents = rawInventory.contents
    }

    val pages = mutableListOf<Page>().apply {
        for (i in 0 until listSize / 7 + 1) add(pageGetter(i))
    }

    if (listSize % 7 == 0 && pages.size != 1) pages.removeAt(pages.lastIndex)

    val page = try {
        pages[pageID].apply { page = pageID }
    } catch (ex: IndexOutOfBoundsException) {
        (this as? Player)?.playSound(location, Sound.ANVIL_LAND, 1f, 1f)
        return
    }

    page.setItems(inventory)
    openInventory(inventory)

}
