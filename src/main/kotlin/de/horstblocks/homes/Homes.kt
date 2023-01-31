package de.horstblocks.homes

import de.horstblocks.homes.commands.HomeCommand
import de.horstblocks.homes.commands.SetHomeCommand
import de.horstblocks.homes.config.loadDatabaseConfigFile
import de.horstblocks.homes.config.loadI18N
import de.horstblocks.homes.db.DatabaseConnection
import de.horstblocks.homes.menu.KMenuListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Homes : JavaPlugin() {
    lateinit var database: DatabaseConnection

    override fun onEnable() {
        instance = this

        loadI18N()
        loadDatabaseConfigFile()
        database = DatabaseConnection()
        database.runScript("init.homes.sql")
        database.runScript("init.playercache.sql")
        database.closeConnection()

        Bukkit.getPluginManager().registerEvents(KMenuListener(), this)

        HomeCommand().let {
            getCommand("home")?.setExecutor(it)
            getCommand("home")?.tabCompleter = it
        }

        SetHomeCommand().let {
            getCommand("sethome")?.setExecutor(it)
            getCommand("sethome")?.tabCompleter = it
        }
    }

    override fun onDisable() {

    }

    companion object {
        lateinit var instance: Homes
            private set
    }
}