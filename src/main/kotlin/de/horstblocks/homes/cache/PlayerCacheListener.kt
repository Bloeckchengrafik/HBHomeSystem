package de.horstblocks.homes.cache

import de.horstblocks.homes.db.PlayerCacheDAO
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerCacheListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val playerCache = PlayerCacheDAO(player)
        playerCache.ensureExists()
    }
}