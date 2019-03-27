/*
 * © Copyright - Lars Artmann | LartyHD 2018.
 */
package de.astride.gungame.kits

import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import net.darkdevelopers.darkbedrock.darkness.spigot.messages.Colors.SECONDARY
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 20.08.2017 18:19.
 * Current Version: 1.0 (20.08.2017 - 27.03.2019)
 */
enum class Kits(
    val helmet: ItemStack?,
    val chestplate: ItemStack?,
    val leggins: ItemStack?,
    val boots: ItemStack?,
    val item: ItemStack
) {
    /*BASIC*/
    KIT1(null, null, null, null, getWaffe(Material.WOOD_AXE)),
    KIT2(null, null, null, null, getWaffe(Material.WOOD_SWORD)),
    /*LEATHER*/
    KIT3(null, getArmor(Material.LEATHER_CHESTPLATE), null, null, getWaffe(Material.WOOD_SWORD)),
    KIT4(
        null,
        getArmor(Material.LEATHER_CHESTPLATE),
        null,
        getArmor(Material.LEATHER_BOOTS),
        getWaffe(Material.WOOD_SWORD)
    ),
    KIT5(
        getArmor(Material.LEATHER_HELMET),
        getArmor(Material.LEATHER_CHESTPLATE),
        null,
        getArmor(Material.LEATHER_BOOTS),
        getWaffe(Material.WOOD_SWORD)
    ),
    KIT6(
        getArmor(Material.LEATHER_HELMET),
        getArmor(Material.LEATHER_CHESTPLATE),
        getArmor(Material.LEATHER_LEGGINGS),
        getArmor(Material.LEATHER_BOOTS),
        getWaffe(Material.WOOD_SWORD)
    ),
    KIT7(
        getArmor(Material.LEATHER_HELMET),
        getArmor(Material.LEATHER_CHESTPLATE),
        getArmor(Material.LEATHER_LEGGINGS),
        getArmor(Material.LEATHER_BOOTS),
        getWaffe(Material.WOOD_AXE, 1)
    ),
    KIT8(
        getArmor(Material.LEATHER_HELMET),
        getArmor(Material.LEATHER_CHESTPLATE, 1),
        getArmor(Material.LEATHER_LEGGINGS),
        getArmor(Material.LEATHER_BOOTS),
        getWaffe(Material.WOOD_AXE, 1)
    ),
    KIT9(
        getArmor(Material.LEATHER_HELMET),
        getArmor(Material.LEATHER_CHESTPLATE, 1),
        getArmor(Material.LEATHER_LEGGINGS),
        getArmor(Material.LEATHER_BOOTS, 1),
        getWaffe(Material.WOOD_AXE, 1)
    ),
    KIT10(
        getArmor(Material.LEATHER_HELMET, 1),
        getArmor(Material.LEATHER_CHESTPLATE, 1),
        getArmor(Material.LEATHER_LEGGINGS),
        getArmor(Material.LEATHER_BOOTS, 1),
        getWaffe(Material.WOOD_AXE, 1)
    ),
    KIT11(
        getArmor(Material.LEATHER_HELMET, 1),
        getArmor(Material.LEATHER_CHESTPLATE, 1),
        getArmor(Material.LEATHER_LEGGINGS, 1),
        getArmor(Material.LEATHER_BOOTS, 1),
        getWaffe(Material.WOOD_AXE, 1)
    ),
    KIT12(
        getArmor(Material.LEATHER_HELMET, 1),
        getArmor(Material.LEATHER_CHESTPLATE, 1),
        getArmor(Material.LEATHER_LEGGINGS, 1),
        getArmor(Material.LEATHER_BOOTS, 1),
        getWaffe(Material.WOOD_SWORD, 1)
    ),
    /*CHAINMAIL*/
    KIT13(
        getArmor(Material.LEATHER_HELMET, 1),
        getArmor(Material.CHAINMAIL_CHESTPLATE),
        getArmor(Material.LEATHER_LEGGINGS, 1),
        getArmor(Material.LEATHER_BOOTS, 1),
        getWaffe(Material.STONE_SWORD)
    ),
    KIT14(
        getArmor(Material.LEATHER_HELMET, 1),
        getArmor(Material.CHAINMAIL_CHESTPLATE),
        getArmor(Material.LEATHER_LEGGINGS, 1),
        getArmor(Material.CHAINMAIL_BOOTS),
        getWaffe(Material.STONE_SWORD)
    ),
    KIT15(
        getArmor(Material.CHAINMAIL_HELMET),
        getArmor(Material.CHAINMAIL_CHESTPLATE),
        getArmor(Material.CHAINMAIL_LEGGINGS),
        getArmor(Material.CHAINMAIL_BOOTS),
        getWaffe(Material.STONE_SWORD)
    ),
    KIT16(
        getArmor(Material.CHAINMAIL_HELMET),
        getArmor(Material.CHAINMAIL_CHESTPLATE),
        getArmor(Material.CHAINMAIL_LEGGINGS),
        getArmor(Material.CHAINMAIL_BOOTS),
        getWaffe(Material.STONE_SWORD)
    ),
    KIT17(
        getArmor(Material.CHAINMAIL_HELMET),
        getArmor(Material.CHAINMAIL_CHESTPLATE),
        getArmor(Material.CHAINMAIL_LEGGINGS),
        getArmor(Material.CHAINMAIL_BOOTS),
        getWaffe(Material.STONE_AXE, 1)
    ),
    KIT18(
        getArmor(Material.CHAINMAIL_HELMET),
        getArmor(Material.CHAINMAIL_CHESTPLATE, 1),
        getArmor(Material.CHAINMAIL_LEGGINGS),
        getArmor(Material.CHAINMAIL_BOOTS),
        getWaffe(Material.STONE_AXE, 1)
    ),
    KIT19(
        getArmor(Material.CHAINMAIL_HELMET),
        getArmor(Material.CHAINMAIL_CHESTPLATE, 1),
        getArmor(Material.CHAINMAIL_LEGGINGS),
        getArmor(Material.CHAINMAIL_BOOTS, 1),
        getWaffe(Material.STONE_AXE, 1)
    ),
    KIT20(
        getArmor(Material.CHAINMAIL_HELMET, 1),
        getArmor(Material.CHAINMAIL_CHESTPLATE, 1),
        getArmor(Material.CHAINMAIL_LEGGINGS),
        getArmor(Material.CHAINMAIL_BOOTS, 1),
        getWaffe(Material.STONE_AXE, 1)
    ),
    KIT21(
        getArmor(Material.CHAINMAIL_HELMET, 1),
        getArmor(Material.CHAINMAIL_CHESTPLATE, 1),
        getArmor(Material.CHAINMAIL_LEGGINGS, 1),
        getArmor(Material.CHAINMAIL_BOOTS, 1),
        getWaffe(Material.STONE_AXE, 1)
    ),
    KIT22(
        getArmor(Material.CHAINMAIL_HELMET, 1),
        getArmor(Material.CHAINMAIL_CHESTPLATE, 1),
        getArmor(Material.CHAINMAIL_LEGGINGS, 1),
        getArmor(Material.CHAINMAIL_BOOTS, 1),
        getWaffe(Material.STONE_SWORD, 1)
    ),
    /*IRON*/
    KIT23(
        getArmor(Material.CHAINMAIL_HELMET, 1),
        getArmor(Material.IRON_CHESTPLATE),
        getArmor(Material.CHAINMAIL_LEGGINGS, 1),
        getArmor(Material.CHAINMAIL_BOOTS, 1),
        getWaffe(Material.IRON_SWORD)
    ),
    KIT24(
        getArmor(Material.CHAINMAIL_HELMET, 1),
        getArmor(Material.IRON_CHESTPLATE),
        getArmor(Material.CHAINMAIL_LEGGINGS, 1),
        getArmor(Material.IRON_BOOTS),
        getWaffe(Material.IRON_SWORD)
    ),
    KIT25(
        getArmor(Material.IRON_HELMET),
        getArmor(Material.IRON_CHESTPLATE),
        getArmor(Material.IRON_LEGGINGS),
        getArmor(Material.IRON_BOOTS),
        getWaffe(Material.IRON_SWORD)
    ),
    KIT26(
        getArmor(Material.IRON_HELMET),
        getArmor(Material.IRON_CHESTPLATE),
        getArmor(Material.IRON_LEGGINGS),
        getArmor(Material.IRON_BOOTS),
        getWaffe(Material.IRON_SWORD)
    ),
    KIT27(
        getArmor(Material.IRON_HELMET),
        getArmor(Material.IRON_CHESTPLATE),
        getArmor(Material.IRON_LEGGINGS),
        getArmor(Material.IRON_BOOTS),
        getWaffe(Material.IRON_AXE, 1)
    ),
    KIT28(
        getArmor(Material.IRON_HELMET),
        getArmor(Material.IRON_CHESTPLATE, 1),
        getArmor(Material.IRON_LEGGINGS),
        getArmor(Material.IRON_BOOTS),
        getWaffe(Material.IRON_AXE, 1)
    ),
    KIT29(
        getArmor(Material.IRON_HELMET),
        getArmor(Material.IRON_CHESTPLATE, 1),
        getArmor(Material.IRON_LEGGINGS),
        getArmor(Material.IRON_BOOTS, 1),
        getWaffe(Material.IRON_AXE, 1)
    ),
    KIT30(
        getArmor(Material.IRON_HELMET, 1),
        getArmor(Material.IRON_CHESTPLATE, 1),
        getArmor(Material.IRON_LEGGINGS),
        getArmor(Material.IRON_BOOTS, 1),
        getWaffe(Material.IRON_AXE, 1)
    ),
    KIT31(
        getArmor(Material.IRON_HELMET, 1),
        getArmor(Material.IRON_CHESTPLATE, 1),
        getArmor(Material.IRON_LEGGINGS, 1),
        getArmor(Material.IRON_BOOTS, 1),
        getWaffe(Material.IRON_AXE, 1)
    ),
    KIT32(
        getArmor(Material.IRON_HELMET, 1),
        getArmor(Material.IRON_CHESTPLATE, 1),
        getArmor(Material.IRON_LEGGINGS, 1),
        getArmor(Material.IRON_BOOTS, 1),
        getWaffe(Material.IRON_SWORD, 1)
    ),
    /*DIAMOND*/
    KIT33(
        getArmor(Material.IRON_HELMET, 1),
        getArmor(Material.DIAMOND_CHESTPLATE),
        getArmor(Material.IRON_LEGGINGS, 1),
        getArmor(Material.IRON_BOOTS, 1),
        getWaffe(Material.DIAMOND_SWORD)
    ),
    KIT34(
        getArmor(Material.IRON_HELMET, 1),
        getArmor(Material.DIAMOND_CHESTPLATE),
        getArmor(Material.IRON_LEGGINGS, 1),
        getArmor(Material.DIAMOND_BOOTS),
        getWaffe(Material.DIAMOND_SWORD)
    ),
    KIT35(
        getArmor(Material.DIAMOND_HELMET),
        getArmor(Material.DIAMOND_CHESTPLATE),
        getArmor(Material.DIAMOND_LEGGINGS),
        getArmor(Material.DIAMOND_BOOTS),
        getWaffe(Material.DIAMOND_SWORD)
    ),
    KIT36(
        getArmor(Material.DIAMOND_HELMET),
        getArmor(Material.DIAMOND_CHESTPLATE),
        getArmor(Material.DIAMOND_LEGGINGS),
        getArmor(Material.DIAMOND_BOOTS),
        getWaffe(Material.DIAMOND_SWORD)
    ),
    KIT37(
        getArmor(Material.DIAMOND_HELMET),
        getArmor(Material.DIAMOND_CHESTPLATE),
        getArmor(Material.DIAMOND_LEGGINGS),
        getArmor(Material.DIAMOND_BOOTS),
        getWaffe(Material.DIAMOND_AXE, 1)
    ),
    KIT38(
        getArmor(Material.DIAMOND_HELMET),
        getArmor(Material.DIAMOND_CHESTPLATE, 1),
        getArmor(Material.DIAMOND_LEGGINGS),
        getArmor(Material.DIAMOND_BOOTS),
        getWaffe(Material.DIAMOND_AXE, 1)
    ),
    KIT39(
        getArmor(Material.DIAMOND_HELMET),
        getArmor(Material.DIAMOND_CHESTPLATE, 1),
        getArmor(Material.DIAMOND_LEGGINGS),
        getArmor(Material.DIAMOND_BOOTS, 1),
        getWaffe(Material.DIAMOND_AXE, 1)
    ),
    KIT40(
        getArmor(Material.DIAMOND_HELMET, 1),
        getArmor(Material.DIAMOND_CHESTPLATE, 1),
        getArmor(Material.DIAMOND_LEGGINGS),
        getArmor(Material.DIAMOND_BOOTS, 1),
        getWaffe(Material.DIAMOND_AXE, 1)
    ),
    KIT41(
        getArmor(Material.DIAMOND_HELMET, 1),
        getArmor(Material.DIAMOND_CHESTPLATE, 1),
        getArmor(Material.DIAMOND_LEGGINGS, 1),
        getArmor(Material.DIAMOND_BOOTS, 1),
        getWaffe(Material.DIAMOND_AXE, 1)
    ),
    KIT42(
        getArmor(Material.DIAMOND_HELMET, 1),
        getArmor(Material.DIAMOND_CHESTPLATE, 1),
        getArmor(Material.DIAMOND_LEGGINGS, 1),
        getArmor(Material.DIAMOND_BOOTS, 1),
        getWaffe(Material.DIAMOND_SWORD, 1)
    ),
    KIT43(
        getArmor(Material.DIAMOND_HELMET, 1),
        getArmor(Material.DIAMOND_CHESTPLATE, 1),
        getArmor(Material.DIAMOND_LEGGINGS, 1),
        getArmor(Material.DIAMOND_BOOTS, 1),
        getWaffe(Material.DIAMOND_SWORD, 2)
    ),
    KIT44(
        getArmor(Material.DIAMOND_HELMET, 1),
        getArmor(Material.DIAMOND_CHESTPLATE, 1),
        getArmor(Material.DIAMOND_LEGGINGS, 1),
        getArmor(Material.DIAMOND_BOOTS, 1),
        getWaffe(Material.DIAMOND_SWORD, 3)
    ),
    KIT45(
        getArmor(Material.DIAMOND_HELMET, 1),
        getArmor(Material.DIAMOND_CHESTPLATE, 1),
        getArmor(Material.DIAMOND_LEGGINGS, 1),
        getArmor(Material.DIAMOND_BOOTS, 1),
        getWaffe(Material.DIAMOND_SWORD, 4)
    ),
    KIT46(
        getArmor(Material.DIAMOND_HELMET, 1),
        getArmor(Material.DIAMOND_CHESTPLATE, 1),
        getArmor(Material.DIAMOND_LEGGINGS, 1),
        getArmor(Material.DIAMOND_BOOTS, 1),
        getWaffe(Material.DIAMOND_SWORD, 5)
    ),
    KIT47(
        getArmor(Material.DIAMOND_HELMET, 1),
        getArmor(Material.DIAMOND_CHESTPLATE, 1),
        getArmor(Material.DIAMOND_LEGGINGS, 1),
        getArmor(Material.DIAMOND_BOOTS, 1),
        getWaffe(Material.DIAMOND_SWORD, 6)
    ),
    KIT48(
        getArmor(Material.DIAMOND_HELMET, 1),
        getArmor(Material.DIAMOND_CHESTPLATE, 1),
        getArmor(Material.DIAMOND_LEGGINGS, 1),
        getArmor(Material.DIAMOND_BOOTS, 1),
        getWaffe(Material.DIAMOND_SWORD, 7)
    ),
    KIT49(
        getArmor(Material.DIAMOND_HELMET, 1),
        getArmor(Material.DIAMOND_CHESTPLATE, 1),
        getArmor(Material.DIAMOND_LEGGINGS, 1),
        getArmor(Material.DIAMOND_BOOTS, 1),
        getWaffe(Material.DIAMOND_SWORD, 8)
    ),
    KIT50(
        getArmor(Material.DIAMOND_HELMET, 1),
        getArmor(Material.DIAMOND_CHESTPLATE, 1),
        getArmor(Material.DIAMOND_LEGGINGS, 1),
        getArmor(Material.DIAMOND_BOOTS, 1),
        getWaffe(Material.DIAMOND_SWORD, 9)
    );

}

private fun getWaffe(material: Material, level: Int = 0): ItemStack {
    val builder = ItemBuilder(material).setName("${SECONDARY}Waffe").setUnbreakable().addAllItemFlags()
    val iItemBuilder =
        if (level != 0) builder.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, level) else builder
    return iItemBuilder.build()
}

private fun getArmor(material: Material, level: Int = 0): ItemStack {

    var typeName = material.toString().toLowerCase()
    when {
        typeName.endsWith("helmet") -> typeName = "${SECONDARY}Helm"
        typeName.endsWith("chestplate") -> typeName = "${SECONDARY}Brustpanzer"
        typeName.endsWith("leggings") -> typeName = "${SECONDARY}Hose"
        typeName.endsWith("boots") -> typeName = "${SECONDARY}Schuhe"
    }

    val builder = ItemBuilder(material).setName("$SECONDARY$typeName").setUnbreakable().addAllItemFlags()
    val iItemBuilder =
        if (level != 0) builder.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, level) else builder
    return iItemBuilder.build()

}
