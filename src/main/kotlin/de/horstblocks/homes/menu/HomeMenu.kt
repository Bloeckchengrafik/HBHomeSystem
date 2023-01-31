package de.horstblocks.homes.menu

import de.horstblocks.homes.config.t
import de.horstblocks.homes.db.HomeDAO
import de.horstblocks.homes.utils.plus
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player

class HomeMenu(player: Player, page: Int = 1) : KMenu(
    title = t("home-menu.title"),
    rows = 6,
    player = player
) {
    private val homes: List<HomeDAO> = HomeDAO.getHomes(player.uniqueId)

    init {
        for (i in 0..8) {
            item(i, MARKER_ITEM)
            item(i + (5 * 9), MARKER_ITEM)
        }

        var pos = 9
        for (home in homes.subList((page - 1) * MAX_HOMES_PER_PAGE, homes.size.coerceAtMost(page * MAX_HOMES_PER_PAGE))) {
            item(pos, HomeItem(home))
            pos++
        }
    }

    companion object {
        private const val MAX_HOMES_PER_PAGE = 4 * 9
    }
}