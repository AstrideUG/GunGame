package de.astride.gungame.services

import com.google.gson.JsonArray
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonService
import net.darkdevelopers.darkbedrock.darkness.spigot.configs.gson.BukkitGsonConfig
import org.bukkit.Bukkit
import java.io.File

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 29.03.2019 13:42.
 * Current Version: 1.0 (29.03.2019 - 29.03.2019)
 */
class ConfigService(private val directory: File) {

    val shops by lazy { Shops() }
    val maps by lazy { Maps() }

    inner class Maps internal constructor() {

        private val configData = ConfigData(directory, "maps.json")
        val config = BukkitGsonConfig(configData)
        val maps = GsonService.load(configData) as? JsonArray ?: JsonArray()

    }

    inner class Shops internal constructor() {

        /* Main */
        private val configData = ConfigData(directory, "shops.json")
        private val jsonArray = GsonService.load(configData) as? JsonArray ?: JsonArray()
        private val bukkitGsonConfig = BukkitGsonConfig(configData)

        /* Values */
        val locations = jsonArray.map { bukkitGsonConfig.getLocation(it.asJsonObject) }

    }

    companion object {

        val instance get() = Bukkit.getServicesManager()?.getRegistration(ConfigService::class.java)?.provider!!

    }

}