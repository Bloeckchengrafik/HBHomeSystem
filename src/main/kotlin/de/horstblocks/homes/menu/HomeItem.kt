package de.horstblocks.homes.menu

import de.horstblocks.homes.config.t
import de.horstblocks.homes.db.HomeDAO
import de.horstblocks.homes.utils.plus
import net.kyori.adventure.text.Component
import org.bukkit.Material

class HomeItem(
    private val dao: HomeDAO
) : KMenu.KItem(
    type = Material.valueOf(dao.material),
    name = Component.text("§f" + dao.name),
    lore = listOf(
        t("home-menu.home-item.location", dao.x.toInt(), dao.y.toInt(), dao.z.toInt(), dao.world),
        t("home-menu.home-item.teleport"),
        t("home-menu.home-item.edit")
    )
) {
    init {
        onClick { menu, event ->
            menu.close()

            if (event.isRightClick) {
                EditHomeMenu(dao, menu.player).open()
                return@onClick
            }

            menu.player.teleport(dao.toLocation())
            menu.player.sendMessage(t("prefix") + t("home-menu.home-item.tp-success", dao.name))
        }
    }
}