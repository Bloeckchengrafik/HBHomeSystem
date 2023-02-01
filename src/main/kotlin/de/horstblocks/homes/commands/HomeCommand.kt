package de.horstblocks.homes.commands

import de.horstblocks.homes.config.t
import de.horstblocks.homes.db.HomeDAO
import de.horstblocks.homes.db.PlayerCacheDAO
import de.horstblocks.homes.menu.HomeMenu
import de.horstblocks.homes.utils.plus
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent

class HomeCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {

        if (args.isNullOrEmpty()) {
            HomeMenu(sender as Player, sender.uniqueId).open()
            return true
        }

        var homeName = args.joinToString(" ")

        if (homeName.endsWith(":")) {
            if (!sender.hasPermission("homes.others")) {
                sender.sendMessage(t("prefix") + t("no-permission"))
                return true
            }

            homeName = homeName.dropLast(1)
            val targetUUID = PlayerCacheDAO(name = homeName).fillUUID()
            if (targetUUID.isFailure) {
                sender.sendMessage(t("prefix") + t("player-not-found", homeName))
                return true
            }

            HomeMenu(sender as Player, targetUUID.getOrThrow()).open()

            return true
        }

        var targetUUID = (sender as Player).uniqueId

        if (homeName.contains(":")) {
            if (!sender.hasPermission("homes.others")) {
                sender.sendMessage(t("prefix") + t("no-permission"))
                return true
            }

            val split = homeName.split(":")
            val targetUsername = split[0]
            homeName = mutableListOf(*split.toTypedArray()).drop(1).joinToString(":")
            val newTargetUUID = PlayerCacheDAO(name = targetUsername).fillUUID()
            if (newTargetUUID.isFailure) {
                sender.sendMessage(t("prefix") + t("player-not-found", targetUsername))
                return true
            }

            targetUUID = newTargetUUID.getOrThrow()
        }

        if (!HomeDAO.hasHome(targetUUID, homeName)) {
            sender.sendMessage(t("prefix") + t("home.doesnotexist"))
            return true
        }

        sender.teleport(
            HomeDAO.getHome(targetUUID, homeName)!!.toLocation(),
            PlayerTeleportEvent.TeleportCause.COMMAND
        )
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): List<String>? {
        if (args.isNullOrEmpty()) return null

        val homeNameSoFar = args.joinToString(" ")

        var uuidUsed = (sender as Player).uniqueId
        var prefix = ""

        if (homeNameSoFar.contains(":")) {
            if (!sender.hasPermission("homes.others")) return null

            val split = homeNameSoFar.split(":")
            val targetUsername = split[0]
            val targetUUID = PlayerCacheDAO(name = targetUsername).fillUUID()
            if (targetUUID.isFailure) return null

            uuidUsed = targetUUID.getOrThrow()
            prefix = "$targetUsername:"
        }

        val homeNameDepth = args.size - 1


        return HomeDAO.getHomes(uuidUsed).map { prefix + it.name }.filter { it.startsWith(homeNameSoFar) }
            .map {
                val split = it.split(" ")
                // Drop the first homeNameDepth elements (the ones that are already typed)
                // and join the rest with spaces
                split.drop(homeNameDepth).joinToString(" ")
            }
    }
}