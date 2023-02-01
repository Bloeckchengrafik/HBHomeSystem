package de.horstblocks.homes.menu

import de.horstblocks.homes.Homes
import de.horstblocks.homes.config.t
import de.horstblocks.homes.config.translateString
import de.horstblocks.homes.db.HomeDAO
import net.wesjd.anvilgui.AnvilGUI
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.*

class HomeMenu(player: Player, referencingPlayer: UUID, page: Int = 1) : KMenu(
    title = t("home-menu.title"),
    rows = 6,
    player = player
) {
    private val homes: List<HomeDAO> = HomeDAO.getHomes(referencingPlayer)

    init {
        for (i in 0..8) {
            item(i, MARKER_ITEM)
            item(i + (5 * 9), MARKER_ITEM)
        }

        item(
            4, KItem(
                type = Material.PAPER,
                name = t("home-menu.page", page),
                lore = listOf()
            )
        )

        item((5 * 9) + 4, KItem(
            type = Material.NETHER_STAR,
            name = t("home-menu.new-home"),
            lore = listOf()
        )).onClick { _, _ ->
            player.closeInventory()

            AnvilGUI.Builder()
                .title(translateString("home-menu.new-home-name"))
                .text(" ")
                .plugin(Homes.instance)
                .onComplete { completion ->
                    val offlinePlayer = Bukkit.getOfflinePlayer(referencingPlayer)
                    val prefix = if (offlinePlayer.uniqueId == player.uniqueId) {
                        ""
                    } else {
                        "${offlinePlayer.name}:"
                    }

                    Bukkit.getServer().dispatchCommand(player, "sethome $prefix${completion.text}")
                    return@onComplete listOf(AnvilGUI.ResponseAction.close())
                }
                .open(player)
        }

        item(8, KItem(
            type = Material.BARRIER,
            name = t("home-menu.close"),
            lore = listOf()
        )).onClick { _, _ ->
            player.closeInventory()
        }

        if (page > 1) {
            item(3 + (5 * 9), KItem(
                type = Material.ARROW,
                name = t("home-menu.left"),
                lore = listOf()
            )).onClick { _, _ ->
                HomeMenu(player, referencingPlayer, page - 1).open()
            }
        }

        if (homes.size > page * MAX_HOMES_PER_PAGE) {
            item(5 + (5 * 9), KItem(
                type = Material.ARROW,
                name = t("home-menu.right"),
                lore = listOf()
            )).onClick { _, _ ->
                HomeMenu(player, referencingPlayer, page + 1).open()
            }
        }

        val allowedToEdit = player.uniqueId == referencingPlayer || player.hasPermission("homes.edit.others")

        var pos = 9
        for (home in homes.subList(
            (page - 1) * MAX_HOMES_PER_PAGE,
            homes.size.coerceAtMost(page * MAX_HOMES_PER_PAGE)
        )) {
            item(pos, HomeItem(home, allowedToEdit))
            pos++
        }
    }

    companion object {
        private const val MAX_HOMES_PER_PAGE = 4 * 9
    }
}