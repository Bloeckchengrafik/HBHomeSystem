package de.horstblocks.homes.menu

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class KMenuListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val inventory = event.inventory
        if (inventory.holder is KMenu) {
            val menu = inventory.holder as KMenu
            event.isCancelled = true
            menu.click(event)
        }
    }
}