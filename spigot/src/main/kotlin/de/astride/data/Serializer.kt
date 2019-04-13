package de.astride.data

import kotlinx.serialization.*
import kotlinx.serialization.internal.IntSerializer
import kotlinx.serialization.internal.SerialClassDescImpl
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.internal.StringSerializer
import net.darkdevelopers.darkbedrock.darkness.spigot.builder.item.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.inventory.*
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.permissions.PermissionAttachmentInfo
import org.bukkit.potion.PotionEffect
import org.bukkit.util.Vector
import java.net.InetAddress
import java.net.InetSocketAddress
import java.util.*

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 19:46.
 * Current Version: 1.0 (04.04.2019 - 13.04.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 19:46.
 * Current Version: 1.0 (04.04.2019 - 04.04.2019)
 */
@Serializer(forClass = EntityDamageEvent::class)
object EntityDamageEventSerializer : KSerializer<EntityDamageEvent> {

    override val descriptor: SerialDescriptor =
        object : SerialClassDescImpl(javaClass.simpleName.replace("Serializer", "")) {}

    override fun serialize(encoder: Encoder, obj: EntityDamageEvent): Unit = encoder.encodeString(obj.toString())

    override fun deserialize(decoder: Decoder): EntityDamageEvent = decoder.decode()

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 20:48.
 * Current Version: 1.0 (04.04.2019 - 04.04.2019)
 */
@Serializer(forClass = Location::class)
object LocationSerializer : KSerializer<Location> {

    override val descriptor: SerialDescriptor =
        object : SerialClassDescImpl(javaClass.simpleName.replace("Serializer", "")) {
            init {
                addElement("world")
                addElement("x")
                addElement("y")
                addElement("z")
                addElement("yaw", true)
                addElement("pitch", true)
            }
        }

    override fun serialize(encoder: Encoder, obj: Location) {

        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, 0, obj.world.name)
        composite.encodeDoubleElement(descriptor, 1, obj.x)
        composite.encodeDoubleElement(descriptor, 2, obj.y)
        composite.encodeDoubleElement(descriptor, 3, obj.z)
        composite.encodeFloatElement(descriptor, 4, obj.yaw)
        composite.encodeFloatElement(descriptor, 5, obj.pitch)
        composite.endStructure(descriptor)

    }

    override fun deserialize(decoder: Decoder): Location {

        //TODO: Fix
        val composite = decoder.beginStructure(descriptor)
//        val worldName = composite.decodeStringElement(descriptor, 0)
//        val worldName1 = composite.decodeStringElement(descriptor, 0)
//        println(worldName + worldName1)
//        println(composite.decodeDoubleElement(descriptor, 1))
//        println(composite.decodeDoubleElement(descriptor, 1))
//        println(composite.decodeStringElement(descriptor, 2))
//        println(composite.decodeStringElement(descriptor, 2))
//        println(composite.context)
//        println(composite.updateMode)
//        println(composite.toString())
//        val x = composite.decodeDoubleElement(descriptor, 2)
//        println(x)
//        val x1 = composite.decodeDoubleElement(descriptor, 0)
//        println(x1)
//        val y = composite.decodeDoubleElement(descriptor, 2)
//        val z = composite.decodeDoubleElement(descriptor, 3)
//        val yaw = composite.decodeFloatElement(descriptor, 4)
//        val pitch = composite.decodeFloatElement(descriptor, 5)

        lateinit var worldName: String
        var x: Double? = null
        var y: Double? = null
        var z: Double? = null
        loop@ while (true) {
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.READ_DONE -> break@loop
                0 -> worldName = composite.decodeStringElement(descriptor, index)
                1 -> x = composite.decodeDoubleElement(descriptor, index)
                2 -> y = composite.decodeDoubleElement(descriptor, index)
                3 -> z = composite.decodeDoubleElement(descriptor, index)
                else -> composite.decodeStringElement(descriptor, index)
//                else -> throw SerializationException("Unknown index $index")
            }
        }
//
        composite.endStructure(descriptor)

        return Location(Bukkit.getWorld(worldName), x!!, y!!, z!!)
//        return Location(null, 0.0, 0.0, 0.0)

    }

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 20:48.
 * Current Version: 1.0 (04.04.2019 - 04.04.2019)
 */
@Serializer(forClass = Inventory::class)
object InventorySerializer : KSerializer<Inventory> {

    override val descriptor: SerialDescriptor =
        object : SerialClassDescImpl(javaClass.simpleName.replace("Serializer", "")) {}

    override fun serialize(encoder: Encoder, obj: Inventory): Unit = encoder.encodeString(obj.toString())

    override fun deserialize(decoder: Decoder): Inventory = decoder.decode()

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 20:49.
 * Current Version: 1.0 (04.04.2019 - 04.04.2019)
 */
@Serializer(forClass = Vector::class)
object VectorSerializer : KSerializer<Vector> {

    override val descriptor: SerialDescriptor =
        object : SerialClassDescImpl(javaClass.simpleName.replace("Serializer", "")) {
            init {
                addElement("x")
                addElement("y")
                addElement("z")
            }
        }

    override fun serialize(encoder: Encoder, obj: Vector) {

        val composite = encoder.beginStructure(descriptor)
        composite.encodeDoubleElement(descriptor, 0, obj.x)
        composite.encodeDoubleElement(descriptor, 1, obj.y)
        composite.encodeDoubleElement(descriptor, 2, obj.z)
        composite.endStructure(descriptor)

    }

    override fun deserialize(decoder: Decoder): Vector = Vector(0, 0, 0) //TODO: fix

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 20:51.
 * Current Version: 1.0 (04.04.2019 - 04.04.2019)
 */
@Serializer(forClass = EntityEquipment::class)
object EntityEquipmentSerializer : KSerializer<EntityEquipment> {

    override val descriptor: SerialDescriptor =
        object : SerialClassDescImpl(javaClass.simpleName.replace("Serializer", "")) {}

    override fun serialize(encoder: Encoder, obj: EntityEquipment): Unit = encoder.encodeString(obj.toString())

    override fun deserialize(decoder: Decoder): EntityEquipment = decoder.decode()

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 20:52.
 * Current Version: 1.0 (04.04.2019 - 04.04.2019)
 */
@Serializer(forClass = InventoryView::class)
object InventoryViewSerializer : KSerializer<InventoryView> {

    override val descriptor: SerialDescriptor =
        object : SerialClassDescImpl(javaClass.simpleName.replace("Serializer", "")) {}

    override fun serialize(encoder: Encoder, obj: InventoryView): Unit = encoder.encodeString(obj.toString())

    override fun deserialize(decoder: Decoder): InventoryView = decoder.decode()

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 20:53.
 * Current Version: 1.0 (04.04.2019 - 12.04.2019)
 */
@Serializer(forClass = ItemStack::class)
object ItemStackSerializer : KSerializer<ItemStack> {

    override val descriptor: SerialDescriptor =
        object : SerialClassDescImpl(javaClass.simpleName.replace("Serializer", "")) {
            init {
                addElement("type")
                addElement("amount", true)
                addElement("durability", true)
                addElement("enchantments", true)
                addElement("itemMeta", true)
            }
        }

    override fun serialize(encoder: Encoder, obj: ItemStack) {

        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, 0, obj.type.name)
        if (obj.amount != 1) composite.encodeIntElement(descriptor, 1, obj.amount)
        if (obj.durability != 0.toShort()) composite.encodeShortElement(descriptor, 2, obj.durability)
        if (obj.enchantments.isNotEmpty()) composite.encodeSerializableElement(
            descriptor,
            3,
            (EnchantmentSerializer to IntSerializer).map,
            obj.enchantments
        )
        if (obj.itemMeta != null) composite.encodeSerializableElement(
            descriptor,
            4,
            ItemMetaSerializer,
            obj.itemMeta
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
                CompositeDecoder.READ_DONE -> break@loop
                0 -> type = Material.valueOf(composite.decodeStringElement(descriptor, 0))
                1 -> amount = composite.decodeIntElement(descriptor, 1)
                2 -> durability = composite.decodeShortElement(descriptor, 2)
                3 -> enchantments =
                    composite.decodeSerializableElement(descriptor, 3, (EnchantmentSerializer to IntSerializer).map)
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
        object : SerialClassDescImpl(javaClass.simpleName.replace("Serializer", "")) {
            init {
                addElement("display-name")
                addElement("lore")
                addElement("item-flags")
                addElement("unbreakable")
            }
        }

    override fun serialize(encoder: Encoder, obj: ItemMeta) {

        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, 0, obj.displayName)
        composite.encodeSerializableElement(descriptor, 1, StringSerializer.list, obj.lore.orEmpty())
        composite.encodeSerializableElement(descriptor, 2, ItemFlagSerializer.set, obj.itemFlags)
        composite.encodeBooleanElement(descriptor, 3, obj.spigot().isUnbreakable)
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
                CompositeDecoder.READ_DONE -> break@loop
                0 -> displayName = composite.decodeStringElement(descriptor, 0)
                1 -> lore = composite.decodeSerializableElement(descriptor, 1, StringSerializer.list)
                2 -> itemFlags = composite.decodeSerializableElement(descriptor, 2, ItemFlagSerializer.set)
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

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 12.04.2019 11:40.
 * Current Version: 1.0 (12.04.2019 - 12.04.2019)
 */
@Serializer(forClass = ItemFlag::class)
object ItemFlagSerializer : KSerializer<ItemFlag> {

    override val descriptor: SerialDescriptor = StringDescriptor.withName("ItemFlag")

    override fun serialize(encoder: Encoder, obj: ItemFlag): Unit = encoder.encodeString(obj.name)

    override fun deserialize(decoder: Decoder): ItemFlag = ItemFlag.valueOf(decoder.decodeString().toUpperCase())

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 06.04.2019 21:08.
 * Current Version: 1.0 (06.04.2019 - 06.04.2019)
 */
@Serializer(forClass = Enchantment::class)
object EnchantmentSerializer : KSerializer<Enchantment> {

    override val descriptor: SerialDescriptor =
        object : SerialClassDescImpl(javaClass.simpleName.replace("Serializer", "")) {}

    override fun serialize(encoder: Encoder, obj: Enchantment) = encoder.encodeString(obj.name)

    override fun deserialize(decoder: Decoder): Enchantment =
        Enchantment.getByName(decoder.decodeString().toUpperCase())

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 20:54.
 * Current Version: 1.0 (04.04.2019 - 11.04.2019)
 */
@Serializer(forClass = InetSocketAddress::class)
object InetSocketAddressSerializer : KSerializer<InetSocketAddress> {

    override val descriptor: SerialDescriptor = StringDescriptor.withName("InetSocketAddress")

    override fun serialize(encoder: Encoder, obj: InetSocketAddress): Unit = encoder.encodeString(obj.toString())

    override fun deserialize(decoder: Decoder): InetSocketAddress {
        val input = decoder.decodeString()
        val decodedString = input.split('/')[1]
        val split = decodedString.split(':')
        return InetSocketAddress(InetAddress.getByName(split[0]), split[1].toInt())
    }

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 20:55.
 * Current Version: 1.0 (04.04.2019 - 11.04.2019)
 */
@Serializer(forClass = UUID::class)
object UUIDSerializer : KSerializer<UUID> {

    override val descriptor: SerialDescriptor = StringDescriptor.withName("UUID")

    override fun serialize(encoder: Encoder, obj: UUID): Unit = encoder.encodeString(obj.toString())

    override fun deserialize(decoder: Decoder): UUID = UUID.fromString(decoder.decodeString())

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 21:04.
 * Current Version: 1.0 (04.04.2019 - 04.04.2019)
 */
@Serializer(forClass = PotionEffect::class)
object PotionEffectSerializer : KSerializer<PotionEffect> {

    override val descriptor: SerialDescriptor =
        object : SerialClassDescImpl(javaClass.simpleName.replace("Serializer", "")) {}

    override fun serialize(encoder: Encoder, obj: PotionEffect): Unit = encoder.encodeString(obj.toString())

    override fun deserialize(decoder: Decoder): PotionEffect = decoder.decode()

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 21:08.
 * Current Version: 1.0 (04.04.2019 - 04.04.2019)
 */
@Serializer(forClass = Set::class)
object PermissionAttachmentInfoSerializer : KSerializer<Set<PermissionAttachmentInfo>> {

    override val descriptor: SerialDescriptor =
        object : SerialClassDescImpl(javaClass.simpleName.replace("Serializer", "")) {}

    override fun serialize(encoder: Encoder, obj: Set<PermissionAttachmentInfo>): Unit =
        encoder.encodeString("[]")

    override fun deserialize(decoder: Decoder): Set<PermissionAttachmentInfo> = emptySet()

}

@Serializer(forClass = Any::class)
object AnySerializer : KSerializer<Any> {

    override val descriptor: SerialDescriptor =
        object : SerialClassDescImpl(javaClass.simpleName.replace("Serializer", "")) {}

    override fun serialize(encoder: Encoder, obj: Any): Unit = encoder.encodeString(obj.toString())

    override fun deserialize(decoder: Decoder): Any = decoder.decode()

}

