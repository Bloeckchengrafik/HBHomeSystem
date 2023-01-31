package de.horstblocks.homes

import de.horstblocks.homes.commands.HomeCommand
import de.horstblocks.homes.config.loadI18N
import de.horstblocks.homes.menu.KMenuListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Homes : JavaPlugin() {
    override fun onEnable() {
        instance = this

        loadI18N()

        Bukkit.getPluginManager().registerEvents(KMenuListener(), this)

        HomeCommand().let {
            getCommand("home")?.setExecutor(it)
            getCommand("home")?.tabCompleter = it
        }
    }

    override fun onDisable() {

    }

    companion object {
        lateinit var instance: Homes
            private set
    }
}