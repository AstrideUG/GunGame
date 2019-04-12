package de.astride.data

import kotlinx.serialization.*
import kotlinx.serialization.internal.IntSerializer
import kotlinx.serialization.internal.SerialClassDescImpl
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.internal.StringSerializer
import org.bukkit.Bukkit
import org.bukkit.Location
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
 * Current Version: 1.0 (04.04.2019 - 12.04.2019)
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
                addElement("amount")
                addElement("durability")
                addElement("enchantments")
                addElement("itemMeta")
            }
        }

    override fun serialize(encoder: Encoder, obj: ItemStack) {

        val enchantmentsSerializer = (EnchantmentSerializer to IntSerializer).map
        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, 0, obj.type.name)
        composite.encodeIntElement(descriptor, 1, obj.amount)
        composite.encodeShortElement(descriptor, 2, obj.durability)
        composite.encodeSerializableElement(descriptor, 3, enchantmentsSerializer, obj.enchantments)
        composite.encodeNullableSerializableElement(descriptor, 4, ItemMetaSerializer, obj.itemMeta)
        composite.endStructure(descriptor)

    }

    override fun deserialize(decoder: Decoder): ItemStack = decoder.decode()

}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 06.04.2019 21:03.
 * Current Version: 1.0 (06.04.2019 - 06.04.2019)
 */
@Serializer(forClass = ItemMeta::class)
object ItemMetaSerializer : KSerializer<ItemMeta> {

    override val descriptor: SerialDescriptor =
        object : SerialClassDescImpl(javaClass.simpleName.replace("Serializer", "")) {
            init {
                addElement("displayName")
                addElement("lore")
                addElement("itemFlags")
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

    override fun deserialize(decoder: Decoder): ItemMeta = decoder.decode()

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

