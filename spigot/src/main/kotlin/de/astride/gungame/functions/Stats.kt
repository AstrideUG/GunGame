package de.astride.gungame.functions

import de.astride.gungame.stats.Action
import net.darkdevelopers.darkbedrock.darkness.general.minecraft.fetcher.Fetcher
import java.util.*

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 09:19.
 * Current Version: 1.0 (30.03.2019 - 31.03.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 20:22.
 * Current Version: 1.0 (30.03.2019 - 30.03.2019)
 */
val allActions: MutableMap<UUID, MutableList<Action>> = mutableMapOf()

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
val UUID.activeActions: List<Action> get() = actions.takeLastWhile { it.id != "StatsReset" }

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 31.03.2019 01:01.
 * Current Version: 1.0 (31.03.2019 - 31.03.2019)
 */
val UUID.rank get() = ranks().takeWhile { it != Fetcher.getName(this) }.count()

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 21:32.
 * Current Version: 1.0 (30.03.2019 - 31.03.2019)
 */
fun UUID.count(key: String, actions: List<Action> = activeActions): Int = actions.filter { it.id == key }.count()

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 23:49.
 * Current Version: 1.0 (30.03.2019 - 31.03.2019)
 */
fun UUID.points(): Int {
    var count = 0
    activeActions.forEach {
        count += when {
            it.id == "PlayerDeathEvent" -> if (count > 5) -5 else count * -1
            it.id == "PlayerRespawnEvent" -> 10
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
