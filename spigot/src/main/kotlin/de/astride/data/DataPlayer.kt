package de.astride.data

import org.bukkit.entity.Player

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 01.04.2019 20:07.
 * Current Version: 1.0 (01.04.2019 - 02.04.2019)
 */
class DataPlayer(
    val damageable: Damageable,
    val dataExp: DataExp,
    val dataFood: DataFood,
    val dataHealth: DataHealth,
    val dataLocation: DataLocation,
    val fireable: Fireable,
    val flyable: Flyable,
    val inventories: Inventories,
    val metaData: MetaData,
    val names: Names,
    val otherData: OtherData,
    val permissables: Permissables,
    val sleepable: Sleepable,
    val speeds: Speeds,
    val targetable: Targetable,
    val whiteAndBlackList: WhiteAndBlackList
)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.04.2019 03:05.
 * Current Version: 1.0 (02.04.2019 - 02.04.2019)
 */
fun Player.toDataPlayer(): DataPlayer = DataPlayer(
    Damageable(maximumNoDamageTicks, noDamageTicks, lastDamage, lastDamageCause),
    DataExp(level, exp, exhaustion, expToLevel, totalExperience),
    DataFood(flySpeed, saturation, exhaustion),
    DataHealth(health, maxHealth, healthScale, isHealthScaled, spigot().isInvulnerable),
    DataLocation(location, bedSpawnLocation, eyeLocation, eyeHeight, velocity, @Suppress("DEPRECATION") isOnGround),
    Fireable(fireTicks, maxFireTicks),
    Flyable(isFlying, allowFlight),
    Inventories(canPickupItems, equipment, enderChest, openInventory, inventory, itemInHand, itemOnCursor),
    MetaData(
        address,
        spigot().rawAddress,
        uniqueId,
        hasPlayedBefore(),
        lastPlayed,
        firstPlayed,
        spigot().locale,
        entityId,
        listeningPluginChannels
    ),
    Names(name, displayName),
    OtherData(
        playerListName,
        playerTime,
        playerTimeOffset,
        playerWeather,
        isPlayerTimeRelative,
        isBlocking,
        isConversing,
        isCustomNameVisible,
        isInsideVehicle,
        isLeashed,
        leashHolder,
        isEmpty,
        passenger,
        isDead,
        isValid,
        isOnline,
        scoreboard,
        killer,
        gameMode,
        fallDistance,
        type,
        vehicle,
        maximumAir,
        remainingAir,
        removeWhenFarAway,
        server,
        activePotionEffects,
        spigot().collidesWithEntities,
        spigot().hiddenPlayers
    ),
    Permissables(isOp, effectivePermissions),
    Sleepable(isSleeping, isSleepingIgnored),
    Speeds(isSprinting, isSneaking, walkSpeed, flySpeed),
    Targetable(spectatorTarget, compassTarget),
    WhiteAndBlackList(isWhitelisted, isBanned)
)


//{

//    init {
//        val a: Player? = null
//        a?.apply {
//
//            DataExp:
//            this.level
//            this.exp
//            this.exhaustion
//            this.expToLevel
//            this.totalExperience

//            DataFood:
//            this.exhaustion
//            this.foodLevel
//            this.saturation

//            Flyable
//            this.isFlying
//            this.allowFlight

//            Permissables
//            this.isOp
//            this.effectivePermissions

//            Targetable
//            this.spectatorTarget
//            this.compassTarget

//            //locations
//            this.world //is saved in location
//            this.location
//            this.bedSpawnLocation
//            this.eyeLocation
//            @Suppress("DEPRECATION") this.isOnGround
//            this.eyeHeight
//            this.velocity

//            Speeds
//            this.isSprinting
//            this.isSneaking
//            this.walkSpeed
//            this.flySpeed

//White-/BlackList
//            this.isWhitelisted
//            this.isBanned

//            MetaData
//            this.address
//            this.spigot().rawAddress
//            this.uniqueId
//            this.hasPlayedBefore()
//            this.lastPlayed
//            this.firstPlayed
//            this.spigot().locale
//            this.listeningPluginChannels
//            this.entityId

//            Sleepable
//            this.isSleepingIgnored
//            this.isSleeping

//            Damageable
//            this.maximumNoDamageTicks
//            this.noDamageTicks
//            this.lastDamage
//            this.lastDamageCause

//            Fireable
//            this.fireTicks
//            this.maxFireTicks

//            Inventories
//            this.canPickupItems
//            this.equipment
//            this.enderChest
//            this.openInventory
//            this.inventory
//            this.itemInHand
//            this.itemOnCursor

//            Names
//            this.name
//            this.displayName

//            DataHealth
//            this.health
//            this.maxHealth
//            this.healthScale
//            this.isHealthScaled
//            this.spigot().isInvulnerable

//OtherData
//            this.playerListName
//            this.playerTime
//            this.playerTimeOffset
//            this.playerWeather
//
//            this.isPlayerTimeRelative
//            this.isBlocking
//            this.isConversing
//            this.isCustomNameVisible
//            this.isInsideVehicle
//
//            this.isLeashed
//            this.leashHolder
//
//            this.isEmpty
//            this.passenger
//
//            this.isDead
//            this.isValid
//            this.isOnline
//
//            this.scoreboard
//            this.killer
//            this.gameMode
//            this.fallDistance
//            this.type
//            this.vehicle //vehicleable
//            this.maximumAir
//            this.remainingAir
//            this.removeWhenFarAway
//            this.server
//            this.activePotionEffects
//
//            //spigot
//            this.spigot().collidesWithEntities
//            this.spigot().hiddenPlayers
//
//    }
//    }
//}