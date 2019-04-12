/*
 * © Copyright - Lars Artmann | LartyHD 2018.
 */
package de.astride.gungame.kits

import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.SECONDARY
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 20.08.2017 18:19.
 * Current Version: 1.0 (20.08.2017 - 12.04.2019)
 */
enum class DefaultKits(
    val helmet: ItemStack?,
    val chestplate: ItemStack?,
    val leggins: ItemStack?,
    val boots: ItemStack?,
    val item: ItemStack
) {
    /*BASIC*/
    KIT1(null, null, null, null, build(Material.WOOD_AXE)),
    KIT2(null, null, null, null, build(Material.WOOD_SWORD)),
    /*LEATHER*/
    KIT3(
        null,
        build(Material.LEATHER_CHESTPLATE), null, null,
        build(Material.WOOD_SWORD)
    ),
    KIT4(
        null,
        build(Material.LEATHER_CHESTPLATE),
        null,
        build(Material.LEATHER_BOOTS),
        build(Material.WOOD_SWORD)
    ),
    KIT5(
        build(Material.LEATHER_HELMET),
        build(Material.LEATHER_CHESTPLATE),
        null,
        build(Material.LEATHER_BOOTS),
        build(Material.WOOD_SWORD)
    ),
    KIT6(
        build(Material.LEATHER_HELMET),
        build(Material.LEATHER_CHESTPLATE),
        build(Material.LEATHER_LEGGINGS),
        build(Material.LEATHER_BOOTS),
        build(Material.WOOD_SWORD)
    ),
    KIT7(
        build(Material.LEATHER_HELMET),
        build(Material.LEATHER_CHESTPLATE),
        build(Material.LEATHER_LEGGINGS),
        build(Material.LEATHER_BOOTS),
        build(Material.WOOD_AXE, 1)
    ),
    KIT8(
        build(Material.LEATHER_HELMET),
        build(Material.LEATHER_CHESTPLATE, 1),
        build(Material.LEATHER_LEGGINGS),
        build(Material.LEATHER_BOOTS),
        build(Material.WOOD_AXE, 1)
    ),
    KIT9(
        build(Material.LEATHER_HELMET),
        build(Material.LEATHER_CHESTPLATE, 1),
        build(Material.LEATHER_LEGGINGS),
        build(Material.LEATHER_BOOTS, 1),
        build(Material.WOOD_AXE, 1)
    ),
    KIT10(
        build(Material.LEATHER_HELMET, 1),
        build(Material.LEATHER_CHESTPLATE, 1),
        build(Material.LEATHER_LEGGINGS),
        build(Material.LEATHER_BOOTS, 1),
        build(Material.WOOD_AXE, 1)
    ),
    KIT11(
        build(Material.LEATHER_HELMET, 1),
        build(Material.LEATHER_CHESTPLATE, 1),
        build(Material.LEATHER_LEGGINGS, 1),
        build(Material.LEATHER_BOOTS, 1),
        build(Material.WOOD_AXE, 1)
    ),
    KIT12(
        build(Material.LEATHER_HELMET, 1),
        build(Material.LEATHER_CHESTPLATE, 1),
        build(Material.LEATHER_LEGGINGS, 1),
        build(Material.LEATHER_BOOTS, 1),
        build(Material.WOOD_SWORD, 1)
    ),
    /*CHAINMAIL*/
    KIT13(
        build(Material.LEATHER_HELMET, 1),
        build(Material.CHAINMAIL_CHESTPLATE),
        build(Material.LEATHER_LEGGINGS, 1),
        build(Material.LEATHER_BOOTS, 1),
        build(Material.STONE_SWORD)
    ),
    KIT14(
        build(Material.LEATHER_HELMET, 1),
        build(Material.CHAINMAIL_CHESTPLATE),
        build(Material.LEATHER_LEGGINGS, 1),
        build(Material.CHAINMAIL_BOOTS),
        build(Material.STONE_SWORD)
    ),
    KIT15(
        build(Material.CHAINMAIL_HELMET),
        build(Material.CHAINMAIL_CHESTPLATE),
        build(Material.CHAINMAIL_LEGGINGS),
        build(Material.CHAINMAIL_BOOTS),
        build(Material.STONE_SWORD)
    ),
    KIT16(
        build(Material.CHAINMAIL_HELMET),
        build(Material.CHAINMAIL_CHESTPLATE),
        build(Material.CHAINMAIL_LEGGINGS),
        build(Material.CHAINMAIL_BOOTS),
        build(Material.STONE_SWORD)
    ),
    KIT17(
        build(Material.CHAINMAIL_HELMET),
        build(Material.CHAINMAIL_CHESTPLATE),
        build(Material.CHAINMAIL_LEGGINGS),
        build(Material.CHAINMAIL_BOOTS),
        build(Material.STONE_AXE, 1)
    ),
    KIT18(
        build(Material.CHAINMAIL_HELMET),
        build(Material.CHAINMAIL_CHESTPLATE, 1),
        build(Material.CHAINMAIL_LEGGINGS),
        build(Material.CHAINMAIL_BOOTS),
        build(Material.STONE_AXE, 1)
    ),
    KIT19(
        build(Material.CHAINMAIL_HELMET),
        build(Material.CHAINMAIL_CHESTPLATE, 1),
        build(Material.CHAINMAIL_LEGGINGS),
        build(Material.CHAINMAIL_BOOTS, 1),
        build(Material.STONE_AXE, 1)
    ),
    KIT20(
        build(Material.CHAINMAIL_HELMET, 1),
        build(Material.CHAINMAIL_CHESTPLATE, 1),
        build(Material.CHAINMAIL_LEGGINGS),
        build(Material.CHAINMAIL_BOOTS, 1),
        build(Material.STONE_AXE, 1)
    ),
    KIT21(
        build(Material.CHAINMAIL_HELMET, 1),
        build(Material.CHAINMAIL_CHESTPLATE, 1),
        build(Material.CHAINMAIL_LEGGINGS, 1),
        build(Material.CHAINMAIL_BOOTS, 1),
        build(Material.STONE_AXE, 1)
    ),
    KIT22(
        build(Material.CHAINMAIL_HELMET, 1),
        build(Material.CHAINMAIL_CHESTPLATE, 1),
        build(Material.CHAINMAIL_LEGGINGS, 1),
        build(Material.CHAINMAIL_BOOTS, 1),
        build(Material.STONE_SWORD, 1)
    ),
    /*IRON*/
    KIT23(
        build(Material.CHAINMAIL_HELMET, 1),
        build(Material.IRON_CHESTPLATE),
        build(Material.CHAINMAIL_LEGGINGS, 1),
        build(Material.CHAINMAIL_BOOTS, 1),
        build(Material.IRON_SWORD)
    ),
    KIT24(
        build(Material.CHAINMAIL_HELMET, 1),
        build(Material.IRON_CHESTPLATE),
        build(Material.CHAINMAIL_LEGGINGS, 1),
        build(Material.IRON_BOOTS),
        build(Material.IRON_SWORD)
    ),
    KIT25(
        build(Material.IRON_HELMET),
        build(Material.IRON_CHESTPLATE),
        build(Material.IRON_LEGGINGS),
        build(Material.IRON_BOOTS),
        build(Material.IRON_SWORD)
    ),
    KIT26(
        build(Material.IRON_HELMET),
        build(Material.IRON_CHESTPLATE),
        build(Material.IRON_LEGGINGS),
        build(Material.IRON_BOOTS),
        build(Material.IRON_SWORD)
    ),
    KIT27(
        build(Material.IRON_HELMET),
        build(Material.IRON_CHESTPLATE),
        build(Material.IRON_LEGGINGS),
        build(Material.IRON_BOOTS),
        build(Material.IRON_AXE, 1)
    ),
    KIT28(
        build(Material.IRON_HELMET),
        build(Material.IRON_CHESTPLATE, 1),
        build(Material.IRON_LEGGINGS),
        build(Material.IRON_BOOTS),
        build(Material.IRON_AXE, 1)
    ),
    KIT29(
        build(Material.IRON_HELMET),
        build(Material.IRON_CHESTPLATE, 1),
        build(Material.IRON_LEGGINGS),
        build(Material.IRON_BOOTS, 1),
        build(Material.IRON_AXE, 1)
    ),
    KIT30(
        build(Material.IRON_HELMET, 1),
        build(Material.IRON_CHESTPLATE, 1),
        build(Material.IRON_LEGGINGS),
        build(Material.IRON_BOOTS, 1),
        build(Material.IRON_AXE, 1)
    ),
    KIT31(
        build(Material.IRON_HELMET, 1),
        build(Material.IRON_CHESTPLATE, 1),
        build(Material.IRON_LEGGINGS, 1),
        build(Material.IRON_BOOTS, 1),
        build(Material.IRON_AXE, 1)
    ),
    KIT32(
        build(Material.IRON_HELMET, 1),
        build(Material.IRON_CHESTPLATE, 1),
        build(Material.IRON_LEGGINGS, 1),
        build(Material.IRON_BOOTS, 1),
        build(Material.IRON_SWORD, 1)
    ),
    /*DIAMOND*/
    KIT33(
        build(Material.IRON_HELMET, 1),
        build(Material.DIAMOND_CHESTPLATE),
        build(Material.IRON_LEGGINGS, 1),
        build(Material.IRON_BOOTS, 1),
        build(Material.DIAMOND_SWORD)
    ),
    KIT34(
        build(Material.IRON_HELMET, 1),
        build(Material.DIAMOND_CHESTPLATE),
        build(Material.IRON_LEGGINGS, 1),
        build(Material.DIAMOND_BOOTS),
        build(Material.DIAMOND_SWORD)
    ),
    KIT35(
        build(Material.DIAMOND_HELMET),
        build(Material.DIAMOND_CHESTPLATE),
        build(Material.DIAMOND_LEGGINGS),
        build(Material.DIAMOND_BOOTS),
        build(Material.DIAMOND_SWORD)
    ),
    KIT36(
        build(Material.DIAMOND_HELMET),
        build(Material.DIAMOND_CHESTPLATE),
        build(Material.DIAMOND_LEGGINGS),
        build(Material.DIAMOND_BOOTS),
        build(Material.DIAMOND_SWORD)
    ),
    KIT37(
        build(Material.DIAMOND_HELMET),
        build(Material.DIAMOND_CHESTPLATE),
        build(Material.DIAMOND_LEGGINGS),
        build(Material.DIAMOND_BOOTS),
        build(Material.DIAMOND_AXE, 1)
    ),
    KIT38(
        build(Material.DIAMOND_HELMET),
        build(Material.DIAMOND_CHESTPLATE, 1),
        build(Material.DIAMOND_LEGGINGS),
        build(Material.DIAMOND_BOOTS),
        build(Material.DIAMOND_AXE, 1)
    ),
    KIT39(
        build(Material.DIAMOND_HELMET),
        build(Material.DIAMOND_CHESTPLATE, 1),
        build(Material.DIAMOND_LEGGINGS),
        build(Material.DIAMOND_BOOTS, 1),
        build(Material.DIAMOND_AXE, 1)
    ),
    KIT40(
        build(Material.DIAMOND_HELMET, 1),
        build(Material.DIAMOND_CHESTPLATE, 1),
        build(Material.DIAMOND_LEGGINGS),
        build(Material.DIAMOND_BOOTS, 1),
        build(Material.DIAMOND_AXE, 1)
    ),
    KIT41(
        build(Material.DIAMOND_HELMET, 1),
        build(Material.DIAMOND_CHESTPLATE, 1),
        build(Material.DIAMOND_LEGGINGS, 1),
        build(Material.DIAMOND_BOOTS, 1),
        build(Material.DIAMOND_AXE, 1)
    ),
    KIT42(
        build(Material.DIAMOND_HELMET, 1),
        build(Material.DIAMOND_CHESTPLATE, 1),
        build(Material.DIAMOND_LEGGINGS, 1),
        build(Material.DIAMOND_BOOTS, 1),
        build(Material.DIAMOND_SWORD, 1)
    ),
    KIT43(
        build(Material.DIAMOND_HELMET, 1),
        build(Material.DIAMOND_CHESTPLATE, 1),
        build(Material.DIAMOND_LEGGINGS, 1),
        build(Material.DIAMOND_BOOTS, 1),
        build(Material.DIAMOND_SWORD, 2)
    ),
    KIT44(
        build(Material.DIAMOND_HELMET, 1),
        build(Material.DIAMOND_CHESTPLATE, 1),
        build(Material.DIAMOND_LEGGINGS, 1),
        build(Material.DIAMOND_BOOTS, 1),
        build(Material.DIAMOND_SWORD, 3)
    ),
    KIT45(
        build(Material.DIAMOND_HELMET, 1),
        build(Material.DIAMOND_CHESTPLATE, 1),
        build(Material.DIAMOND_LEGGINGS, 1),
        build(Material.DIAMOND_BOOTS, 1),
        build(Material.DIAMOND_SWORD, 4)
    ),
    KIT46(
        build(Material.DIAMOND_HELMET, 1),
        build(Material.DIAMOND_CHESTPLATE, 1),
        build(Material.DIAMOND_LEGGINGS, 1),
        build(Material.DIAMOND_BOOTS, 1),
        build(Material.DIAMOND_SWORD, 5)
    ),
    KIT47(
        build(Material.DIAMOND_HELMET, 1),
        build(Material.DIAMOND_CHESTPLATE, 1),
        build(Material.DIAMOND_LEGGINGS, 1),
        build(Material.DIAMOND_BOOTS, 1),
        build(Material.DIAMOND_SWORD, 6)
    ),
    KIT48(
        build(Material.DIAMOND_HELMET, 1),
        build(Material.DIAMOND_CHESTPLATE, 1),
        build(Material.DIAMOND_LEGGINGS, 1),
        build(Material.DIAMOND_BOOTS, 1),
        build(Material.DIAMOND_SWORD, 7)
    ),
    KIT49(
        build(Material.DIAMOND_HELMET, 1),
        build(Material.DIAMOND_CHESTPLATE, 1),
        build(Material.DIAMOND_LEGGINGS, 1),
        build(Material.DIAMOND_BOOTS, 1),
        build(Material.DIAMOND_SWORD, 8)
    ),
    KIT50(
        build(Material.DIAMOND_HELMET, 1),
        build(Material.DIAMOND_CHESTPLATE, 1),
        build(Material.DIAMOND_LEGGINGS, 1),
        build(Material.DIAMOND_BOOTS, 1),
        build(Material.DIAMOND_SWORD, 9)
    );

}

private fun build(material: Material, level: Int = 0): ItemStack {

    var typeName = material.toString().toLowerCase()
    typeName = when {
        typeName.endsWith("helmet") -> "Helm"
        typeName.endsWith("chestplate") -> "Brustpanzer"
        typeName.endsWith("leggings") -> "Hose"
        typeName.endsWith("boots") -> "Schuhe"
        else -> "Waffe"
    }

    val builder = ItemBuilder(material).setName("$SECONDARY$typeName")
    val iItemBuilder = if (level != 0) builder.addUnsafeEnchantment(
        if (typeName == "Waffe") Enchantment.DAMAGE_ALL else Enchantment.PROTECTION_ENVIRONMENTAL,
        level
    ) else builder
    return iItemBuilder.setUnbreakable().addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE).build()

}
