package de.horstblocks.homes.menu

import de.horstblocks.homes.config.t
import de.horstblocks.homes.db.HomeDAO
import de.horstblocks.homes.utils.plus
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class DelayedSetItemRunnable(private val player: Player, private val home: HomeDAO) : BukkitRunnable() {
    override fun run() {
        player.openInventory.cursor?.let {
            if (it.type.isAir) return@let
            home.material = it.type.name
            home.save()

            player.closeInventory()
            player.sendMessage(t("prefix") + t("home-edit-menu.set-item-success", home.name))
        }
    }
}