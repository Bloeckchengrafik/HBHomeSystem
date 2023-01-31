package de.horstblocks.homes.menu

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player

class HomeMenu(player: Player) : KMenu(
    title = Component.text("Homes"),
    rows = 3,
    player = player
) {
    init {
        item(0, KItem(
            type = Material.DIAMOND,
            name = Component.text("Home 1"),
            lore = listOf(
                Component.text("This is your first home"),
                Component.text("You can teleport to it by typing /home home1")
            )
        )).onClick { menu, _ ->
            menu.close()
            menu.player.sendMessage(Component.text("You clicked on Home 1"))
        }
    }
}