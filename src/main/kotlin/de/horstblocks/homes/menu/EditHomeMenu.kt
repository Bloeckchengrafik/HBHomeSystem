package de.horstblocks.homes.menu

import de.horstblocks.homes.Homes
import de.horstblocks.homes.config.t
import de.horstblocks.homes.config.translateString
import de.horstblocks.homes.db.HomeDAO
import de.horstblocks.homes.utils.plus
import net.wesjd.anvilgui.AnvilGUI
import org.bukkit.Material
import org.bukkit.entity.Player

class EditHomeMenu(
    val home: HomeDAO,
    player: Player
) : KMenu(
    title = t("home-edit-menu.title", home.name),
    rows = 3,
    player = player
) {
    init {
        item(10, KItem(Material.ANVIL, t("home-edit-menu.rename"))).onClick { _, _ ->
            player.closeInventory()

            AnvilGUI.Builder()
                .title(translateString("home-edit-menu.rename"))
                .text(home.name)
                .plugin(Homes.instance)
                .onComplete { completion ->
                    if (HomeDAO.hasHome(player.uniqueId, completion.text)) {
                        player.sendMessage(t("prefix") + t("home-edit-menu.rename-already-exists", completion.text))
                        return@onComplete listOf(AnvilGUI.ResponseAction.close())
                    }

                    val oldName = home.name

                    home.name = completion.text
                    home.save()

                    player.sendMessage(t("prefix") + t("home-edit-menu.rename-success", oldName, completion.text))

                    return@onComplete listOf(AnvilGUI.ResponseAction.close())
                }
                .open(player)
        }

        item(12, KItem(Material.valueOf(home.material), t("home-edit-menu.set-item"))).onClick { _, event ->
            DelayedSetItemRunnable(player, home).runTaskLater(Homes.instance, 1)
        }

        item(14, KItem(Material.OAK_DOOR, t("home-edit-menu.replace"))).onClick { _, _ ->
            player.closeInventory()

            home.x = player.location.x.toFloat()
            home.y = player.location.y.toFloat()
            home.z = player.location.z.toFloat()
            home.yaw = player.location.yaw
            home.pitch = player.location.pitch
            home.world = player.location.world!!.name
            home.save()

            player.sendMessage(t("prefix") + t("home-edit-menu.replace-success", home.name))
        }

        item(16, KItem(Material.LAVA_BUCKET, t("home-edit-menu.delete"))).onClick { _, _ ->
            AreYouSureMenu(player, "home-edit-menu.delete-confirm") {
                home.delete()
                player.sendMessage(t("prefix") + t("home-edit-menu.delete-success", home.name))
                player.closeInventory()
            }.open()
        }
    }
}