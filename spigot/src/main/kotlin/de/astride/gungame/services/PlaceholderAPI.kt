package de.astride.gungame.services

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.PluginManager

fun String.replaced(player: Player, pluginManager: PluginManager = Bukkit.getPluginManager()): String =
    if (pluginManager.getPlugin("PlaceholderAPI") != null) PlaceholderAPI.setPlaceholders(player, this) else this
