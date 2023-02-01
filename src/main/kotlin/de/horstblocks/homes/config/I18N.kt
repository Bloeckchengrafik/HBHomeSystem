package de.horstblocks.homes.config

import net.kyori.adventure.text.Component
import org.bukkit.ChatColor

lateinit var langConfig: ConfigFile
    private set

fun loadI18N() {
    langConfig = ConfigFile(
        "translations.yml", hashMapOf(
            "prefix" to "&6[&eHomes&6] &r",
            "home-menu.title" to "&6Homes",
            "home-menu.page" to "&7Page &e%s&r",
            "home-menu.close" to "&7Close",
            "home-menu.left" to "&7Left",
            "home-menu.right" to "&7Right",
            "home-menu.new-home" to "&aNew home",
            "home-menu.new-home-name" to "&7Enter a name",
            "home-menu.home-item.location" to "&7Location: &e%s, %s, %s&7 in &e%s&r",
            "home-menu.home-item.teleport" to "&7Click to teleport to this home",
            "home-menu.home-item.edit" to "&7Rightclick to edit this home",
            "home-menu.home-item.tp-success" to "&aSuccessfully teleported to home &e%s&r!",
            "sethome.usage" to "Usage: &c/sethome <name>",
            "sethome.already-exists" to "You already have a home with the name &c%s&r!",
            "sethome.success" to "Successfully created home &a%s&r!",
            "are-you-sure-menu.yes" to "&aYes",
            "are-you-sure-menu.no" to "&cNo",
            "home-edit-menu.title" to "&6Edit home &e%s&r",
            "home-edit-menu.delete" to "&cDelete",
            "home-edit-menu.delete-confirm" to "&cAre you sure?",
            "home-edit-menu.delete-success" to "&aSuccessfully deleted home &e%s&r!",
            "home-edit-menu.rename" to "&eRename",
            "home-edit-menu.rename-success" to "&aSuccessfully renamed home &e%s&r to &e%s&r!",
            "home-edit-menu.rename-already-exists" to "You already have a home with the name &c%s&r!",
            "home-edit-menu.replace" to "&eSet new location",
            "home-edit-menu.replace-success" to "&aSuccessfully set new location for home &e%s&r!",
            "home-edit-menu.set-item" to "&eSet item by clicking with an item in your inventory on this item",
            "home-edit-menu.set-item-success" to "&aSuccessfully set new item for home &e%s&r!",
            "home.doesnotexist" to "&7This home &cdoesn't&7 exist."
        )
    )
}

fun t(key: String): Component {
    return Component.text(ChatColor.translateAlternateColorCodes('&', langConfig.get(key)))
}

fun t(key: String, vararg args: Any): Component {
    return Component.text(String.format(ChatColor.translateAlternateColorCodes('&', langConfig.get(key)), *args))
}

fun translateString(key: String): String {
    return ChatColor.translateAlternateColorCodes('&', langConfig.get(key))
}
