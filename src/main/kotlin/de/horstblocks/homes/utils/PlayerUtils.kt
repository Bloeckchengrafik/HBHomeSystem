package de.horstblocks.homes.utils

import org.bukkit.entity.Player

private const val MAX_GREATNESS = 255

fun Player.greatestPermission(permissionToFmt: String): Int {
    if (hasPermission(permissionToFmt.format(MAX_GREATNESS))) {
        return Int.MAX_VALUE
    }

    for (i in MAX_GREATNESS downTo 0) {
        if (hasPermission(permissionToFmt.format(i))) {
            return i
        }
    }

    return 0
}