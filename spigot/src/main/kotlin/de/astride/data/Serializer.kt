@file:Suppress("unused")

package de.astride.data

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.*
import org.bukkit.inventory.meta.ItemMeta
import java.util.*

@Serializer(forClass = ItemStack::class)
object ItemStackSerializer : KSerializer<ItemStack> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor(javaClass.simpleName.replace("Serializer", "")) {
            element<String>("type")
            element<Int>("amount", isOptional = true)
            element<Short>("durability", isOptional = true)
            element<Map<Enchantment, Int>>("enchantments", isOptional = true)
            element<ItemMeta>("itemMeta", isOptional = true)
        }

    override fun serialize(encoder: Encoder, value: ItemStack) {

        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, 0, value.type.name)
        if (value.amount != 1) composite.encodeIntElement(descriptor, 1, value.amount)
        if (value.durability != 0.toShort()) composite.encodeShortElement(descriptor, 2, value.durability)
        if (value.enchantments.isNotEmpty()) composite.encodeSerializableElement(
            descriptor,
            3,
            serializer<Map<Enchantment, Int>>(),
            value.enchantments
        )
        if (value.itemMeta != null) composite.encodeSerializableElement(
            descriptor,
            4,
            ItemMetaSerializer,
            value.itemMeta
        )
        composite.endStructure(descriptor)

    }

    override fun deserialize(decoder: Decoder): ItemStack {

        val composite = decoder.beginStructure(descriptor)

        var type: Material? = null
        var amount = 1
        var durability: Short = 0
        var enchantments: Map<Enchantment, Int> = emptyMap()
        var itemMeta: ItemMeta? = null
        loop@ while (true) {
            if (type == Material.AIR) break@loop
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break@loop
                0 -> type = Material.valueOf(composite.decodeStringElement(descriptor, 0))
                1 -> amount = composite.decodeIntElement(descriptor, 1)
                2 -> durability = composite.decodeShortElement(descriptor, 2)
                3 -> enchantments =
                    composite.decodeSerializableElement(descriptor, 3, serializer<Map<Enchantment, Int>>())
                4 -> itemMeta = composite.decodeSerializableElement(descriptor, 4, ItemMetaSerializer)
                else -> throw SerializationException("Unknown index $index")
            }
        }
        composite.endStructure(descriptor)

        return if (type == null || type == Material.AIR) ItemStack(Material.AIR)
        else ItemBuilder(ItemStack(type, amount, durability).apply { this.itemMeta = itemMeta }).apply {
            enchantments.forEach { (enchantment, i) -> addEnchant(enchantment, i) }
        }.build()
    }

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 06.04.2019 21:03.
 * Current Version: 1.0 (06.04.2019 - 13.04.2019)
 */
@Serializer(forClass = ItemMeta::class)
object ItemMetaSerializer : KSerializer<ItemMeta> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor(javaClass.simpleName.replace("Serializer", "")) {
            element<String>("display-name", isOptional = true)
            element<List<String>>("lore", isOptional = true)
            element<Set<String>>("item-flags", isOptional = true)
            element<Boolean>("unbreakable", isOptional = true)
        }

    override fun serialize(encoder: Encoder, value: ItemMeta) {

        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, 0, value.displayName)
        composite.encodeSerializableElement(descriptor, 1, serializer(), value.lore.orEmpty())
        composite.encodeSerializableElement(descriptor, 2, serializer(), value.itemFlags)
        composite.encodeBooleanElement(descriptor, 3, value.spigot().isUnbreakable)
        composite.endStructure(descriptor)

    }

    override fun deserialize(decoder: Decoder): ItemMeta {

        val composite = decoder.beginStructure(descriptor)

        var displayName: String? = null
        var lore = listOf<String>()
        var itemFlags: Set<ItemFlag> = emptySet()
        var unbreakable: Boolean? = null
        loop@ while (true) {
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break@loop
                0 -> displayName = composite.decodeStringElement(descriptor, 0)
                1 -> lore = composite.decodeSerializableElement(descriptor, 1, serializer())
                2 -> itemFlags = composite.decodeSerializableElement(descriptor, 2, serializer())
                3 -> unbreakable =
                    composite.decodeBooleanElement(descriptor, 3)
                else -> throw SerializationException("Unknown index $index")
            }
        }
        composite.endStructure(descriptor)

        return Bukkit.getItemFactory().getItemMeta(Material.STONE).apply {
            displayName?.let { this.displayName = it }
            this.lore = lore
            this.itemFlags.addAll(itemFlags)
            unbreakable?.let { this.spigot().isUnbreakable = it }
        }
    }

}

@Suppress("unused")
@Serializer(forClass = ItemFlag::class)
object ItemFlagSerializer : KSerializer<ItemFlag> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(javaClass.simpleName.replace("Serializer", ""), PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ItemFlag): Unit = encoder.encodeString(value.name)

    override fun deserialize(decoder: Decoder): ItemFlag = ItemFlag.valueOf(
        decoder.decodeString()
            .uppercase(Locale.getDefault())
    )

}

@Suppress("unused")
@Serializer(forClass = Enchantment::class)
object EnchantmentSerializer : KSerializer<Enchantment> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(javaClass.simpleName.replace("Serializer", ""), PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Enchantment) = encoder.encodeString(value.name)

    override fun deserialize(decoder: Decoder): Enchantment =
        Enchantment.getByName(decoder.decodeString().uppercase(Locale.getDefault()))

}

