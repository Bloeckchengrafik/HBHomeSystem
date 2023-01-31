package de.horstblocks.homes.menu

import de.horstblocks.homes.config.t
import org.bukkit.Material
import org.bukkit.entity.Player

class AreYouSureMenu(
    player: Player,
    titleKey: String,
    private val callback: () -> Unit
) : KMenu(
    3,
    t(titleKey),
    player
) {

        init {
            item(11, KItem(
                Material.RED_STAINED_GLASS,
                t("are-you-sure-menu.no"),
                lore = listOf()
            )).onClick { _, _ ->
                player.closeInventory()
            }

            item(15, KItem(
                Material.GREEN_STAINED_GLASS,
                t("are-you-sure-menu.yes"),
                lore = listOf()
            )).onClick { _, _ ->
                callback()
                player.closeInventory()
            }
        }
}