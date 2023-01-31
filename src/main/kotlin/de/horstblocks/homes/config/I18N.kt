package de.horstblocks.homes.config

import net.kyori.adventure.text.Component
import org.bukkit.ChatColor

lateinit var langConfig: ConfigFile
    private set

fun loadI18N() {
    langConfig = ConfigFile("translations.yml", hashMapOf(
        "prefix" to "&6[&eHomes&6] &r",
        "home-menu.title" to "&6Homes",
        "home-menu.page" to "&7Page &e%s&r",
        "home-menu.close" to "&7Close",
        "home-menu.left" to "&7Left",
        "home-menu.right" to "&7Right",
        "home-menu.home-item.location" to "&7Location: &e%s, %s, %s&7 in &e%s&r",
        "home-menu.home-item.teleport" to "&7Click to teleport to this home",
        "home-menu.home-item.tp-success" to "&aSuccessfully teleported to home &e%s&r!",
        "sethome.usage" to "Usage: &c/sethome <name>",
        "sethome.already-exists" to "You already have a home with the name &c%s&r!",
        "sethome.success" to "Successfully created home &a%s&r!"
    ))
}

fun t(key: String): Component {
    return Component.text(ChatColor.translateAlternateColorCodes('&', langConfig.get(key)))
}

fun t(key: String, vararg args: Any): Component {
    return Component.text(String.format(ChatColor.translateAlternateColorCodes('&',langConfig.get(key)), *args))
}
