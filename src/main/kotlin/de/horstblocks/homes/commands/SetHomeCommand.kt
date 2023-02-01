package de.horstblocks.homes.commands

import de.horstblocks.homes.config.t
import de.horstblocks.homes.db.HomeDAO
import de.horstblocks.homes.utils.definingMaterial
import de.horstblocks.homes.utils.greatestPermission
import de.horstblocks.homes.utils.plus
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class SetHomeCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (args == null || args.isEmpty()) {
            sender.sendMessage(t("prefix") + t("sethome.usage"))
            return true
        }

        val homeName = args.joinToString(" ")

        val homes = HomeDAO.getHomes((sender as Player).uniqueId)

        if(homes.stream().filter { it.name == homeName }.count() > 0) {
            sender.sendMessage(t("prefix") + t("sethome.already-exists", homeName))
            return true
        }

        val maxHomes = sender.greatestPermission("homes.max.%d")
        if(homes.size >= maxHomes) {
            sender.sendMessage(t("prefix") + t("sethome.max-reached", maxHomes))
            return true
        }

        HomeDAO(
            name = homeName,
            owner = sender.uniqueId.toString(),
            x = sender.location.x.toFloat(),
            y = sender.location.y.toFloat(),
            z = sender.location.z.toFloat(),
            yaw = sender.location.yaw,
            pitch = sender.location.pitch,
            world = sender.location.world.name,
            material = sender.location.world.getBiome(sender.location).definingMaterial.name
        ).save()

        sender.sendMessage(t("prefix") + t("sethome.success", homeName))

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> {
        // There is no tab completion possible for this command
        return mutableListOf()
    }
}