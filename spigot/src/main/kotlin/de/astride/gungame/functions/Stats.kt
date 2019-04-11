package de.astride.gungame.functions

import de.astride.gungame.stats.Action
import net.darkdevelopers.darkbedrock.darkness.general.minecraft.fetcher.Fetcher
import java.util.*

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 09:19.
 * Current Version: 1.0 (30.03.2019 - 11.04.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 06.04.2019 03:47.
 * Current Version: 1.0 (06.04.2019 - 11.04.2019)
 */
val replacements = mutableMapOf<String, (UUID) -> Any>(
    "name" to { uuid -> Fetcher.getName(uuid) ?: uuid },
    "rank" to { uuid -> uuid.rank },
    "points" to { uuid -> uuid.points() },
    "deaths" to { uuid -> uuid.count("PlayerDeathEvent") },
    "kills" to { uuid -> uuid.count("PlayerRespawnEvent") },
    "kd" to { uuid -> uuid.count("PlayerRespawnEvent").toFloat() / uuid.count("PlayerDeathEvent").toFloat() },
    "death-streak" to { uuid -> uuid.streak("PlayerDeathEvent", "PlayerRespawnEvent") },
    "kill-streak" to { uuid -> uuid.streak("PlayerRespawnEvent", "PlayerDeathEvent") },
    "max-death-streak" to { uuid -> uuid.maxStreak("PlayerDeathEvent", "PlayerRespawnEvent") },
    "max-kill-streak" to { uuid -> uuid.maxStreak("PlayerRespawnEvent", "PlayerDeathEvent") },
    "shop-color-changes" to { uuid -> uuid.count("shop-change-color") },
    "shop-openings" to { uuid ->
        uuid.count("shop-change-color", uuid.activeActions.filter { !(it.meta["sneaking"] as? Boolean ?: true) })
    },
    "bought-levelup" to { uuid -> uuid.count("bought-LevelUp") },
    "bought-magicheal" to { uuid -> uuid.count("bought-MagicHeal") },
    "bought-instantkiller" to { uuid -> uuid.count("bought-InstantKiller") },
    "bought-keepinventory" to { uuid -> uuid.count("bought-KeepInventory") },
    "used-magicheal" to { uuid -> uuid.count("used-MagicHeal") },
    "used-instantkiller" to { uuid -> uuid.count("used-InstantKiller") },
    "used-keepinventory" to { uuid -> uuid.count("used-KeepInventory") }
)

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 20:22.
 * Current Version: 1.0 (30.03.2019 - 30.03.2019)
 */
lateinit var allActions: MutableMap<UUID, MutableList<Action>>

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 18:49.
 * Current Version: 1.0 (30.03.2019 - 30.03.2019)
 */
val UUID.actions: MutableList<Action> get() = allActions.getOrPut(this) { mutableListOf() }

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 23:46.
 * Current Version: 1.0 (30.03.2019 - 30.03.2019)
 */
val UUID.activeActions: List<Action> get() = actions.takeLastWhile { it.key != "StatsReset" }

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 31.03.2019 01:01.
 * Current Version: 1.0 (31.03.2019 - 31.03.2019)
 */
val UUID.rank get() = ranks().asReversed().takeWhile { it != Fetcher.getName(this) }.count() + 1

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 21:32.
 * Current Version: 1.0 (30.03.2019 - 31.03.2019)
 */
fun UUID.count(key: String, actions: List<Action> = activeActions): Int = actions.filter { it.key == key }.count()

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 23:49.
 * Current Version: 1.0 (30.03.2019 - 31.03.2019)
 */
fun UUID.points(): Int {
    var count = 0
    activeActions.forEach {
        count += when {
            it.key == "PlayerDeathEvent" -> if (count > 5) -5 else count * -1
            it.key == "PlayerRespawnEvent" -> 10
            else -> 0
        }
    }
    return count
}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 23:59.
 *
 * Sorted Names
 *
 * Current Version: 1.0 (30.03.2019 - 31.03.2019)
 */
fun ranks() = allActions.keys.sortedBy { it.points() }.map { Fetcher.getName(it) ?: "unknown" }

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 31.03.2019 19:25.
 * Current Version: 1.0 (31.03.2019 - 31.03.2019)
 */
fun UUID.maxStreak(check1: String, runNew: String): Int {
    val counts = mutableListOf(0)
    activeActions.forEach {
        if (it.key == check1) counts[counts.size - 1]++
        else if (it.key == runNew) counts += 0
    }
    var b = 0
    for (i in 0 until counts.size) if (counts[i] > b) b = counts[i]
    return b
}

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 31.03.2019 21:19.
 * Current Version: 1.0 (31.03.2019 - 31.03.2019)
 */
fun UUID.streak(check1: String, runNew: String): Int = count(check1, activeActions.takeLastWhile { it.key != runNew })

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 06.04.2019 06:35.
 * Current Version: 1.0 (06.04.2019 - 06.04.2019)
 */
fun List<String?>.withReplacements(uuid: UUID): List<String?> = map { line ->
    line ?: return@map null
    var result = line

    replacements.forEach { (key, func) ->
        if (key in line) result = result.replace(key, func(uuid))
    }

    result
}
