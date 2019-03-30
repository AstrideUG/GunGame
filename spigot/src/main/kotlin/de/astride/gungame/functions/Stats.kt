package de.astride.gungame.functions

import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonConfig
import net.darkdevelopers.darkbedrock.darkness.general.databases.mysql.MySQL
import org.bukkit.entity.Player
import java.util.*

/*
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 09:19.
 * Current Version: 1.0 (30.03.2019 - 30.03.2019)
 */

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 09:16.
 * Current Version: 1.0 (30.03.2019 - 30.03.2019)
 */
val statistics = mutableMapOf<UUID, MutableMap<String, Int>>()

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 09:19.
 * Current Version: 1.0 (30.03.2019 - 30.03.2019)
 */
val Player.stats get() = statistics.putIfAbsent(uniqueId, mutableMapOf())!!


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 30.03.2019 09:30.
 * Current Version: 1.0 (30.03.2019 - 30.03.2019)
 */
val mySQL by lazy { MySQL(GsonConfig(ConfigData(javaPlugin.dataFolder, "mysql.json"))) }