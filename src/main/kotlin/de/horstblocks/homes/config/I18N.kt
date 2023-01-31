package de.horstblocks.homes.config

import net.kyori.adventure.text.Component
import org.bukkit.ChatColor

lateinit var langConfig: ConfigFile
    private set

fun loadI18N() {
    langConfig = ConfigFile("translations.yml", hashMapOf(
        "prefix" to "&6[&eHomes&6] &r",
        "home-menu.title" to "&6Homes tx"
    ))
}

fun t(key: String): Component {
    return Component.text(ChatColor.translateAlternateColorCodes('&', langConfig.get(key)))
}

fun t(key: String, vararg args: Any): Component {
    return Component.text(String.format(ChatColor.translateAlternateColorCodes('&',langConfig.get(key)), *args))
}
