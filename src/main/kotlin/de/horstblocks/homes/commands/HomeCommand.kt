package de.horstblocks.homes.commands

import de.horstblocks.homes.config.t
import de.horstblocks.homes.db.HomeDAO
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
            HomeMenu(sender as Player).open()
            return true
        }

        val homeName = args.joinToString(" ")

        if(!HomeDAO.hasHome((sender as Player).uniqueId, homeName)) {
            sender.sendMessage(t("prefix") + t("home.doesnotexist"))
            return true
        }

        sender.teleport(HomeDAO.getHome(sender.uniqueId, homeName)!!.toLocation(), PlayerTeleportEvent.TeleportCause.COMMAND)
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
        return HomeDAO.getHomes((sender as Player).uniqueId).map { it.name }.filter { it.startsWith(homeNameSoFar) }
    }
}