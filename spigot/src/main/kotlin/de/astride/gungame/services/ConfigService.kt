package de.astride.gungame.services

import com.google.gson.JsonArray
import com.google.gson.JsonObject
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

    val config by lazy { Config() }
    val shops by lazy { Shops() }
    val maps by lazy { Maps() }

    inner class Config internal constructor() {

        /* Main */
        private val configData = ConfigData(directory, "config.json")
        private val jsonObject = GsonService.loadAsJsonObject(configData)

        /* SubClass */
        val files by lazy { Files(jsonObject[Files::class.java.simpleName]?.asJsonObject) }

        inner class Files internal constructor(jsonObject: JsonObject?) {

            /* Values */
            val maps by lazy { jsonObject?.get("maps")?.asString ?: "maps.json" }
            val shops by lazy { jsonObject?.get("shops")?.asString ?: "shops.json" }

        }

    }

    inner class Maps internal constructor() {

        /* Main */
        private val configData = ConfigData(directory, config.files.maps)
        val maps = GsonService.load(configData) as? JsonArray ?: JsonArray()

        /* Values */
        val bukkitGsonConfig = BukkitGsonConfig(configData)

    }

    inner class Shops internal constructor() {

        /* Main */
        private val configData = ConfigData(directory, config.files.shops)
        private val jsonArray = GsonService.load(configData) as? JsonArray ?: JsonArray()
        private val bukkitGsonConfig = BukkitGsonConfig(configData)

        /* Values */
        val locations = jsonArray.map { bukkitGsonConfig.getLocation(it.asJsonObject) }

    }

    companion object {

        val instance get() = Bukkit.getServicesManager()?.getRegistration(ConfigService::class.java)?.provider!!

    }

}